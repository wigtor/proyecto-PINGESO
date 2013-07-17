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

    Integer obtenerCantidadNotificaciones(String username);
    Collection<Notificacion> getAllNotificaciones(String username);
    
}
