/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Notificacion;
import entities.NotificacionDeUsuario;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface NotificadorLocal {

    Integer obtenerCantidadNotificaciones(String username);
    Collection<Notificacion> getAllNotificaciones(String username);
    public Notificacion getNotificacion(Integer id);

    public String getOrigenIncidencia(Notificacion notif);

    public boolean isNotificacionUsuario(Notificacion notif);

    public byte[] getBytesImagen(NotificacionDeUsuario notifUsuarioTemp);
    
}
