/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.InspectorDAO;
import entities.Inspector;
import entities.PuntoLimpio;
import entities.RevisionPuntoLimpio;
import entities.SolicitudMantencion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class InspectorDAO_impl extends genericDAO_impl<Inspector> implements InspectorDAO {
    public InspectorDAO_impl(EntityManager em) {
        super(Inspector.class);
        this.em = em;
    }
    
    @Override
    public Inspector findByRut(int rut) {
        Query q = this.em.createNamedQuery("Inspector.findByRut");
        q.setParameter("rut", rut);
        List<Inspector> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
    
    @Override
    public boolean deleteByRut(int rut) {
        //Obtengo el inspector
        Query q = this.em.createNamedQuery("Inspector.findByRut");
        q.setParameter("rut", rut);
        List<Inspector> res = q.getResultList();
        if (res.isEmpty()) {
            return false;
        }
        Inspector inspToDelete = res.get(0);
        
        //Actualizo los puntos limpios para desligarlos del inspector
        List<PuntoLimpio> listaPtos = inspToDelete.getPuntosLimpios();
        for(PuntoLimpio p : listaPtos) {
            p.setInspectorEncargado(null);
            getEntityManager().merge(p);
        }
        
        //Actualizo las revisiones para desligarlos del inspector
        List<RevisionPuntoLimpio> listaRev = inspToDelete.getRevisionesRealizadas();
        for(RevisionPuntoLimpio p : listaRev) {
            p.setInspectorRevisor(null);
            getEntityManager().merge(p);
        }
        
        //Actualizo las solicitudes para desligarlos del inspector
        List<SolicitudMantencion> listaSol = inspToDelete.getSolicitudesMantencionRealizadas();
        for(SolicitudMantencion p : listaSol) {
            p.setInspectorSolicitante(null);
            getEntityManager().merge(p);
        }
        
        //Borro el inspector
        getEntityManager().remove(inspToDelete);
        return true;
    }
}
