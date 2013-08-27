/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author victor
 */
@Stateless
public class GeneradorReportes implements GeneradorReportesLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    @Override
    public Map<String, String> getOpcionesReporte(int tipoReporte) {
        HashMap<String, String> availablesOptions = new HashMap<>();
        switch(tipoReporte) {
            case DATOS_PUNTOS_LIMPIOS:
                availablesOptions.put("numero", "Número");
                availablesOptions.put("nombre", "Nombre");
                availablesOptions.put("comuna", "Comuna");
                availablesOptions.put("ubicacion", "Ubicación");
                availablesOptions.put("cantidadVisitas", "Cantidad de visitas");
                availablesOptions.put("cantidadMantenciones", "Cantidad de mantenciones");
                availablesOptions.put("inspectorEncargado", "Inspector encargado");
                availablesOptions.put("fechaUltimaRevision", "Fecha última revisión");
                availablesOptions.put("fechaProximaRevision", "Fecha próxima revisión");
                return availablesOptions;
            case MANTENCIONES_PUNTO_LIMPIO:
                availablesOptions.put("numero", "Número");
                availablesOptions.put("num_punto_limpio", "N° de punto limpio");
                availablesOptions.put("nombre_punto_limpio", "Nombre punto limpio");
                availablesOptions.put("operario_encargado", "Operario encargado");
                availablesOptions.put("fecha", "Fecha");
                availablesOptions.put("detalle", "Detalle");
                return availablesOptions;
            case REVISIONES_PUNTO_LIMPIO:
                availablesOptions.put("numero", "Número");
                availablesOptions.put("num_punto_limpio", "N° de punto limpio");
                availablesOptions.put("nombre_punto_limpio", "Nombre punto limpio");
                availablesOptions.put("inspector_encargado", "Inspector encargado");
                availablesOptions.put("fecha", "Fecha");
                availablesOptions.put("detalle", "Detalle");
                return availablesOptions;
            case SOLICITUDES_PUNTO_LIMPIO:
                availablesOptions.put("numero", "Número");
                availablesOptions.put("num_punto_limpio", "N° de punto limpio");
                availablesOptions.put("nombre_punto_limpio", "Nombre punto limpio");
                availablesOptions.put("inspector_solicitate", "Inspector solicitante");
                availablesOptions.put("operario_encargado", "Operario encargado");
                availablesOptions.put("fecha", "Fecha");
                availablesOptions.put("detalle", "Detalle");
                return availablesOptions;
            case USUARIOS_SISTEMA:
                availablesOptions.put("id", "id");
                availablesOptions.put("rol_usuario", "Tipo de usuario");
                availablesOptions.put("nombre", "Nombre");
                availablesOptions.put("apellido1", "Apellido paterno");
                availablesOptions.put("apellido2", "Apellido materno");
                availablesOptions.put("correo", "Correo electrónico");
                availablesOptions.put("cantidad_revisiones", "Cantidad de revisiones");
                availablesOptions.put("cantidad_mantenciones", "Cantidad de mantenciones");
                availablesOptions.put("cantiad_solicitudes", "Cantidad de solicitudes");
                return availablesOptions;
        }
        return null;
        
    }

    @Override
    public String[][] getDatosReporte(int tipoReporte, Collection<String> cabeceraReporte) {
        switch(tipoReporte) {
            case DATOS_PUNTOS_LIMPIOS:
                
                
            case MANTENCIONES_PUNTO_LIMPIO:
                
                
            case REVISIONES_PUNTO_LIMPIO:
                
                
            case SOLICITUDES_PUNTO_LIMPIO:
                
                
            case USUARIOS_SISTEMA:
                
                
        }
        return null;
    }
}
