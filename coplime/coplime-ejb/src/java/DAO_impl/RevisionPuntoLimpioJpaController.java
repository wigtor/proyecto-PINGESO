/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO_impl;

import DAO_impl.exceptions.NonexistentEntityException;
import DAO_impl.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Inspector;
import entities.PuntoLimpio;
import entities.RevisionPuntoLimpio;
import entities.SolicitudMantencion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author victor
 */
public class RevisionPuntoLimpioJpaController implements Serializable {

    public RevisionPuntoLimpioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RevisionPuntoLimpio revisionPuntoLimpio) throws RollbackFailureException, Exception {
        if (revisionPuntoLimpio.getSolicitudesPorMantencion() == null) {
            revisionPuntoLimpio.setSolicitudesPorMantencion(new ArrayList<SolicitudMantencion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Inspector inspectorRevisor = revisionPuntoLimpio.getInspectorRevisor();
            if (inspectorRevisor != null) {
                inspectorRevisor = em.getReference(inspectorRevisor.getClass(), inspectorRevisor.getId());
                revisionPuntoLimpio.setInspectorRevisor(inspectorRevisor);
            }
            PuntoLimpio puntoLimpio = revisionPuntoLimpio.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio = em.getReference(puntoLimpio.getClass(), puntoLimpio.getId());
                revisionPuntoLimpio.setPuntoLimpio(puntoLimpio);
            }
            List<SolicitudMantencion> attachedSolicitudesPorMantencion = new ArrayList<SolicitudMantencion>();
            for (SolicitudMantencion solicitudesPorMantencionSolicitudMantencionToAttach : revisionPuntoLimpio.getSolicitudesPorMantencion()) {
                solicitudesPorMantencionSolicitudMantencionToAttach = em.getReference(solicitudesPorMantencionSolicitudMantencionToAttach.getClass(), solicitudesPorMantencionSolicitudMantencionToAttach.getNum());
                attachedSolicitudesPorMantencion.add(solicitudesPorMantencionSolicitudMantencionToAttach);
            }
            revisionPuntoLimpio.setSolicitudesPorMantencion(attachedSolicitudesPorMantencion);
            em.persist(revisionPuntoLimpio);
            if (inspectorRevisor != null) {
                inspectorRevisor.getRevisionesRealizadas().add(revisionPuntoLimpio);
                inspectorRevisor = em.merge(inspectorRevisor);
            }
            if (puntoLimpio != null) {
                puntoLimpio.getRevisiones().add(revisionPuntoLimpio);
                puntoLimpio = em.merge(puntoLimpio);
            }
            for (SolicitudMantencion solicitudesPorMantencionSolicitudMantencion : revisionPuntoLimpio.getSolicitudesPorMantencion()) {
                RevisionPuntoLimpio oldRevisionOriginadoraOfSolicitudesPorMantencionSolicitudMantencion = solicitudesPorMantencionSolicitudMantencion.getRevisionOriginadora();
                solicitudesPorMantencionSolicitudMantencion.setRevisionOriginadora(revisionPuntoLimpio);
                solicitudesPorMantencionSolicitudMantencion = em.merge(solicitudesPorMantencionSolicitudMantencion);
                if (oldRevisionOriginadoraOfSolicitudesPorMantencionSolicitudMantencion != null) {
                    oldRevisionOriginadoraOfSolicitudesPorMantencionSolicitudMantencion.getSolicitudesPorMantencion().remove(solicitudesPorMantencionSolicitudMantencion);
                    oldRevisionOriginadoraOfSolicitudesPorMantencionSolicitudMantencion = em.merge(oldRevisionOriginadoraOfSolicitudesPorMantencionSolicitudMantencion);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RevisionPuntoLimpio revisionPuntoLimpio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RevisionPuntoLimpio persistentRevisionPuntoLimpio = em.find(RevisionPuntoLimpio.class, revisionPuntoLimpio.getNum());
            Inspector inspectorRevisorOld = persistentRevisionPuntoLimpio.getInspectorRevisor();
            Inspector inspectorRevisorNew = revisionPuntoLimpio.getInspectorRevisor();
            PuntoLimpio puntoLimpioOld = persistentRevisionPuntoLimpio.getPuntoLimpio();
            PuntoLimpio puntoLimpioNew = revisionPuntoLimpio.getPuntoLimpio();
            List<SolicitudMantencion> solicitudesPorMantencionOld = persistentRevisionPuntoLimpio.getSolicitudesPorMantencion();
            List<SolicitudMantencion> solicitudesPorMantencionNew = revisionPuntoLimpio.getSolicitudesPorMantencion();
            if (inspectorRevisorNew != null) {
                inspectorRevisorNew = em.getReference(inspectorRevisorNew.getClass(), inspectorRevisorNew.getId());
                revisionPuntoLimpio.setInspectorRevisor(inspectorRevisorNew);
            }
            if (puntoLimpioNew != null) {
                puntoLimpioNew = em.getReference(puntoLimpioNew.getClass(), puntoLimpioNew.getId());
                revisionPuntoLimpio.setPuntoLimpio(puntoLimpioNew);
            }
            List<SolicitudMantencion> attachedSolicitudesPorMantencionNew = new ArrayList<SolicitudMantencion>();
            for (SolicitudMantencion solicitudesPorMantencionNewSolicitudMantencionToAttach : solicitudesPorMantencionNew) {
                solicitudesPorMantencionNewSolicitudMantencionToAttach = em.getReference(solicitudesPorMantencionNewSolicitudMantencionToAttach.getClass(), solicitudesPorMantencionNewSolicitudMantencionToAttach.getNum());
                attachedSolicitudesPorMantencionNew.add(solicitudesPorMantencionNewSolicitudMantencionToAttach);
            }
            solicitudesPorMantencionNew = attachedSolicitudesPorMantencionNew;
            revisionPuntoLimpio.setSolicitudesPorMantencion(solicitudesPorMantencionNew);
            revisionPuntoLimpio = em.merge(revisionPuntoLimpio);
            if (inspectorRevisorOld != null && !inspectorRevisorOld.equals(inspectorRevisorNew)) {
                inspectorRevisorOld.getRevisionesRealizadas().remove(revisionPuntoLimpio);
                inspectorRevisorOld = em.merge(inspectorRevisorOld);
            }
            if (inspectorRevisorNew != null && !inspectorRevisorNew.equals(inspectorRevisorOld)) {
                inspectorRevisorNew.getRevisionesRealizadas().add(revisionPuntoLimpio);
                inspectorRevisorNew = em.merge(inspectorRevisorNew);
            }
            if (puntoLimpioOld != null && !puntoLimpioOld.equals(puntoLimpioNew)) {
                puntoLimpioOld.getRevisiones().remove(revisionPuntoLimpio);
                puntoLimpioOld = em.merge(puntoLimpioOld);
            }
            if (puntoLimpioNew != null && !puntoLimpioNew.equals(puntoLimpioOld)) {
                puntoLimpioNew.getRevisiones().add(revisionPuntoLimpio);
                puntoLimpioNew = em.merge(puntoLimpioNew);
            }
            for (SolicitudMantencion solicitudesPorMantencionOldSolicitudMantencion : solicitudesPorMantencionOld) {
                if (!solicitudesPorMantencionNew.contains(solicitudesPorMantencionOldSolicitudMantencion)) {
                    solicitudesPorMantencionOldSolicitudMantencion.setRevisionOriginadora(null);
                    solicitudesPorMantencionOldSolicitudMantencion = em.merge(solicitudesPorMantencionOldSolicitudMantencion);
                }
            }
            for (SolicitudMantencion solicitudesPorMantencionNewSolicitudMantencion : solicitudesPorMantencionNew) {
                if (!solicitudesPorMantencionOld.contains(solicitudesPorMantencionNewSolicitudMantencion)) {
                    RevisionPuntoLimpio oldRevisionOriginadoraOfSolicitudesPorMantencionNewSolicitudMantencion = solicitudesPorMantencionNewSolicitudMantencion.getRevisionOriginadora();
                    solicitudesPorMantencionNewSolicitudMantencion.setRevisionOriginadora(revisionPuntoLimpio);
                    solicitudesPorMantencionNewSolicitudMantencion = em.merge(solicitudesPorMantencionNewSolicitudMantencion);
                    if (oldRevisionOriginadoraOfSolicitudesPorMantencionNewSolicitudMantencion != null && !oldRevisionOriginadoraOfSolicitudesPorMantencionNewSolicitudMantencion.equals(revisionPuntoLimpio)) {
                        oldRevisionOriginadoraOfSolicitudesPorMantencionNewSolicitudMantencion.getSolicitudesPorMantencion().remove(solicitudesPorMantencionNewSolicitudMantencion);
                        oldRevisionOriginadoraOfSolicitudesPorMantencionNewSolicitudMantencion = em.merge(oldRevisionOriginadoraOfSolicitudesPorMantencionNewSolicitudMantencion);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = revisionPuntoLimpio.getNum();
                if (findRevisionPuntoLimpio(id) == null) {
                    throw new NonexistentEntityException("The revisionPuntoLimpio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RevisionPuntoLimpio revisionPuntoLimpio;
            try {
                revisionPuntoLimpio = em.getReference(RevisionPuntoLimpio.class, id);
                revisionPuntoLimpio.getNum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The revisionPuntoLimpio with id " + id + " no longer exists.", enfe);
            }
            Inspector inspectorRevisor = revisionPuntoLimpio.getInspectorRevisor();
            if (inspectorRevisor != null) {
                inspectorRevisor.getRevisionesRealizadas().remove(revisionPuntoLimpio);
                inspectorRevisor = em.merge(inspectorRevisor);
            }
            PuntoLimpio puntoLimpio = revisionPuntoLimpio.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio.getRevisiones().remove(revisionPuntoLimpio);
                puntoLimpio = em.merge(puntoLimpio);
            }
            List<SolicitudMantencion> solicitudesPorMantencion = revisionPuntoLimpio.getSolicitudesPorMantencion();
            for (SolicitudMantencion solicitudesPorMantencionSolicitudMantencion : solicitudesPorMantencion) {
                solicitudesPorMantencionSolicitudMantencion.setRevisionOriginadora(null);
                solicitudesPorMantencionSolicitudMantencion = em.merge(solicitudesPorMantencionSolicitudMantencion);
            }
            em.remove(revisionPuntoLimpio);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RevisionPuntoLimpio> findRevisionPuntoLimpioEntities() {
        return findRevisionPuntoLimpioEntities(true, -1, -1);
    }

    public List<RevisionPuntoLimpio> findRevisionPuntoLimpioEntities(int maxResults, int firstResult) {
        return findRevisionPuntoLimpioEntities(false, maxResults, firstResult);
    }

    private List<RevisionPuntoLimpio> findRevisionPuntoLimpioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RevisionPuntoLimpio.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public RevisionPuntoLimpio findRevisionPuntoLimpio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RevisionPuntoLimpio.class, id);
        } finally {
            em.close();
        }
    }

    public int getRevisionPuntoLimpioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RevisionPuntoLimpio> rt = cq.from(RevisionPuntoLimpio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
