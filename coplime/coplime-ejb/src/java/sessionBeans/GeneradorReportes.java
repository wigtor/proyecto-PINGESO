/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.MantencionDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RevisionDAO;
import DAO.interfaces.SolicitudMantencionDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Inspector;
import entities.MantencionPuntoLimpio;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
import entities.RevisionPuntoLimpio;
import entities.SolicitudMantencion;
import entities.Usuario;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class GeneradorReportes implements GeneradorReportesLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private static final String[] OPCIONES_DATOS_PUNTOS_LIMPIOS = {"Número", "Nombre",
        "Comuna", "Ubicación", "Cantidad de visitas", "Cantidad de mantenciones", 
        "Inspector encargado", "Fecha última revisión", "Fecha próxima revisión"};
    private static final String[] OPCIONES_MANTENCIONES_PUNTO_LIMPIO = {"Número", 
        "N° de punto limpio", "Nombre punto limpio", "Operario encargado", "Fecha", "Detalle"};
    private static final String[] OPCIONES_REVISIONES_PUNTO_LIMPIO = {"Número", 
        "N° de punto limpio", "Nombre punto limpio", "Inspector encargado", "Fecha", "Detalle"};
    private static final String[] OPCIONES_SOLICITUDES_PUNTO_LIMPIO = {"Número", "N° de punto limpio", 
        "Nombre punto limpio", "Inspector solicitante", "Operario encargado", "Fecha", "Detalle"};
    private static final String[] OPCIONES_USUARIOS_SISTEMA = {"Id", "Tipo de usuario", 
        "Nombre", "Apellido paterno", "Apellido materno", "Correo electrónico", 
        "Cantidad de revisiones", "Cantidad de mantenciones", "Cantidad de solicitudes"};

    @Override
    public Map<String, Integer> getOpcionesReporte(int tipoReporte) {
        TreeMap<String, Integer> availablesOptions = new TreeMap<>();
        int i;
        switch(tipoReporte) {
            case DATOS_PUNTOS_LIMPIOS:
                for (i = 0; i < OPCIONES_DATOS_PUNTOS_LIMPIOS.length; i++) {
                    availablesOptions.put(OPCIONES_DATOS_PUNTOS_LIMPIOS[i], i);
                }
                
                return availablesOptions;
            case MANTENCIONES_PUNTO_LIMPIO:
                for (i = 0; i < OPCIONES_MANTENCIONES_PUNTO_LIMPIO.length; i++) {
                    availablesOptions.put(OPCIONES_MANTENCIONES_PUNTO_LIMPIO[i], i);
                }
                return availablesOptions;
            case REVISIONES_PUNTO_LIMPIO:
                for (i = 0; i < OPCIONES_REVISIONES_PUNTO_LIMPIO.length; i++) {
                    availablesOptions.put(OPCIONES_REVISIONES_PUNTO_LIMPIO[i], i);
                }
                return availablesOptions;
            case SOLICITUDES_PUNTO_LIMPIO:
                for (i = 0; i < OPCIONES_SOLICITUDES_PUNTO_LIMPIO.length; i++) {
                    availablesOptions.put(OPCIONES_SOLICITUDES_PUNTO_LIMPIO[i], i);
                }
                return availablesOptions;
            case USUARIOS_SISTEMA:
                for (i = 0; i < OPCIONES_USUARIOS_SISTEMA.length; i++) {
                    availablesOptions.put(OPCIONES_USUARIOS_SISTEMA[i], i);
                }
                return availablesOptions;
        }
        return null;
        
    }

    @Override
    public String[][] getDatosReporte(int tipoReporte, List<String> cabeceraReporte, Calendar fechaIni, Calendar fechaFin) {
        //Se arregla la horaFin para que quede al final del día
        fechaFin.set(Calendar.HOUR_OF_DAY, 23);
        fechaFin.set(Calendar.MINUTE, 59);
        fechaFin.set(Calendar.SECOND, 59);
        fechaFin.set(Calendar.MILLISECOND, 999);
        
        Collections.sort(cabeceraReporte);
        String [][] resultado = null;
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        int i, j, index;
        switch(tipoReporte) {
            case DATOS_PUNTOS_LIMPIOS:
                PuntoLimpioDAO ptoDAO = factory.getPuntoLimpioDAO();
                Collection<PuntoLimpio> ptosLimpios = ptoDAO.findAll();
                resultado = new String[ptosLimpios.size()+1][cabeceraReporte.size()];
                
                //Defino la cabecera de los datos
                i = 0;
                for (String elemCabeceraInt : cabeceraReporte) {
                    index = Integer.parseInt(elemCabeceraInt);
                    resultado[0][i] = OPCIONES_DATOS_PUNTOS_LIMPIOS[index];
                    i++;
                }
                
                i = 1;
                for (PuntoLimpio ptoTemp : ptosLimpios) {
                    j = 0;
                    for (String elemCabeceraInt : cabeceraReporte) {
                        index = Integer.parseInt(elemCabeceraInt);
                        resultado[i][j] = extraeDatoPuntoLimpio(index, ptoTemp);
                        j++;
                    }
                    i++;
                }
                break;
            case MANTENCIONES_PUNTO_LIMPIO:
                MantencionDAO mantDAO = factory.getMantencionDAO();
                Collection<MantencionPuntoLimpio> mantenciones = mantDAO.findByDateRange(fechaIni, fechaFin);
                resultado = new String[mantenciones.size()+1][cabeceraReporte.size()];
                
                //Defino la cabecera de los datos
                
                i = 0;
                for (String elemCabeceraInt : cabeceraReporte) {
                    index = Integer.parseInt(elemCabeceraInt);
                    resultado[0][i] = OPCIONES_MANTENCIONES_PUNTO_LIMPIO[index];
                    i++;
                }
                
                i = 1;
                for (MantencionPuntoLimpio mantTemp : mantenciones) {
                    j = 0;
                    for (String elemCabeceraInt : cabeceraReporte) {
                        index = Integer.parseInt(elemCabeceraInt);
                        resultado[i][j] = extraeDatoMantencion(index, mantTemp);
                        j++;
                    }
                    i++;
                }
                break;
            case REVISIONES_PUNTO_LIMPIO:
                RevisionDAO revDAO = factory.getRevisionDAO();
                Collection<RevisionPuntoLimpio> revisiones = revDAO.findByDateRange(fechaIni, fechaFin);
                resultado = new String[revisiones.size()+1][cabeceraReporte.size()];
                
                //Defino la cabecera de los datos
                i = 0;
                for (String elemCabeceraInt : cabeceraReporte) {
                    index = Integer.parseInt(elemCabeceraInt);
                    resultado[0][i] = OPCIONES_REVISIONES_PUNTO_LIMPIO[index];
                    i++;
                }
                
                i = 1;
                for (RevisionPuntoLimpio revTemp : revisiones) {
                    j = 0;
                    for (String elemCabeceraInt : cabeceraReporte) {
                        index = Integer.parseInt(elemCabeceraInt);
                        resultado[i][j] = extraeDatoRevision(index, revTemp);
                        j++;
                    }
                    i++;
                }
                break;
                
            case SOLICITUDES_PUNTO_LIMPIO:
                SolicitudMantencionDAO solDAO = factory.getSolicitudMantencionDAO();
                Collection<SolicitudMantencion> solicitudes = solDAO.findByDateRange(fechaIni, fechaFin);
                resultado = new String[solicitudes.size()+1][cabeceraReporte.size()];
                
                //Defino la cabecera de los datos
                i = 0;
                for (String elemCabeceraInt : cabeceraReporte) {
                    index = Integer.parseInt(elemCabeceraInt);
                    resultado[0][i] = OPCIONES_SOLICITUDES_PUNTO_LIMPIO[index];
                    i++;
                }
                
                i = 1;
                for (SolicitudMantencion solTemp : solicitudes) {
                    j = 0;
                    for (String elemCabeceraInt : cabeceraReporte) {
                        index = Integer.parseInt(elemCabeceraInt);
                        resultado[i][j] = extraeDatoSolicitud(index, solTemp);
                        j++;
                    }
                    i++;
                }
                break;
                
            case USUARIOS_SISTEMA:
                UsuarioDAO userDAO = factory.getUsuarioDAO();
                Collection<Usuario> usuarios = userDAO.findAll();
                resultado = new String[usuarios.size()+1][cabeceraReporte.size()];
                
                //Defino la cabecera de los datos
                i = 0;
                for (String elemCabeceraInt : cabeceraReporte) {
                    index = Integer.parseInt(elemCabeceraInt);
                    resultado[0][i] = OPCIONES_USUARIOS_SISTEMA[index];
                    i++;
                }
                
                i = 1;
                for (Usuario userTemp : usuarios) {
                    j = 0;
                    for (String elemCabeceraInt : cabeceraReporte) {
                        index = Integer.parseInt(elemCabeceraInt);
                        resultado[i][j] = extraeDatoUsuario(index, userTemp);
                        j++;
                    }
                    i++;
                }
                break;
        }
        return resultado;
    }
    
    private String extraeDatoPuntoLimpio(int index, PuntoLimpio obj) {
        //String nombreDatos = OPCIONES_DATOS_PUNTOS_LIMPIOS[index];
        switch (index) {
            case 0:
                return obj.getId().toString();
            case 1:
                return obj.getNombre();
            case 2:
                return obj.getComuna().getNombre();
            case 3:
                return obj.getUbicacion();
            case 4:
                return Integer.toString(obj.getRevisiones().size());
            case 5:
                return Integer.toString(obj.getMantenciones().size());
            case 6:
                Usuario userTemp = obj.getInspectorEncargado().getUsuario();
                return userTemp.getNombre().concat(" ").concat(userTemp.getApellido1())
                        .concat(" ").concat(userTemp.getApellido2());
            case 7:
                int cantidadMant = obj.getMantenciones().size();
                if (cantidadMant > 0) {
                    Calendar f1 = obj.getMantenciones().get(cantidadMant - 1).getFecha();
                    return Integer.toString(f1.get(Calendar.DAY_OF_MONTH)).concat("-")
                            .concat(Integer.toString(f1.get(Calendar.MONTH))).concat("-")
                            .concat(Integer.toString(f1.get(Calendar.YEAR)));
                }
                return "";
            case 8:
                Calendar f2 = obj.getFechaProxRevision();
                return Integer.toString(f2.get(Calendar.DAY_OF_MONTH)).concat("-")
                        .concat(Integer.toString(f2.get(Calendar.MONTH))).concat("-")
                        .concat(Integer.toString(f2.get(Calendar.YEAR)));
        }
        return "";
    }
    
    private String extraeDatoMantencion(int index, MantencionPuntoLimpio obj) {
        switch (index) {
            case 0:
                return obj.getId().toString();
            case 1:
                return obj.getPuntoLimpio().getId().toString();
            case 2:
                return obj.getPuntoLimpio().getNombre();
            case 3:
                Usuario userTemp = obj.getOperarioMantencion().getUsuario();
                return userTemp.getNombre().concat(" ").concat(userTemp.getApellido1())
                        .concat(" ").concat(userTemp.getApellido2());
            case 4:
                Calendar f2 = obj.getFecha();
                return Integer.toString(f2.get(Calendar.DAY_OF_MONTH)).concat("-")
                        .concat(Integer.toString(f2.get(Calendar.MONTH))).concat("-")
                        .concat(Integer.toString(f2.get(Calendar.YEAR)));
            case 5:
                return obj.getComentarios();
        }
        return "";
    }
    
    private String extraeDatoRevision(int index, RevisionPuntoLimpio obj) {
        switch (index) {
            case 0:
                return obj.getId().toString();
            case 1:
                return obj.getPuntoLimpio().getId().toString();
            case 2:
                return obj.getPuntoLimpio().getNombre();
            case 3:
                Usuario userTemp = obj.getInspectorRevisor().getUsuario();
                return userTemp.getNombre().concat(" ").concat(userTemp.getApellido1())
                        .concat(" ").concat(userTemp.getApellido2());
            case 4:
                Calendar f2 = obj.getFecha();
                return Integer.toString(f2.get(Calendar.DAY_OF_MONTH)).concat("-")
                        .concat(Integer.toString(f2.get(Calendar.MONTH))).concat("-")
                        .concat(Integer.toString(f2.get(Calendar.YEAR)));
            case 5:
                return obj.getDetalles();
        }
        return "";
    }
    
    private String extraeDatoSolicitud(int index, SolicitudMantencion obj) {
        switch (index) {
            case 0:
                return obj.getId().toString();
            case 1:
                return obj.getPuntoLimpio().getId().toString();
            case 2:
                return obj.getPuntoLimpio().getNombre();
            case 3:
                Usuario userTemp = obj.getInspectorSolicitante().getUsuario();
                return userTemp.getNombre().concat(" ").concat(userTemp.getApellido1())
                        .concat(" ").concat(userTemp.getApellido2());
            case 4:
                Usuario userTemp2 = obj.getOperarioAsignado().getUsuario();
                return userTemp2.getNombre().concat(" ").concat(userTemp2.getApellido1())
                        .concat(" ").concat(userTemp2.getApellido2());
            case 5:
                Calendar f2 = obj.getFecha();
                return Integer.toString(f2.get(Calendar.DAY_OF_MONTH)).concat("-")
                        .concat(Integer.toString(f2.get(Calendar.MONTH))).concat("-")
                        .concat(Integer.toString(f2.get(Calendar.YEAR)));
            case 6:
                return obj.getDetalles();
        }
        return "";
    }
    
    private String extraeDatoUsuario(int index, Usuario obj) {
        switch (index) {
            case 0:
                return obj.getId().toString();
            case 1:
                return obj.getRol().getNombreRol();
            case 2:
                return obj.getNombre();
            case 3:
                return obj.getApellido1();
            case 4:
                return obj.getApellido2();
            case 5:
                return obj.getEmail();
            case 6: //Revisiones
                if (obj.getRol().getNombreRol().equals("Inspector")) {
                    InspectorDAO inspDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em).getInspectorDAO();
                    Inspector insp = inspDAO.findByRut(obj.getRut());
                    if (insp != null) {
                        return Integer.toString(insp.getRevisionesRealizadas().size());
                    }
                }
                return "";
            case 7: //Mantenciones
                if (obj.getRol().getNombreRol().equals("Operario")) {
                    OperarioDAO operDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em).getOperarioDAO();
                    OperarioMantencion oper = operDAO.findByRut(obj.getRut());
                    if (oper != null) {
                        return Integer.toString(oper.getMantencionesRealizadas().size());
                    }
                }
                return "";
            case 8: //Solicitudes
                if (obj.getRol().getNombreRol().equals("Inspector")) {
                    InspectorDAO inspDAO2 = DAOFactory.getDAOFactory(DAOFactory.JPA, em).getInspectorDAO();
                    Inspector insp2 = inspDAO2.findByRut(obj.getRut());
                    if (insp2 != null) {
                        return Integer.toString(insp2.getSolicitudesMantencionRealizadas().size());
                    }
                }
                return "";
        }
        return "";
    }
}
