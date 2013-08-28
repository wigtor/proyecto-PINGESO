/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.MantencionDAO;
import entities.MantencionPuntoLimpio;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class MantencionDAO_impl extends genericDAO_impl<MantencionPuntoLimpio> implements MantencionDAO{
    
    public MantencionDAO_impl(EntityManager em) {
        super(MantencionPuntoLimpio.class);
        this.em = em;
    }
    
    @Override
    public Collection<MantencionPuntoLimpio> findAllFromOperario(Integer idUsuario) {
        Query q = this.em.createNamedQuery("MantencionPuntoLimpio.findByOperario");
        q.setParameter("idUsuario", idUsuario);
        List<MantencionPuntoLimpio> res = (List<MantencionPuntoLimpio>)q.getResultList();
        return res;
    }

    @Override
    public Collection<MantencionPuntoLimpio> findByDateRange(Calendar fechaIni, Calendar fechaFin) {
        if (fechaIni == null) {
            return new LinkedList<>();
        }
        if (fechaFin == null) {
            return new LinkedList<>();
        }
        Query q = this.em.createNamedQuery("MantencionPuntoLimpio.findByDateRange");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        Collection<MantencionPuntoLimpio> res = (Collection<MantencionPuntoLimpio>)q.getResultList();
        return res;
    }
}
