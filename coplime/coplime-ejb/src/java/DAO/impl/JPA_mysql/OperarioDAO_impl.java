/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.OperarioDAO;
import entities.MantencionPuntoLimpio;
import entities.OperarioMantencion;
import entities.SolicitudMantencion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class OperarioDAO_impl extends genericDAO_impl<OperarioMantencion> implements OperarioDAO {
    public OperarioDAO_impl(EntityManager em) {
        super(OperarioMantencion.class);
        this.em = em;
    }
    
    @Override
    public boolean deleteByRut(int rut) {
        //Obtengo el inspector
        Query q = this.em.createNamedQuery("OperarioMantencion.findByRut");
        q.setParameter("rut", rut);
        List<OperarioMantencion> res = (List<OperarioMantencion>)q.getResultList();
        if (res.isEmpty()) {
            return false;
        }
        OperarioMantencion operarioToDelete = res.get(0);
        
        //Actualizo las solicitudes de mantención para desligarlos del inspector
        List<SolicitudMantencion> listaSolic = operarioToDelete.getSolicitudesMantencionRealizadas();
        for(SolicitudMantencion p : listaSolic) {
            p.setInspectorSolicitante(null);
            getEntityManager().merge(p);
        }
        
        //Actualizo los puntos limpios para desligarlos del inspector
        List<MantencionPuntoLimpio> listaMant = operarioToDelete.getMantencionesRealizadas();
        for(MantencionPuntoLimpio p : listaMant) {
            p.setOperarioMantencion(null);
            getEntityManager().merge(p);
        }
        
        //Borro el inspector
        getEntityManager().remove(operarioToDelete);
        return true;
    }
    
    @Override
    public OperarioMantencion findByUsername(String username) {
        Query q = this.em.createNamedQuery("OperarioMantencion.findByUsername");
        q.setParameter("username", username);
        OperarioMantencion res;
        try {
            res = (OperarioMantencion)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }

    @Override
    public OperarioMantencion findByRut(int rut) {
        Query q = this.em.createNamedQuery("OperarioMantencion.findByRut");
        q.setParameter("rut", rut);
        OperarioMantencion res;
        try {
            res = (OperarioMantencion)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}
