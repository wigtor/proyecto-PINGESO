/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.PuntoLimpioDAO;
import entities.PuntoLimpio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class PuntoLimpioDAO_impl extends genericDAO_impl<PuntoLimpio> implements PuntoLimpioDAO{
    
    public PuntoLimpioDAO_impl(EntityManager em) {
        super(PuntoLimpio.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    @Override
    public PuntoLimpio find(String puntoLimpioName) {
        Query q = this.em.createNamedQuery("PuntoLimpio.findByName");
        q.setParameter("nombre", puntoLimpioName);
        List<PuntoLimpio> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
    
    @Override
    public boolean numExist(Integer num) {
        Query q1 = this.em.createNamedQuery("PuntoLimpio.checkByNum");
        q1.setParameter("num", num);
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
    public boolean nombreExist(String nombre) {
        Query q1 = this.em.createNamedQuery("PuntoLimpio.checkByNombre");
        q1.setParameter("nombre", nombre);
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
