/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import java.util.Collection;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface GeneradorReportesLocal {
    public static int DATOS_PUNTOS_LIMPIOS = 1;
    public static int MANTENCIONES_PUNTO_LIMPIO = 2;
    public static int REVISIONES_PUNTO_LIMPIO = 3;
    public static int SOLICITUDES_PUNTO_LIMPIO = 4;
    public static int USUARIOS_SISTEMA = 5;
    
    public Map<String, String> getOpcionesReporte(int tipoReporte);
    
    public String[][] getDatosReporte(int tipoReporte, Collection<String> cabeceraReporte);
}
