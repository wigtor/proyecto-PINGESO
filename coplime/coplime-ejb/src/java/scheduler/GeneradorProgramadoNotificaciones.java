/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import DAO.DAOFactory;
import DAO.interfaces.NotificacionDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.AdministradorDAO;
import entities.Contenedor;
import entities.Notificacion;
import entities.PuntoLimpio;
import entities.Administrador;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import scheduler.algoritmosCalculo.AlgoritmoCalculo;
import scheduler.algoritmosCalculo.FactoryAlgoritmosCalculo;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author Armando
 */
@Stateless
public class GeneradorProgramadoNotificaciones implements GeneradorProgramadoNotificacionesLocal {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    //TODO Cambiar por Programatic Timers (buscar en la documentación)
    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "01")
    @Override
    //NOTA: Los parámetros de periodicidad de la tarea programada deben ser configurables externamente
    //a través de la aplicación.
    public void calculoLlenadoAutomatico() {
        //Inicialización de las variables
        Collection<PuntoLimpio> listaPuntosLimpios;
        boolean resultadoOperacion;
        
        // TODO: Borrar esta línea
        System.out.println("Timer event [" + new Date() + "]: Generando notificaciones automáticas.");
        
        //Le pedimos al sistema que nos entregue los puntos limpios existentes
        listaPuntosLimpios = crudPuntoLimpio.getAllPuntosLimpios();
        /* Con la lista de puntos limpios pedimos la aplicación del algoritmo
         * y si es necesario la generación de notificaciones automáticas.
         */
        resultadoOperacion = evaluar(listaPuntosLimpios);
        if (resultadoOperacion == true){
            Logger.getLogger(GeneradorProgramadoNotificaciones.class.getName()).log(Level.FINER, "Generación de notificaciones automáticas finalizada exitosamente.");
        } else {
            Logger.getLogger(GeneradorProgramadoNotificaciones.class.getName()).log(Level.WARNING, "Error en generación de notificaciones automáticas.");
        }
    }
        
     /**
     * Función evaluar: dado una lista de puntos limpios procede a aplicar un algoritmo
     * de estimación de fecha de llenado de cada contenedor para cada punto limpio en la
     * lista, refrescando las fechas de próxima revisión del punto limpio en la base
     * de datos a través de la interfaz DAO correspondiente. Además, si la fecha de
     * próxima revisión corresponde al día de mañana, genera la notificación asociada.
     * 
     * Si un contenedor está lleno, la fecha de revisión es fijada para HOY.
     * Si un contenedor NO tiene puntos en su historial, se genera una notificación al Administrador.
     * @param puntosLimpiosEvaluar: listado con los puntos limpios a los cuales se le estimará la fecha de próxima revisión.
     * @return True cuando la operación es exitosa, False si existieron errores.
     */
    private boolean evaluar(Collection<PuntoLimpio> puntosLimpiosEvaluar){
        AlgoritmoCalculo ac = FactoryAlgoritmosCalculo.getAlgoritmoCalculo(FactoryAlgoritmosCalculo.PROMEDIO_PENDIENTES);
        Date fechaRevision,menorFechaRevision=null;
        for (PuntoLimpio p:puntosLimpiosEvaluar){
            for (Contenedor c: p.getContenedores()){
                //Se establece como fecha de revisión aquella para la cual se estima que el contenedor C
                //alcanzará el porcentaje establecido.
                fechaRevision = ac.estimar(c.getHistorialContenedor(), 100);
                /* Cazando alerta de contenedor sin puntos en el historial:
                 * Si la fecha de revisión está fijada para el día de AYER, generar notificaciones respectivas.
                 */
                Calendar calendario = Calendar.getInstance();
                calendario.add(Calendar.DAY_OF_MONTH, -1);
                if(fechaRevision.equals(calendario.getTime())){//¿Es la fecha de revisión "Ayer"?
                    generarNotificacionContenedorSinHistorial(p, c);
                    String stringAviso = "Contenedor ID ".concat(c.getId().toString());
                    stringAviso = stringAviso.concat(", Material ").concat(c.getMaterialDeAcopio().getNombre_material().toString());
                    stringAviso = stringAviso.concat(" perteneciente al punto limpio ").concat(p.getNombre());
                    stringAviso = stringAviso.concat(" no contiene registros en su historial de llenado.");
                    Logger.getLogger(GeneradorProgramadoNotificaciones.class.getName()).log(Level.WARNING, stringAviso);
                    //Volvemos al día de "hoy" sumando 1 al valor de la variable calendario
                    calendario.add(Calendar.DAY_OF_MONTH, 1);
                    //Se corrige el valor de la fecha de revisión de "ayer" a "hoy"
                    fechaRevision = calendario.getTime();//Hoy
                }
                
                /* Se establece la fecha más próxima para tener que revisar el punto limpio
                 * Si no hay fechas, se escoge la primera.
                 * Si hay fechas, se compara con la menor actual.
                 */
        
                if (menorFechaRevision==null){
                    menorFechaRevision = fechaRevision;
                } else {
                    if(fechaRevision.before(menorFechaRevision)){
                        menorFechaRevision = fechaRevision;
                    }
                }
            }
            actualizarFechaRevisionPL(p, menorFechaRevision);
            
            Calendar magnana = Calendar.getInstance();
            magnana.add(Calendar.DATE, 2);
            Calendar menorFechaRevisionCalendar = Calendar.getInstance();
            menorFechaRevisionCalendar.setTime(menorFechaRevision);
            if(menorFechaRevision.before(magnana.getTime())){
                generarNotificacionRevisionPL(menorFechaRevisionCalendar, p);
            }
            //Es necesario resetear la menor fecha de revisión para un nuevo punto limpio
            menorFechaRevision = null;
            
        }
        
        //@TODO cazar excepciones para retornar false en casos de fallo.
        return true;
    }
    
    /**
     * private void actualizarFechaRevisionPL
     * Establece una nueva fecha de revisión para un punto limpio P
     * @param p : Punto limpio cuya fecha de revisión se modificará
     * @param nuevaFecha : Nueva fecha de revisión
     */
    
    private void actualizarFechaRevisionPL(PuntoLimpio p,Date nuevaFecha){
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptosLimpiosDAO = fabricaDAO.getPuntoLimpioDAO();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nuevaFecha);
        p.setFechaProxRevision(cal);
        ptosLimpiosDAO.update(p);
    }
    
    /**
     * private void generarNotificaciones: dada la lista de Puntos Limpios y dada la lista de resultados
     * asociados a cada Punto Limpio, le indica al sistema generar las Notificaciones correspondientes
     * según corresponda.
     * @param fechaRevision : fecha en la cual el punto limpio deberá revisarse
     * @param pl : Punto limpio asociado
     */
    
    private void generarNotificacionRevisionPL(Calendar fechaRevision, PuntoLimpio pl){
       //Nota: ¿por qué la fecha del MENSAJE de la notificación no es pl.getFechaHora()?
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        NotificacionDAO notifDAO = fabricaDAO.getNotificacionDAO();
        Notificacion nuevaNotificacion = new Notificacion();
        
        nuevaNotificacion.setPuntoLimpio(pl);
        nuevaNotificacion.setFechaHora(Calendar.getInstance());
        //Se verifica que el punto limpio disponga de inspector, caso contrario el usuario a cargo
        //será cualquier administrador otorgado por la interfaz AdministradorDAO
        if(pl.getInspectorEncargado()==null){
            AdministradorDAO adminDAO = fabricaDAO.getAdministradorDAO();
            nuevaNotificacion.setUsuarioEncargado(adminDAO.findAnyAdministrador().getUsuario());
        } else {
            nuevaNotificacion.setUsuarioEncargado(pl.getInspectorEncargado().getUsuario());
        }
        nuevaNotificacion.setComentario("El punto limpio número ".concat(pl.getId().toString()).concat(" de nombre ").concat(pl.getNombre()).concat(" necesita ser revisado. Causa: probable llenado de uno de los contenedores el día ").concat(fechaRevision.toString()).concat("."));
        //TODO resolver tipo de incidencia
        //nuevaNotificacion.setTipoIncidencia(null);
        notifDAO.insert(nuevaNotificacion);        
        }
    
    private void generarNotificacionContenedorSinHistorial(PuntoLimpio pl, Contenedor c){
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        NotificacionDAO notifDAO = fabricaDAO.getNotificacionDAO();
        AdministradorDAO adminDAO = fabricaDAO.getAdministradorDAO();
        
    
        Administrador admin = adminDAO.findAnyAdministrador();
        Notificacion nuevaNotificacion = new Notificacion();
        nuevaNotificacion.setFechaHora(Calendar.getInstance());
        nuevaNotificacion.setPuntoLimpio(pl);
        nuevaNotificacion.setUsuarioEncargado(admin.getUsuario());
        //TODO resolver tipo de incidencia
        //nuevaNotificacion.setTipoIncidencia(null);
        nuevaNotificacion.setComentario("Contenedor ID ".concat(c.getId().toString()).concat(" (".concat(c.getMaterialDeAcopio().toString()).concat(") perteneciente al Punto Limpio ".concat(pl.getNombre()).concat(" no tiene historial de llenado.\nPor favor, ingrese una revisión del punto limpio para generar el historial."))));
        notifDAO.insert(nuevaNotificacion);
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
