/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.PuntoLimpio;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudPuntoLimpioLocal {

    public boolean eliminarPuntoLimpio(Integer id);

    public PuntoLimpio getPuntoLimpioByNum(Integer num);

    public Collection<PuntoLimpio> getAllPuntosLimpios();

    public void agregarPuntoLimpio(String nombre, String comuna, Calendar fechaProxRev, String estadoIni, int numInspEnc);

    public void editarPuntoLimpio(Integer idPtoLimpio, String nombre, String comuna, Calendar fechaProxRev, String estadoIni, int numInspEnc);

    public boolean eliminarPuntoLimpioByNum(Integer num);
    
}
