/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.UnidadMedidaDAO;
import entities.UnidadMedida;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class UnidadMedidaDAO_impl extends genericDAO_impl<UnidadMedida> implements UnidadMedidaDAO{
    
    public UnidadMedidaDAO_impl(EntityManager em) {
        super(UnidadMedida.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    @Override
    public UnidadMedida find(String unidadName) {
        Query q = this.em.createNamedQuery("UnidadMedida.findByName");
        q.setParameter("nombre", unidadName);
        List<UnidadMedida> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
}
