/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.PuntoLimpio;
import entities.TipoIncidencia;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface AvisosIncidenciaLocal {

    Collection<TipoIncidencia> getTiposAvisos();

    public void guardarAvisoIncidencia(int numPuntoLimpio, String emailContacto, String detalles, int idTipoIncidenciaSeleccionada, byte[] datosImagen, String tipoImagen);

    public Collection<PuntoLimpio> getPuntosLimpios();

    public String getNombrePtoLimpio(int numPtoLimpio);
    
}
