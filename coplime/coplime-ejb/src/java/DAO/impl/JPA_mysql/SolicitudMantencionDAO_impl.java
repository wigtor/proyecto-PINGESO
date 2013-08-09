/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.SolicitudMantencionDAO;
import entities.SolicitudMantencion;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
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
        Query q = this.em.createNamedQuery("SolicitudMantencion.findByInspector");
        q.setParameter("idUsuario", idUsuario);
        List<SolicitudMantencion> res = q.getResultList();
        return res;
    }
    
    @Override
    public Collection<SolicitudMantencion> findAllFromOperario(Integer idUsuario) {
        Query q = this.em.createNamedQuery("SolicitudMantencion.findByOperario");
        q.setParameter("idUsuario", idUsuario);
        List<SolicitudMantencion> res = q.getResultList();
        return res;
    }
    
}
