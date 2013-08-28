/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.RevisionDAO;
import entities.RevisionPuntoLimpio;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class RevisionDAO_impl extends genericDAO_impl<RevisionPuntoLimpio> implements RevisionDAO{
    
    public RevisionDAO_impl(EntityManager em) {
        super(RevisionPuntoLimpio.class);
        this.em = em;
    }

    @Override
    public Collection<RevisionPuntoLimpio> findAllFromInspector(Integer idUsuario) {
        if (idUsuario == null) 
            return new LinkedList<>();
        Query q = this.em.createNamedQuery("RevisionPuntoLimpio.findByInspector");
        q.setParameter("idUsuario", idUsuario);
        Collection<RevisionPuntoLimpio> res = (Collection<RevisionPuntoLimpio>)q.getResultList();
        return res;
    }

    @Override
    public Collection<RevisionPuntoLimpio> findByDateRange(Calendar fechaIni, Calendar fechaFin) {
        if (fechaIni == null) {
            return new LinkedList<>();
        }
        if (fechaFin == null) {
            return new LinkedList<>();
        }
        Query q = this.em.createNamedQuery("RevisionPuntoLimpio.findByDateRange");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        Collection<RevisionPuntoLimpio> res = (Collection<RevisionPuntoLimpio>)q.getResultList();
        return res;
    }
    
}
