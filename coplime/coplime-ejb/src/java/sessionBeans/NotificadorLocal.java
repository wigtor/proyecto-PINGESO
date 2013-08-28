/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Notificacion;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface NotificadorLocal {

    public Integer obtenerCantidadNotificaciones(String username);
    
    public Collection<Notificacion> getAllNotificaciones(String username);
    
    public Notificacion getNotificacion(Integer id);

    public String getOrigenIncidencia(Notificacion notif);

    public boolean isNotificacionUsuario(Notificacion notif);

    public boolean checkResuelta(Integer idNotif, boolean check);
    
    public boolean checkRevisada(Integer idNotif, boolean check);

    public boolean eliminarNotificacion(Integer idNotif);

}
