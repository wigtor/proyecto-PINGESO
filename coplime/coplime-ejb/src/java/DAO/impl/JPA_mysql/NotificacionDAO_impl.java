/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.NotificacionDAO;
import entities.Notificacion;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class NotificacionDAO_impl extends genericDAO_impl<Notificacion> implements NotificacionDAO{
    
    public NotificacionDAO_impl(EntityManager em) {
        super(Notificacion.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    
}
