/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.RolDAO;
import entities.Rol;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class RolDAO_impl extends genericDAO_impl<Rol> implements RolDAO{
    
    public RolDAO_impl(EntityManager em) {
        super(Rol.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    @Override
    public Rol find(String rolName) {
        Query q = this.em.createNamedQuery("Rol.findByName");
        q.setParameter("nombre", rolName);
        Rol res;
        try {
            res = (Rol)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}
