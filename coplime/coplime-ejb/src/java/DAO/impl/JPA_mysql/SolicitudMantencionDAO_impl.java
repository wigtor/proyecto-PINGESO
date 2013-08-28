/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.SolicitudMantencionDAO;
import entities.SolicitudMantencion;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class SolicitudMantencionDAO_impl extends genericDAO_impl<SolicitudMantencion> implements SolicitudMantencionDAO{
    
    public SolicitudMantencionDAO_impl(EntityManager em) {
        super(SolicitudMantencion.class);
        this.em = em;
    }

    @Override
    public Collection<SolicitudMantencion> findAllFromInspector(Integer idUsuario) {
        if (idUsuario == null) {
            return new LinkedList<>();
        }
        Query q = this.em.createNamedQuery("SolicitudMantencion.findByInspector");
        q.setParameter("idUsuario", idUsuario);
        Collection<SolicitudMantencion> res = (Collection<SolicitudMantencion>)q.getResultList();
        return res;
    }
    
    @Override
    public Collection<SolicitudMantencion> findAllFromOperario(Integer idUsuario) {
        if (idUsuario == null) {
            return new LinkedList<>();
        }
        Query q = this.em.createNamedQuery("SolicitudMantencion.findByOperario");
        q.setParameter("idUsuario", idUsuario);
        Collection<SolicitudMantencion> res = (Collection<SolicitudMantencion>)q.getResultList();
        return res;
    }
    
    @Override
    public Integer countPorRevisar(String username) {
        Query q = this.em.createNamedQuery("SolicitudMantencion.countNoRevisadasUsuarioDestinatario");
        q.setParameter("username", username);
        
        Long resultado;
        try {
            resultado = (Long)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return 0;
        }
        //System.out.println("Notificaciones por revisar para el usuario con username: "+username+" son:"+resultado);
        return new Integer(resultado.intValue());
    }

    @Override
    public Collection<SolicitudMantencion> findByDateRange(Calendar fechaIni, Calendar fechaFin) {
        if (fechaIni == null) {
            return new LinkedList<>();
        }
        if (fechaFin == null) {
            return new LinkedList<>();
        }
        Query q = this.em.createNamedQuery("SolicitudMantencion.findByDateRange");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        Collection<SolicitudMantencion> res = (Collection<SolicitudMantencion>)q.getResultList();
        return res;
    }
    
}
