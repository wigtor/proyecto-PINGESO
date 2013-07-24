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
    public PuntoLimpio findByNum(int num) {
        Query q = this.em.createNamedQuery("PuntoLimpio.findByNum");
        q.setParameter("num", num);
        List<PuntoLimpio> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
    
    @Override
    public boolean deleteByNum(int num) {
        //Obtengo el inspector
        Query q = this.em.createNamedQuery("Inspector.deleteByNum");
        q.setParameter("num", num);
        List<PuntoLimpio> res = q.getResultList();
        if (res.isEmpty()) {
            return false;
        }
        PuntoLimpio ptoToDelete = res.get(0);
        
        /*
        //Actualizo los puntos limpios para desligarlos del inspector
        List<PuntoLimpio> listaPtos = inspToDelete.getPuntosLimpios();
        for(PuntoLimpio p : listaPtos) {
            p.setInspectorEncargado(null);
            getEntityManager().merge(p);
        }
        */
        
        //Borro el inspector
        getEntityManager().remove(ptoToDelete);
        return true;
    }
}
