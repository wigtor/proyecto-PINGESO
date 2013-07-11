/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.EstadoDAO;
import entities.Estado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class EstadoDAO_impl extends genericDAO_impl<Estado> implements EstadoDAO{
    
    public EstadoDAO_impl(EntityManager em) {
        super(Estado.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    @Override
    public Estado find(String estadoName) {
        Query q = this.em.createNamedQuery("Estado.findByName");
        q.setParameter("nombre", estadoName);
        List<Estado> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
}
