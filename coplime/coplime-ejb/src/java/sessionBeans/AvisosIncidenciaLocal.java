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

    /**
     * Obtiene una colección de todos los tipos de aviso de incidencia que son visibles por el usuario.
     * @return Una colección de objetos "TipoIncidencia", puede ser vacía, pero nunca null
     */
    Collection<TipoIncidencia> getTiposAvisos();

    /**
     * Crea una entidad NotificacionDeUsuario y la persiste en la fuente de datos.
     * @param numPuntoLimpio El identificador del punto limpio al que se le realiza el aviso de incidencia
     * @param emailContacto El mail de la persona que realizó el aviso
     * @param detalles Una descripción de que trata la incidencia
     * @param idTipoIncidenciaSeleccionada El identificador del tipo de incidencia que el usuario seleccionó
     * @param datosImagen Los datos como bytes de una imagen que ayude a explicar el aviso de incidencia, puede ser null.
     * @param tipoImagen Un string indicando que tipo MIME de la imagen que se guarda, por ejemplo: "image/jpeg"
     */
    public void guardarAvisoIncidencia(int numPuntoLimpio, String emailContacto, String detalles, int idTipoIncidenciaSeleccionada, byte[] datosImagen, String tipoImagen);

}
