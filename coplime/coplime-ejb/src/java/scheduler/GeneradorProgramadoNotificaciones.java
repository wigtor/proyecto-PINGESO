/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import DAO.DAOFactory;
import DAO.interfaces.NotificacionDAO;
import DAO.interfaces.PuntoLimpioDAO;
import entities.Contenedor;
import entities.HistoricoContenedor;
import entities.Notificacion;
import entities.PuntoLimpio;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
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
    
    

    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "01")
    @Override
    //NOTA: Los parámetros de periodicidad de la tarea programada deben ser configurables externamente
    //a través de la aplicación.
    public void calculoLlenadoAutomatico() {
        //Inicialización de las variables
        //@TODO hacer variables privadas
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
     * @param puntosLimpiosEvaluar: listado con los puntos limpios a los cuales se le estimará la fecha de próxima revisión.
     * @return True cuando la operación es exitosa, False si existieron errores.
     */
    private boolean evaluar(Collection<PuntoLimpio> puntosLimpiosEvaluar){
        AlgoritmoCalculo ac = FactoryAlgoritmosCalculo.getAlgoritmoCalculo(FactoryAlgoritmosCalculo.PROMEDIO_PENDIENTES);
        Date fechaRevision,menorFechaRevision=null;
        for (PuntoLimpio p:puntosLimpiosEvaluar){
            for (Contenedor c: p.getContenedores()){
                /*
                 * Se pide 
                 */
                fechaRevision = ac.estimar(c.getHistorialContenedor(), 100);
                /* Se establece la fecha más próxima para tener que revisar el punto limpio
                 * Si no hay fechas, se escoge la primera.
                 * Si hay fechas, se compara con la menor actual.
                 */
                if (menorFechaRevision==null){
                    menorFechaRevision = fechaRevision;
                } else {
                    /*
                     * fechaRevision.before: devuelve TRUE si 
                     */
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
                generarNotificacion(menorFechaRevisionCalendar, p);
            }
            //Es necesario resetear la menor fecha de revisión para un nuevo punto limpio
            menorFechaRevision = null;
            
        }
        
        //@TODO cazar excepciones para retornar false en casos de fallo.
        return true;
    }
    
    private void actualizarFechaRevisionPL(PuntoLimpio p,Date nuevaFecha){
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptosLimpiosDAO = fabricaDAO.getPuntoLimpioDAO();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nuevaFecha);
        p.setFechaProxRevision(cal);
        ptosLimpiosDAO.update(p);
    }
    
    
   
    
    
    
    /*
     * Función generarNotificaciones: dada la lista de Puntos Limpios y dada la lista de resultados
     * asociados a cada Punto Limpio, le indica al sistema generar las Notificaciones correspondientes
     * según corresponda.
     */
    
    private void generarNotificacion(Calendar fechaRevision, PuntoLimpio pl){
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        NotificacionDAO notifDAO = fabricaDAO.getNotificacionDAO();
        Notificacion nuevaNotificacion = new Notificacion();
        nuevaNotificacion.setPuntoLimpio(pl);
        nuevaNotificacion.setFechaHora(Calendar.getInstance());
        nuevaNotificacion.setComentario("El punto limpio número ".concat(pl.getId().toString()).concat(" de nombre ").concat(pl.getNombre()).concat(" necesita ser revisado. Causa: probable llenado de uno de los contenedores el día ").concat(fechaRevision.toString()).concat("."));
        // TODO: Revisar tema tipos incidencia.
        //nuevaNotificacion.setTipoIncidencia(null);
        notifDAO.insert(nuevaNotificacion);
        
        }

    public void persist(Object object) {
        em.persist(object);
    }
}
