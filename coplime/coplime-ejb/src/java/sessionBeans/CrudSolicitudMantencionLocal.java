/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.SolicitudMantencion;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudSolicitudMantencionLocal {
    public boolean agregarSolicitudMantencion(Integer numPtoLimpio, String usernameLogueado, Integer numOperario, String detalle);
    public SolicitudMantencion getSolicitudById(Integer numSolicitud);
    public Collection<SolicitudMantencion> getAllSolicitudes(String usernameQuienPregunta);
}
