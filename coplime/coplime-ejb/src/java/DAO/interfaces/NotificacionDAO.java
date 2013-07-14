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
    // Interface that all AdministradorDAOs must support

    public Integer countPorRevisar(String username);
}
