/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.UsuarioDAO;
import entities.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author victor
 */

public class UsuarioDAO_impl extends genericDAO_impl<Usuario> implements UsuarioDAO{
    
    
    public UsuarioDAO_impl(EntityManager em) {
        super(Usuario.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    
    @Override
    public Usuario find(String username) {
        Query q = this.em.createNamedQuery("Usuario.findByUsername");
        q.setParameter("username", username);
        Usuario res;
        try {
            res = (Usuario)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
    
    @Override
    public Usuario findByRut(int rut) {
        Query q = this.em.createNamedQuery("Usuario.findByRut");
        q.setParameter("rut", rut);
        Usuario res;
        try {
            res = (Usuario)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
    
    @Override
    public boolean deleteByRut(int rut) {
        boolean resultado = false;
        Query q1 = this.em.createNamedQuery("Usuario.deleteInspectorByRut");
        q1.setParameter("rut", rut);
        if (q1.executeUpdate() > 0) {
            resultado = true;
        }
        Query q2 = this.em.createNamedQuery("Usuario.deleteOperarioByRut");
        q2.setParameter("rut", rut);
        if (q2.executeUpdate() > 0) {
            resultado = true;
        }
        Query q3 = this.em.createNamedQuery("Usuario.deleteAdministradorByRut");
        q3.setParameter("rut", rut);
        if (q3.executeUpdate() > 0) {
            resultado = true;
        }
        
        return resultado;
    }
    
    @Override
    public boolean rutExist(Integer rut) {
        Query q1 = this.em.createNamedQuery("Usuario.checkByRut");
        q1.setParameter("rut", rut);
        Long res = (Long)q1.getSingleResult();
        if (res == null) {
            return false;
        }
        if (res.intValue() > 0) {
            return true;
        }
        return false;
    }
    @Override
    public boolean usernameExist(String username) {
        Query q1 = this.em.createNamedQuery("Usuario.checkByUsername");
        q1.setParameter("username", username);
        Long res = (Long)q1.getSingleResult();
        if (res == null) {
            return false;
        }
        if (res.intValue() > 0) {
            return true;
        }
        return false;
    }
}
