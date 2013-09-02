/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.ComunaDAO;
import entities.Comuna;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class ComunaDAO_impl extends genericDAO_impl<Comuna> implements ComunaDAO{
    
    public ComunaDAO_impl(EntityManager em) {
        super(Comuna.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    @Override
    public Comuna buscarPorNombre(String comunaName) {
        Query q = this.em.createNamedQuery("Comuna.findByName");
        q.setParameter("nombre", comunaName);
        List<Comuna> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
}
