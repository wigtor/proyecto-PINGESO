/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO_impl;

import DAO_impl.exceptions.NonexistentEntityException;
import DAO_impl.exceptions.RollbackFailureException;
import entities.Inspector;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.SolicitudMantencion;
import java.util.ArrayList;
import java.util.List;
import entities.RevisionPuntoLimpio;
import entities.PuntoLimpio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author victor
 */
public class InspectorJpaController implements Serializable {

    public InspectorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inspector inspector) throws RollbackFailureException, Exception {
        if (inspector.getSolicitudesMantencionRealizadas() == null) {
            inspector.setSolicitudesMantencionRealizadas(new ArrayList<SolicitudMantencion>());
        }
        if (inspector.getRevisionesRealizadas() == null) {
            inspector.setRevisionesRealizadas(new ArrayList<RevisionPuntoLimpio>());
        }
        if (inspector.getPuntosLimpios() == null) {
            inspector.setPuntosLimpios(new ArrayList<PuntoLimpio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<SolicitudMantencion> attachedSolicitudesMantencionRealizadas = new ArrayList<SolicitudMantencion>();
            for (SolicitudMantencion solicitudesMantencionRealizadasSolicitudMantencionToAttach : inspector.getSolicitudesMantencionRealizadas()) {
                solicitudesMantencionRealizadasSolicitudMantencionToAttach = em.getReference(solicitudesMantencionRealizadasSolicitudMantencionToAttach.getClass(), solicitudesMantencionRealizadasSolicitudMantencionToAttach.getNum());
                attachedSolicitudesMantencionRealizadas.add(solicitudesMantencionRealizadasSolicitudMantencionToAttach);
            }
            inspector.setSolicitudesMantencionRealizadas(attachedSolicitudesMantencionRealizadas);
            List<RevisionPuntoLimpio> attachedRevisionesRealizadas = new ArrayList<RevisionPuntoLimpio>();
            for (RevisionPuntoLimpio revisionesRealizadasRevisionPuntoLimpioToAttach : inspector.getRevisionesRealizadas()) {
                revisionesRealizadasRevisionPuntoLimpioToAttach = em.getReference(revisionesRealizadasRevisionPuntoLimpioToAttach.getClass(), revisionesRealizadasRevisionPuntoLimpioToAttach.getNum());
                attachedRevisionesRealizadas.add(revisionesRealizadasRevisionPuntoLimpioToAttach);
            }
            inspector.setRevisionesRealizadas(attachedRevisionesRealizadas);
            List<PuntoLimpio> attachedPuntosLimpios = new ArrayList<PuntoLimpio>();
            for (PuntoLimpio puntosLimpiosPuntoLimpioToAttach : inspector.getPuntosLimpios()) {
                puntosLimpiosPuntoLimpioToAttach = em.getReference(puntosLimpiosPuntoLimpioToAttach.getClass(), puntosLimpiosPuntoLimpioToAttach.getId());
                attachedPuntosLimpios.add(puntosLimpiosPuntoLimpioToAttach);
            }
            inspector.setPuntosLimpios(attachedPuntosLimpios);
            em.persist(inspector);
            for (SolicitudMantencion solicitudesMantencionRealizadasSolicitudMantencion : inspector.getSolicitudesMantencionRealizadas()) {
                Inspector oldInspectorSolicitanteOfSolicitudesMantencionRealizadasSolicitudMantencion = solicitudesMantencionRealizadasSolicitudMantencion.getInspectorSolicitante();
                solicitudesMantencionRealizadasSolicitudMantencion.setInspectorSolicitante(inspector);
                solicitudesMantencionRealizadasSolicitudMantencion = em.merge(solicitudesMantencionRealizadasSolicitudMantencion);
                if (oldInspectorSolicitanteOfSolicitudesMantencionRealizadasSolicitudMantencion != null) {
                    oldInspectorSolicitanteOfSolicitudesMantencionRealizadasSolicitudMantencion.getSolicitudesMantencionRealizadas().remove(solicitudesMantencionRealizadasSolicitudMantencion);
                    oldInspectorSolicitanteOfSolicitudesMantencionRealizadasSolicitudMantencion = em.merge(oldInspectorSolicitanteOfSolicitudesMantencionRealizadasSolicitudMantencion);
                }
            }
            for (RevisionPuntoLimpio revisionesRealizadasRevisionPuntoLimpio : inspector.getRevisionesRealizadas()) {
                Inspector oldInspectorRevisorOfRevisionesRealizadasRevisionPuntoLimpio = revisionesRealizadasRevisionPuntoLimpio.getInspectorRevisor();
                revisionesRealizadasRevisionPuntoLimpio.setInspectorRevisor(inspector);
                revisionesRealizadasRevisionPuntoLimpio = em.merge(revisionesRealizadasRevisionPuntoLimpio);
                if (oldInspectorRevisorOfRevisionesRealizadasRevisionPuntoLimpio != null) {
                    oldInspectorRevisorOfRevisionesRealizadasRevisionPuntoLimpio.getRevisionesRealizadas().remove(revisionesRealizadasRevisionPuntoLimpio);
                    oldInspectorRevisorOfRevisionesRealizadasRevisionPuntoLimpio = em.merge(oldInspectorRevisorOfRevisionesRealizadasRevisionPuntoLimpio);
                }
            }
            for (PuntoLimpio puntosLimpiosPuntoLimpio : inspector.getPuntosLimpios()) {
                Inspector oldInspectorEncargadoOfPuntosLimpiosPuntoLimpio = puntosLimpiosPuntoLimpio.getInspectorEncargado();
                puntosLimpiosPuntoLimpio.setInspectorEncargado(inspector);
                puntosLimpiosPuntoLimpio = em.merge(puntosLimpiosPuntoLimpio);
                if (oldInspectorEncargadoOfPuntosLimpiosPuntoLimpio != null) {
                    oldInspectorEncargadoOfPuntosLimpiosPuntoLimpio.getPuntosLimpios().remove(puntosLimpiosPuntoLimpio);
                    oldInspectorEncargadoOfPuntosLimpiosPuntoLimpio = em.merge(oldInspectorEncargadoOfPuntosLimpiosPuntoLimpio);
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

    public void edit(Inspector inspector) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Inspector persistentInspector = em.find(Inspector.class, inspector.getId());
            List<SolicitudMantencion> solicitudesMantencionRealizadasOld = persistentInspector.getSolicitudesMantencionRealizadas();
            List<SolicitudMantencion> solicitudesMantencionRealizadasNew = inspector.getSolicitudesMantencionRealizadas();
            List<RevisionPuntoLimpio> revisionesRealizadasOld = persistentInspector.getRevisionesRealizadas();
            List<RevisionPuntoLimpio> revisionesRealizadasNew = inspector.getRevisionesRealizadas();
            List<PuntoLimpio> puntosLimpiosOld = persistentInspector.getPuntosLimpios();
            List<PuntoLimpio> puntosLimpiosNew = inspector.getPuntosLimpios();
            List<SolicitudMantencion> attachedSolicitudesMantencionRealizadasNew = new ArrayList<SolicitudMantencion>();
            for (SolicitudMantencion solicitudesMantencionRealizadasNewSolicitudMantencionToAttach : solicitudesMantencionRealizadasNew) {
                solicitudesMantencionRealizadasNewSolicitudMantencionToAttach = em.getReference(solicitudesMantencionRealizadasNewSolicitudMantencionToAttach.getClass(), solicitudesMantencionRealizadasNewSolicitudMantencionToAttach.getNum());
                attachedSolicitudesMantencionRealizadasNew.add(solicitudesMantencionRealizadasNewSolicitudMantencionToAttach);
            }
            solicitudesMantencionRealizadasNew = attachedSolicitudesMantencionRealizadasNew;
            inspector.setSolicitudesMantencionRealizadas(solicitudesMantencionRealizadasNew);
            List<RevisionPuntoLimpio> attachedRevisionesRealizadasNew = new ArrayList<RevisionPuntoLimpio>();
            for (RevisionPuntoLimpio revisionesRealizadasNewRevisionPuntoLimpioToAttach : revisionesRealizadasNew) {
                revisionesRealizadasNewRevisionPuntoLimpioToAttach = em.getReference(revisionesRealizadasNewRevisionPuntoLimpioToAttach.getClass(), revisionesRealizadasNewRevisionPuntoLimpioToAttach.getNum());
                attachedRevisionesRealizadasNew.add(revisionesRealizadasNewRevisionPuntoLimpioToAttach);
            }
            revisionesRealizadasNew = attachedRevisionesRealizadasNew;
            inspector.setRevisionesRealizadas(revisionesRealizadasNew);
            List<PuntoLimpio> attachedPuntosLimpiosNew = new ArrayList<PuntoLimpio>();
            for (PuntoLimpio puntosLimpiosNewPuntoLimpioToAttach : puntosLimpiosNew) {
                puntosLimpiosNewPuntoLimpioToAttach = em.getReference(puntosLimpiosNewPuntoLimpioToAttach.getClass(), puntosLimpiosNewPuntoLimpioToAttach.getId());
                attachedPuntosLimpiosNew.add(puntosLimpiosNewPuntoLimpioToAttach);
            }
            puntosLimpiosNew = attachedPuntosLimpiosNew;
            inspector.setPuntosLimpios(puntosLimpiosNew);
            inspector = em.merge(inspector);
            for (SolicitudMantencion solicitudesMantencionRealizadasOldSolicitudMantencion : solicitudesMantencionRealizadasOld) {
                if (!solicitudesMantencionRealizadasNew.contains(solicitudesMantencionRealizadasOldSolicitudMantencion)) {
                    solicitudesMantencionRealizadasOldSolicitudMantencion.setInspectorSolicitante(null);
                    solicitudesMantencionRealizadasOldSolicitudMantencion = em.merge(solicitudesMantencionRealizadasOldSolicitudMantencion);
                }
            }
            for (SolicitudMantencion solicitudesMantencionRealizadasNewSolicitudMantencion : solicitudesMantencionRealizadasNew) {
                if (!solicitudesMantencionRealizadasOld.contains(solicitudesMantencionRealizadasNewSolicitudMantencion)) {
                    Inspector oldInspectorSolicitanteOfSolicitudesMantencionRealizadasNewSolicitudMantencion = solicitudesMantencionRealizadasNewSolicitudMantencion.getInspectorSolicitante();
                    solicitudesMantencionRealizadasNewSolicitudMantencion.setInspectorSolicitante(inspector);
                    solicitudesMantencionRealizadasNewSolicitudMantencion = em.merge(solicitudesMantencionRealizadasNewSolicitudMantencion);
                    if (oldInspectorSolicitanteOfSolicitudesMantencionRealizadasNewSolicitudMantencion != null && !oldInspectorSolicitanteOfSolicitudesMantencionRealizadasNewSolicitudMantencion.equals(inspector)) {
                        oldInspectorSolicitanteOfSolicitudesMantencionRealizadasNewSolicitudMantencion.getSolicitudesMantencionRealizadas().remove(solicitudesMantencionRealizadasNewSolicitudMantencion);
                        oldInspectorSolicitanteOfSolicitudesMantencionRealizadasNewSolicitudMantencion = em.merge(oldInspectorSolicitanteOfSolicitudesMantencionRealizadasNewSolicitudMantencion);
                    }
                }
            }
            for (RevisionPuntoLimpio revisionesRealizadasOldRevisionPuntoLimpio : revisionesRealizadasOld) {
                if (!revisionesRealizadasNew.contains(revisionesRealizadasOldRevisionPuntoLimpio)) {
                    revisionesRealizadasOldRevisionPuntoLimpio.setInspectorRevisor(null);
                    revisionesRealizadasOldRevisionPuntoLimpio = em.merge(revisionesRealizadasOldRevisionPuntoLimpio);
                }
            }
            for (RevisionPuntoLimpio revisionesRealizadasNewRevisionPuntoLimpio : revisionesRealizadasNew) {
                if (!revisionesRealizadasOld.contains(revisionesRealizadasNewRevisionPuntoLimpio)) {
                    Inspector oldInspectorRevisorOfRevisionesRealizadasNewRevisionPuntoLimpio = revisionesRealizadasNewRevisionPuntoLimpio.getInspectorRevisor();
                    revisionesRealizadasNewRevisionPuntoLimpio.setInspectorRevisor(inspector);
                    revisionesRealizadasNewRevisionPuntoLimpio = em.merge(revisionesRealizadasNewRevisionPuntoLimpio);
                    if (oldInspectorRevisorOfRevisionesRealizadasNewRevisionPuntoLimpio != null && !oldInspectorRevisorOfRevisionesRealizadasNewRevisionPuntoLimpio.equals(inspector)) {
                        oldInspectorRevisorOfRevisionesRealizadasNewRevisionPuntoLimpio.getRevisionesRealizadas().remove(revisionesRealizadasNewRevisionPuntoLimpio);
                        oldInspectorRevisorOfRevisionesRealizadasNewRevisionPuntoLimpio = em.merge(oldInspectorRevisorOfRevisionesRealizadasNewRevisionPuntoLimpio);
                    }
                }
            }
            for (PuntoLimpio puntosLimpiosOldPuntoLimpio : puntosLimpiosOld) {
                if (!puntosLimpiosNew.contains(puntosLimpiosOldPuntoLimpio)) {
                    puntosLimpiosOldPuntoLimpio.setInspectorEncargado(null);
                    puntosLimpiosOldPuntoLimpio = em.merge(puntosLimpiosOldPuntoLimpio);
                }
            }
            for (PuntoLimpio puntosLimpiosNewPuntoLimpio : puntosLimpiosNew) {
                if (!puntosLimpiosOld.contains(puntosLimpiosNewPuntoLimpio)) {
                    Inspector oldInspectorEncargadoOfPuntosLimpiosNewPuntoLimpio = puntosLimpiosNewPuntoLimpio.getInspectorEncargado();
                    puntosLimpiosNewPuntoLimpio.setInspectorEncargado(inspector);
                    puntosLimpiosNewPuntoLimpio = em.merge(puntosLimpiosNewPuntoLimpio);
                    if (oldInspectorEncargadoOfPuntosLimpiosNewPuntoLimpio != null && !oldInspectorEncargadoOfPuntosLimpiosNewPuntoLimpio.equals(inspector)) {
                        oldInspectorEncargadoOfPuntosLimpiosNewPuntoLimpio.getPuntosLimpios().remove(puntosLimpiosNewPuntoLimpio);
                        oldInspectorEncargadoOfPuntosLimpiosNewPuntoLimpio = em.merge(oldInspectorEncargadoOfPuntosLimpiosNewPuntoLimpio);
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
                Long id = inspector.getId();
                if (findInspector(id) == null) {
                    throw new NonexistentEntityException("The inspector with id " + id + " no longer exists.");
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
            Inspector inspector;
            try {
                inspector = em.getReference(Inspector.class, id);
                inspector.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inspector with id " + id + " no longer exists.", enfe);
            }
            List<SolicitudMantencion> solicitudesMantencionRealizadas = inspector.getSolicitudesMantencionRealizadas();
            for (SolicitudMantencion solicitudesMantencionRealizadasSolicitudMantencion : solicitudesMantencionRealizadas) {
                solicitudesMantencionRealizadasSolicitudMantencion.setInspectorSolicitante(null);
                solicitudesMantencionRealizadasSolicitudMantencion = em.merge(solicitudesMantencionRealizadasSolicitudMantencion);
            }
            List<RevisionPuntoLimpio> revisionesRealizadas = inspector.getRevisionesRealizadas();
            for (RevisionPuntoLimpio revisionesRealizadasRevisionPuntoLimpio : revisionesRealizadas) {
                revisionesRealizadasRevisionPuntoLimpio.setInspectorRevisor(null);
                revisionesRealizadasRevisionPuntoLimpio = em.merge(revisionesRealizadasRevisionPuntoLimpio);
            }
            List<PuntoLimpio> puntosLimpios = inspector.getPuntosLimpios();
            for (PuntoLimpio puntosLimpiosPuntoLimpio : puntosLimpios) {
                puntosLimpiosPuntoLimpio.setInspectorEncargado(null);
                puntosLimpiosPuntoLimpio = em.merge(puntosLimpiosPuntoLimpio);
            }
            em.remove(inspector);
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

    public List<Inspector> findInspectorEntities() {
        return findInspectorEntities(true, -1, -1);
    }

    public List<Inspector> findInspectorEntities(int maxResults, int firstResult) {
        return findInspectorEntities(false, maxResults, firstResult);
    }

    private List<Inspector> findInspectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inspector.class));
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

    public Inspector findInspector(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inspector.class, id);
        } finally {
            em.close();
        }
    }

    public int getInspectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inspector> rt = cq.from(Inspector.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
