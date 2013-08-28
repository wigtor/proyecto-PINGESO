/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.PuntoLimpioDAO;
import entities.PuntoLimpio;
import java.util.Collection;
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
                availablesOptions.put("Id", 0);
                availablesOptions.put("Tipo de usuario", 1);
                availablesOptions.put("Nombre", 2);
                availablesOptions.put("Apellido paterno", 3);
                availablesOptions.put("Apellido materno", 4);
                availablesOptions.put("Correo electrónico", 5);
                availablesOptions.put("Cantidad de revisiones", 6);
                availablesOptions.put("Cantidad de mantenciones", 7);
                availablesOptions.put("Cantidad de solicitudes", 8);
                return availablesOptions;
        }
        return null;
        
    }

    @Override
    public String[][] getDatosReporte(int tipoReporte, Collection<String> cabeceraReporte) {
        String [][] resultado = null;
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        
        switch(tipoReporte) {
            case DATOS_PUNTOS_LIMPIOS:
                PuntoLimpioDAO ptoDAO = factory.getPuntoLimpioDAO();
                Collection<PuntoLimpio> ptosLimpios = ptoDAO.findAll();
                System.out.println("Cantidad filas: "+ (ptosLimpios.size()+1) + " Cantidad columnas: " + cabeceraReporte.size());
                resultado = new String[ptosLimpios.size()+1][cabeceraReporte.size()];
                //Defino la cabecera de los datos
                int i, j, index;
                i = 0;
                for (String elemCabeceraInt : cabeceraReporte) {
                    index = Integer.parseInt(elemCabeceraInt);
                    resultado[0][i] = OPCIONES_DATOS_PUNTOS_LIMPIOS[index];
                    i++;
                }
                
                for (i = 1; i < resultado.length; i++) {
                    j = 0;
                    for (String elemCabeceraInt : cabeceraReporte) {
                        //index = Integer.parseInt(elemCabeceraInt);
                        resultado[i][j] = elemCabeceraInt;
                        j++;
                    }
                }
                
            case MANTENCIONES_PUNTO_LIMPIO:
                
                
            case REVISIONES_PUNTO_LIMPIO:
                
                
            case SOLICITUDES_PUNTO_LIMPIO:
                
                
            case USUARIOS_SISTEMA:
                
                
        }
        return resultado;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
