/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface GeneradorReportesLocal {
    public static final int DATOS_PUNTOS_LIMPIOS = 1;
    public static final int MANTENCIONES_PUNTO_LIMPIO = 2;
    public static final int REVISIONES_PUNTO_LIMPIO = 3;
    public static final int SOLICITUDES_PUNTO_LIMPIO = 4;
    public static final int USUARIOS_SISTEMA = 5;
    
    public Map<String, Integer> getOpcionesReporte(int tipoReporte);
    
    public String[][] getDatosReporte(int tipoReporte, List<String> cabeceraReporte, Calendar fechaIni, Calendar fechaFin);
}
