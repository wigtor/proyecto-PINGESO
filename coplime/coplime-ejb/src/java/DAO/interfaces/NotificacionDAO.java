/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Notificacion;
import java.util.Collection;

/**
 *
 * @author victor
 */
public interface NotificacionDAO extends genericDAO<Notificacion>{
    
    /**
     * Cuenta cuantas notificaciones tiene el usuario que no ha revisado.
     * @param username El nombre de usuario de quien se quiere obtener su notificaciones
     * @return Devuelve la cantidad de notificaciones sin revisar
     */
    public Integer countPorRevisar(String username);
    
    
    public Collection<Notificacion> findAllOfUser(String username);
}
