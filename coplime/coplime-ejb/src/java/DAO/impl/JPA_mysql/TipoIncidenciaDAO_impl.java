/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.TipoIncidenciaDAO;
import entities.TipoIncidencia;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        TipoIncidencia res;
        try {
            res = (TipoIncidencia)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
    
    @Override
    public Collection<TipoIncidencia> findAllVisibles() {
        Query q = this.em.createNamedQuery("TipoIncidencia.findAllVisibles");
        Collection<TipoIncidencia> res = (Collection<TipoIncidencia>)q.getResultList();
        return res;
    }
}
