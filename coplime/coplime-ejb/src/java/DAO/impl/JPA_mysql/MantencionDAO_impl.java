/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.MantencionDAO;
import entities.MantencionPuntoLimpio;
import java.util.Collection;
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
}
