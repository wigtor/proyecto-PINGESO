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
import entities.SolicitudMantencion;
import java.util.ArrayList;
import java.util.List;
import entities.MantencionPuntoLimpio;
import entities.OperarioMantencion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author victor
 */
public class OperarioMantencionJpaController implements Serializable {

    public OperarioMantencionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OperarioMantencion operarioMantencion) throws RollbackFailureException, Exception {
        if (operarioMantencion.getSolicitudesMantencionRealizadas() == null) {
            operarioMantencion.setSolicitudesMantencionRealizadas(new ArrayList<SolicitudMantencion>());
        }
        if (operarioMantencion.getMantencionesRealizadas() == null) {
            operarioMantencion.setMantencionesRealizadas(new ArrayList<MantencionPuntoLimpio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<SolicitudMantencion> attachedSolicitudesMantencionRealizadas = new ArrayList<SolicitudMantencion>();
            for (SolicitudMantencion solicitudesMantencionRealizadasSolicitudMantencionToAttach : operarioMantencion.getSolicitudesMantencionRealizadas()) {
                solicitudesMantencionRealizadasSolicitudMantencionToAttach = em.getReference(solicitudesMantencionRealizadasSolicitudMantencionToAttach.getClass(), solicitudesMantencionRealizadasSolicitudMantencionToAttach.getNum());
                attachedSolicitudesMantencionRealizadas.add(solicitudesMantencionRealizadasSolicitudMantencionToAttach);
            }
            operarioMantencion.setSolicitudesMantencionRealizadas(attachedSolicitudesMantencionRealizadas);
            List<MantencionPuntoLimpio> attachedMantencionesRealizadas = new ArrayList<MantencionPuntoLimpio>();
            for (MantencionPuntoLimpio mantencionesRealizadasMantencionPuntoLimpioToAttach : operarioMantencion.getMantencionesRealizadas()) {
                mantencionesRealizadasMantencionPuntoLimpioToAttach = em.getReference(mantencionesRealizadasMantencionPuntoLimpioToAttach.getClass(), mantencionesRealizadasMantencionPuntoLimpioToAttach.getNum());
                attachedMantencionesRealizadas.add(mantencionesRealizadasMantencionPuntoLimpioToAttach);
            }
            operarioMantencion.setMantencionesRealizadas(attachedMantencionesRealizadas);
            em.persist(operarioMantencion);
            for (SolicitudMantencion solicitudesMantencionRealizadasSolicitudMantencion : operarioMantencion.getSolicitudesMantencionRealizadas()) {
                OperarioMantencion oldOperarioAsignadoOfSolicitudesMantencionRealizadasSolicitudMantencion = solicitudesMantencionRealizadasSolicitudMantencion.getOperarioAsignado();
                solicitudesMantencionRealizadasSolicitudMantencion.setOperarioAsignado(operarioMantencion);
                solicitudesMantencionRealizadasSolicitudMantencion = em.merge(solicitudesMantencionRealizadasSolicitudMantencion);
                if (oldOperarioAsignadoOfSolicitudesMantencionRealizadasSolicitudMantencion != null) {
                    oldOperarioAsignadoOfSolicitudesMantencionRealizadasSolicitudMantencion.getSolicitudesMantencionRealizadas().remove(solicitudesMantencionRealizadasSolicitudMantencion);
                    oldOperarioAsignadoOfSolicitudesMantencionRealizadasSolicitudMantencion = em.merge(oldOperarioAsignadoOfSolicitudesMantencionRealizadasSolicitudMantencion);
                }
            }
            for (MantencionPuntoLimpio mantencionesRealizadasMantencionPuntoLimpio : operarioMantencion.getMantencionesRealizadas()) {
                OperarioMantencion oldOperarioMantencionOfMantencionesRealizadasMantencionPuntoLimpio = mantencionesRealizadasMantencionPuntoLimpio.getOperarioMantencion();
                mantencionesRealizadasMantencionPuntoLimpio.setOperarioMantencion(operarioMantencion);
                mantencionesRealizadasMantencionPuntoLimpio = em.merge(mantencionesRealizadasMantencionPuntoLimpio);
                if (oldOperarioMantencionOfMantencionesRealizadasMantencionPuntoLimpio != null) {
                    oldOperarioMantencionOfMantencionesRealizadasMantencionPuntoLimpio.getMantencionesRealizadas().remove(mantencionesRealizadasMantencionPuntoLimpio);
                    oldOperarioMantencionOfMantencionesRealizadasMantencionPuntoLimpio = em.merge(oldOperarioMantencionOfMantencionesRealizadasMantencionPuntoLimpio);
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

    public void edit(OperarioMantencion operarioMantencion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OperarioMantencion persistentOperarioMantencion = em.find(OperarioMantencion.class, operarioMantencion.getId());
            List<SolicitudMantencion> solicitudesMantencionRealizadasOld = persistentOperarioMantencion.getSolicitudesMantencionRealizadas();
            List<SolicitudMantencion> solicitudesMantencionRealizadasNew = operarioMantencion.getSolicitudesMantencionRealizadas();
            List<MantencionPuntoLimpio> mantencionesRealizadasOld = persistentOperarioMantencion.getMantencionesRealizadas();
            List<MantencionPuntoLimpio> mantencionesRealizadasNew = operarioMantencion.getMantencionesRealizadas();
            List<SolicitudMantencion> attachedSolicitudesMantencionRealizadasNew = new ArrayList<SolicitudMantencion>();
            for (SolicitudMantencion solicitudesMantencionRealizadasNewSolicitudMantencionToAttach : solicitudesMantencionRealizadasNew) {
                solicitudesMantencionRealizadasNewSolicitudMantencionToAttach = em.getReference(solicitudesMantencionRealizadasNewSolicitudMantencionToAttach.getClass(), solicitudesMantencionRealizadasNewSolicitudMantencionToAttach.getNum());
                attachedSolicitudesMantencionRealizadasNew.add(solicitudesMantencionRealizadasNewSolicitudMantencionToAttach);
            }
            solicitudesMantencionRealizadasNew = attachedSolicitudesMantencionRealizadasNew;
            operarioMantencion.setSolicitudesMantencionRealizadas(solicitudesMantencionRealizadasNew);
            List<MantencionPuntoLimpio> attachedMantencionesRealizadasNew = new ArrayList<MantencionPuntoLimpio>();
            for (MantencionPuntoLimpio mantencionesRealizadasNewMantencionPuntoLimpioToAttach : mantencionesRealizadasNew) {
                mantencionesRealizadasNewMantencionPuntoLimpioToAttach = em.getReference(mantencionesRealizadasNewMantencionPuntoLimpioToAttach.getClass(), mantencionesRealizadasNewMantencionPuntoLimpioToAttach.getNum());
                attachedMantencionesRealizadasNew.add(mantencionesRealizadasNewMantencionPuntoLimpioToAttach);
            }
            mantencionesRealizadasNew = attachedMantencionesRealizadasNew;
            operarioMantencion.setMantencionesRealizadas(mantencionesRealizadasNew);
            operarioMantencion = em.merge(operarioMantencion);
            for (SolicitudMantencion solicitudesMantencionRealizadasOldSolicitudMantencion : solicitudesMantencionRealizadasOld) {
                if (!solicitudesMantencionRealizadasNew.contains(solicitudesMantencionRealizadasOldSolicitudMantencion)) {
                    solicitudesMantencionRealizadasOldSolicitudMantencion.setOperarioAsignado(null);
                    solicitudesMantencionRealizadasOldSolicitudMantencion = em.merge(solicitudesMantencionRealizadasOldSolicitudMantencion);
                }
            }
            for (SolicitudMantencion solicitudesMantencionRealizadasNewSolicitudMantencion : solicitudesMantencionRealizadasNew) {
                if (!solicitudesMantencionRealizadasOld.contains(solicitudesMantencionRealizadasNewSolicitudMantencion)) {
                    OperarioMantencion oldOperarioAsignadoOfSolicitudesMantencionRealizadasNewSolicitudMantencion = solicitudesMantencionRealizadasNewSolicitudMantencion.getOperarioAsignado();
                    solicitudesMantencionRealizadasNewSolicitudMantencion.setOperarioAsignado(operarioMantencion);
                    solicitudesMantencionRealizadasNewSolicitudMantencion = em.merge(solicitudesMantencionRealizadasNewSolicitudMantencion);
                    if (oldOperarioAsignadoOfSolicitudesMantencionRealizadasNewSolicitudMantencion != null && !oldOperarioAsignadoOfSolicitudesMantencionRealizadasNewSolicitudMantencion.equals(operarioMantencion)) {
                        oldOperarioAsignadoOfSolicitudesMantencionRealizadasNewSolicitudMantencion.getSolicitudesMantencionRealizadas().remove(solicitudesMantencionRealizadasNewSolicitudMantencion);
                        oldOperarioAsignadoOfSolicitudesMantencionRealizadasNewSolicitudMantencion = em.merge(oldOperarioAsignadoOfSolicitudesMantencionRealizadasNewSolicitudMantencion);
                    }
                }
            }
            for (MantencionPuntoLimpio mantencionesRealizadasOldMantencionPuntoLimpio : mantencionesRealizadasOld) {
                if (!mantencionesRealizadasNew.contains(mantencionesRealizadasOldMantencionPuntoLimpio)) {
                    mantencionesRealizadasOldMantencionPuntoLimpio.setOperarioMantencion(null);
                    mantencionesRealizadasOldMantencionPuntoLimpio = em.merge(mantencionesRealizadasOldMantencionPuntoLimpio);
                }
            }
            for (MantencionPuntoLimpio mantencionesRealizadasNewMantencionPuntoLimpio : mantencionesRealizadasNew) {
                if (!mantencionesRealizadasOld.contains(mantencionesRealizadasNewMantencionPuntoLimpio)) {
                    OperarioMantencion oldOperarioMantencionOfMantencionesRealizadasNewMantencionPuntoLimpio = mantencionesRealizadasNewMantencionPuntoLimpio.getOperarioMantencion();
                    mantencionesRealizadasNewMantencionPuntoLimpio.setOperarioMantencion(operarioMantencion);
                    mantencionesRealizadasNewMantencionPuntoLimpio = em.merge(mantencionesRealizadasNewMantencionPuntoLimpio);
                    if (oldOperarioMantencionOfMantencionesRealizadasNewMantencionPuntoLimpio != null && !oldOperarioMantencionOfMantencionesRealizadasNewMantencionPuntoLimpio.equals(operarioMantencion)) {
                        oldOperarioMantencionOfMantencionesRealizadasNewMantencionPuntoLimpio.getMantencionesRealizadas().remove(mantencionesRealizadasNewMantencionPuntoLimpio);
                        oldOperarioMantencionOfMantencionesRealizadasNewMantencionPuntoLimpio = em.merge(oldOperarioMantencionOfMantencionesRealizadasNewMantencionPuntoLimpio);
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
                Long id = operarioMantencion.getId();
                if (findOperarioMantencion(id) == null) {
                    throw new NonexistentEntityException("The operarioMantencion with id " + id + " no longer exists.");
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
            OperarioMantencion operarioMantencion;
            try {
                operarioMantencion = em.getReference(OperarioMantencion.class, id);
                operarioMantencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operarioMantencion with id " + id + " no longer exists.", enfe);
            }
            List<SolicitudMantencion> solicitudesMantencionRealizadas = operarioMantencion.getSolicitudesMantencionRealizadas();
            for (SolicitudMantencion solicitudesMantencionRealizadasSolicitudMantencion : solicitudesMantencionRealizadas) {
                solicitudesMantencionRealizadasSolicitudMantencion.setOperarioAsignado(null);
                solicitudesMantencionRealizadasSolicitudMantencion = em.merge(solicitudesMantencionRealizadasSolicitudMantencion);
            }
            List<MantencionPuntoLimpio> mantencionesRealizadas = operarioMantencion.getMantencionesRealizadas();
            for (MantencionPuntoLimpio mantencionesRealizadasMantencionPuntoLimpio : mantencionesRealizadas) {
                mantencionesRealizadasMantencionPuntoLimpio.setOperarioMantencion(null);
                mantencionesRealizadasMantencionPuntoLimpio = em.merge(mantencionesRealizadasMantencionPuntoLimpio);
            }
            em.remove(operarioMantencion);
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

    public List<OperarioMantencion> findOperarioMantencionEntities() {
        return findOperarioMantencionEntities(true, -1, -1);
    }

    public List<OperarioMantencion> findOperarioMantencionEntities(int maxResults, int firstResult) {
        return findOperarioMantencionEntities(false, maxResults, firstResult);
    }

    private List<OperarioMantencion> findOperarioMantencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OperarioMantencion.class));
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

    public OperarioMantencion findOperarioMantencion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OperarioMantencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperarioMantencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OperarioMantencion> rt = cq.from(OperarioMantencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
