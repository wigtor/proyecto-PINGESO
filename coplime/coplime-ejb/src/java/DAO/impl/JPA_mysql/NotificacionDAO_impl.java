/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.NotificacionDAO;
import entities.Notificacion;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    
    @Override
    public Integer countPorRevisar(String username) {
        Query q = this.em.createNamedQuery("Notificacion.countNoRevisadasUsuarioDestinatario");
        q.setParameter("username", username);
        
        Long resultado;
        try {
            resultado = (Long)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return 0;
        }
        //System.out.println("Notificaciones por revisar para el usuario con username: "+username+" son:"+resultado);
        return new Integer(resultado.intValue());
    }
    
    @Override
    public Collection<Notificacion> findAllOfUser(String username) {
        Query q = this.em.createNamedQuery("Notificacion.findByUsuarioDestinatario");
        q.setParameter("username", username);
        return (Collection<Notificacion>)q.getResultList();
    }
            
}
