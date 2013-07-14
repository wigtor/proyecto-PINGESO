/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.UsuarioDAO;
import entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
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
    public Usuario find(String username, String password) {
        Query q = this.em.createNamedQuery("Usuario.findByUsernameAndPassword");
        q.setParameter("username", username);
        q.setParameter("password", password);
        List<Usuario> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
    
    @Override
    public Usuario find(String username) {
        Query q = this.em.createNamedQuery("Usuario.findByUsername");
        q.setParameter("username", username);
        List<Usuario> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
    
    @Override
    public Usuario findByRut(int rut) {
        Query q = this.em.createNamedQuery("Usuario.findByRut");
        q.setParameter("rut", rut);
        List<Usuario> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
}
