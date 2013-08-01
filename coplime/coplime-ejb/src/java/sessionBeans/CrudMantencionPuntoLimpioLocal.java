/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.MantencionPuntoLimpio;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudMantencionPuntoLimpioLocal {

    public boolean agregarMantencion(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado);
    public MantencionPuntoLimpio getMantencionById(Integer numMantencion);
    public Collection<MantencionPuntoLimpio> getAllMantenciones(String usernameQuienPregunta);
}
