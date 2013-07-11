/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.TipoIncidenciaDAO;
import entities.TipoIncidencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class TipoIncidenciaDAO_impl extends genericDAO_impl<TipoIncidencia> implements TipoIncidenciaDAO{
    
    public TipoIncidenciaDAO_impl(EntityManager em) {
        super(TipoIncidencia.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    @Override
    public TipoIncidencia find(String tipoName) {
        Query q = this.em.createNamedQuery("TipoIncidencia.findByName");
        q.setParameter("nombre", tipoName);
        List<TipoIncidencia> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
}
