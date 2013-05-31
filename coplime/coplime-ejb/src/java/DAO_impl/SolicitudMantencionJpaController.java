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
import entities.RevisionPuntoLimpio;
import entities.Inspector;
import entities.PuntoLimpio;
import entities.OperarioMantencion;
import entities.SolicitudMantencion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author victor
 */
public class SolicitudMantencionJpaController implements Serializable {

    public SolicitudMantencionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudMantencion solicitudMantencion) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RevisionPuntoLimpio revisionOriginadora = solicitudMantencion.getRevisionOriginadora();
            if (revisionOriginadora != null) {
                revisionOriginadora = em.getReference(revisionOriginadora.getClass(), revisionOriginadora.getNum());
                solicitudMantencion.setRevisionOriginadora(revisionOriginadora);
            }
            Inspector inspectorSolicitante = solicitudMantencion.getInspectorSolicitante();
            if (inspectorSolicitante != null) {
                inspectorSolicitante = em.getReference(inspectorSolicitante.getClass(), inspectorSolicitante.getId());
                solicitudMantencion.setInspectorSolicitante(inspectorSolicitante);
            }
            PuntoLimpio puntoLimpio = solicitudMantencion.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio = em.getReference(puntoLimpio.getClass(), puntoLimpio.getId());
                solicitudMantencion.setPuntoLimpio(puntoLimpio);
            }
            OperarioMantencion operarioAsignado = solicitudMantencion.getOperarioAsignado();
            if (operarioAsignado != null) {
                operarioAsignado = em.getReference(operarioAsignado.getClass(), operarioAsignado.getId());
                solicitudMantencion.setOperarioAsignado(operarioAsignado);
            }
            em.persist(solicitudMantencion);
            if (revisionOriginadora != null) {
                revisionOriginadora.getSolicitudesPorMantencion().add(solicitudMantencion);
                revisionOriginadora = em.merge(revisionOriginadora);
            }
            if (inspectorSolicitante != null) {
                inspectorSolicitante.getSolicitudesMantencionRealizadas().add(solicitudMantencion);
                inspectorSolicitante = em.merge(inspectorSolicitante);
            }
            if (puntoLimpio != null) {
                puntoLimpio.getSolicitudesMantencion().add(solicitudMantencion);
                puntoLimpio = em.merge(puntoLimpio);
            }
            if (operarioAsignado != null) {
                operarioAsignado.getSolicitudesMantencionRealizadas().add(solicitudMantencion);
                operarioAsignado = em.merge(operarioAsignado);
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

    public void edit(SolicitudMantencion solicitudMantencion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudMantencion persistentSolicitudMantencion = em.find(SolicitudMantencion.class, solicitudMantencion.getNum());
            RevisionPuntoLimpio revisionOriginadoraOld = persistentSolicitudMantencion.getRevisionOriginadora();
            RevisionPuntoLimpio revisionOriginadoraNew = solicitudMantencion.getRevisionOriginadora();
            Inspector inspectorSolicitanteOld = persistentSolicitudMantencion.getInspectorSolicitante();
            Inspector inspectorSolicitanteNew = solicitudMantencion.getInspectorSolicitante();
            PuntoLimpio puntoLimpioOld = persistentSolicitudMantencion.getPuntoLimpio();
            PuntoLimpio puntoLimpioNew = solicitudMantencion.getPuntoLimpio();
            OperarioMantencion operarioAsignadoOld = persistentSolicitudMantencion.getOperarioAsignado();
            OperarioMantencion operarioAsignadoNew = solicitudMantencion.getOperarioAsignado();
            if (revisionOriginadoraNew != null) {
                revisionOriginadoraNew = em.getReference(revisionOriginadoraNew.getClass(), revisionOriginadoraNew.getNum());
                solicitudMantencion.setRevisionOriginadora(revisionOriginadoraNew);
            }
            if (inspectorSolicitanteNew != null) {
                inspectorSolicitanteNew = em.getReference(inspectorSolicitanteNew.getClass(), inspectorSolicitanteNew.getId());
                solicitudMantencion.setInspectorSolicitante(inspectorSolicitanteNew);
            }
            if (puntoLimpioNew != null) {
                puntoLimpioNew = em.getReference(puntoLimpioNew.getClass(), puntoLimpioNew.getId());
                solicitudMantencion.setPuntoLimpio(puntoLimpioNew);
            }
            if (operarioAsignadoNew != null) {
                operarioAsignadoNew = em.getReference(operarioAsignadoNew.getClass(), operarioAsignadoNew.getId());
                solicitudMantencion.setOperarioAsignado(operarioAsignadoNew);
            }
            solicitudMantencion = em.merge(solicitudMantencion);
            if (revisionOriginadoraOld != null && !revisionOriginadoraOld.equals(revisionOriginadoraNew)) {
                revisionOriginadoraOld.getSolicitudesPorMantencion().remove(solicitudMantencion);
                revisionOriginadoraOld = em.merge(revisionOriginadoraOld);
            }
            if (revisionOriginadoraNew != null && !revisionOriginadoraNew.equals(revisionOriginadoraOld)) {
                revisionOriginadoraNew.getSolicitudesPorMantencion().add(solicitudMantencion);
                revisionOriginadoraNew = em.merge(revisionOriginadoraNew);
            }
            if (inspectorSolicitanteOld != null && !inspectorSolicitanteOld.equals(inspectorSolicitanteNew)) {
                inspectorSolicitanteOld.getSolicitudesMantencionRealizadas().remove(solicitudMantencion);
                inspectorSolicitanteOld = em.merge(inspectorSolicitanteOld);
            }
            if (inspectorSolicitanteNew != null && !inspectorSolicitanteNew.equals(inspectorSolicitanteOld)) {
                inspectorSolicitanteNew.getSolicitudesMantencionRealizadas().add(solicitudMantencion);
                inspectorSolicitanteNew = em.merge(inspectorSolicitanteNew);
            }
            if (puntoLimpioOld != null && !puntoLimpioOld.equals(puntoLimpioNew)) {
                puntoLimpioOld.getSolicitudesMantencion().remove(solicitudMantencion);
                puntoLimpioOld = em.merge(puntoLimpioOld);
            }
            if (puntoLimpioNew != null && !puntoLimpioNew.equals(puntoLimpioOld)) {
                puntoLimpioNew.getSolicitudesMantencion().add(solicitudMantencion);
                puntoLimpioNew = em.merge(puntoLimpioNew);
            }
            if (operarioAsignadoOld != null && !operarioAsignadoOld.equals(operarioAsignadoNew)) {
                operarioAsignadoOld.getSolicitudesMantencionRealizadas().remove(solicitudMantencion);
                operarioAsignadoOld = em.merge(operarioAsignadoOld);
            }
            if (operarioAsignadoNew != null && !operarioAsignadoNew.equals(operarioAsignadoOld)) {
                operarioAsignadoNew.getSolicitudesMantencionRealizadas().add(solicitudMantencion);
                operarioAsignadoNew = em.merge(operarioAsignadoNew);
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
                Long id = solicitudMantencion.getNum();
                if (findSolicitudMantencion(id) == null) {
                    throw new NonexistentEntityException("The solicitudMantencion with id " + id + " no longer exists.");
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
            SolicitudMantencion solicitudMantencion;
            try {
                solicitudMantencion = em.getReference(SolicitudMantencion.class, id);
                solicitudMantencion.getNum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudMantencion with id " + id + " no longer exists.", enfe);
            }
            RevisionPuntoLimpio revisionOriginadora = solicitudMantencion.getRevisionOriginadora();
            if (revisionOriginadora != null) {
                revisionOriginadora.getSolicitudesPorMantencion().remove(solicitudMantencion);
                revisionOriginadora = em.merge(revisionOriginadora);
            }
            Inspector inspectorSolicitante = solicitudMantencion.getInspectorSolicitante();
            if (inspectorSolicitante != null) {
                inspectorSolicitante.getSolicitudesMantencionRealizadas().remove(solicitudMantencion);
                inspectorSolicitante = em.merge(inspectorSolicitante);
            }
            PuntoLimpio puntoLimpio = solicitudMantencion.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio.getSolicitudesMantencion().remove(solicitudMantencion);
                puntoLimpio = em.merge(puntoLimpio);
            }
            OperarioMantencion operarioAsignado = solicitudMantencion.getOperarioAsignado();
            if (operarioAsignado != null) {
                operarioAsignado.getSolicitudesMantencionRealizadas().remove(solicitudMantencion);
                operarioAsignado = em.merge(operarioAsignado);
            }
            em.remove(solicitudMantencion);
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

    public List<SolicitudMantencion> findSolicitudMantencionEntities() {
        return findSolicitudMantencionEntities(true, -1, -1);
    }

    public List<SolicitudMantencion> findSolicitudMantencionEntities(int maxResults, int firstResult) {
        return findSolicitudMantencionEntities(false, maxResults, firstResult);
    }

    private List<SolicitudMantencion> findSolicitudMantencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudMantencion.class));
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

    public SolicitudMantencion findSolicitudMantencion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudMantencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudMantencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudMantencion> rt = cq.from(SolicitudMantencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
