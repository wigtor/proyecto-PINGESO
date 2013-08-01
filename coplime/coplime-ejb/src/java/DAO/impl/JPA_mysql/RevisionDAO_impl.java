/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.RevisionDAO;
import entities.RevisionPuntoLimpio;
import java.util.Collection;
import java.util.List;
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
        Query q = this.em.createNamedQuery("RevisionPuntoLimpio.findByInspector");
        q.setParameter("idUsuario", idUsuario);
        List<RevisionPuntoLimpio> res = q.getResultList();
        return res;
    }
    
}
