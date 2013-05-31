/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO_impl;

import DAO_impl.exceptions.NonexistentEntityException;
import DAO_impl.exceptions.RollbackFailureException;
import entities.Notificacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.PuntoLimpio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author victor
 */
public class NotificacionJpaController implements Serializable {

    public NotificacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificacion notificacion) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PuntoLimpio puntoLimpio = notificacion.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio = em.getReference(puntoLimpio.getClass(), puntoLimpio.getId());
                notificacion.setPuntoLimpio(puntoLimpio);
            }
            em.persist(notificacion);
            if (puntoLimpio != null) {
                puntoLimpio.getNotificaciones().add(notificacion);
                puntoLimpio = em.merge(puntoLimpio);
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

    public void edit(Notificacion notificacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Notificacion persistentNotificacion = em.find(Notificacion.class, notificacion.getId());
            PuntoLimpio puntoLimpioOld = persistentNotificacion.getPuntoLimpio();
            PuntoLimpio puntoLimpioNew = notificacion.getPuntoLimpio();
            if (puntoLimpioNew != null) {
                puntoLimpioNew = em.getReference(puntoLimpioNew.getClass(), puntoLimpioNew.getId());
                notificacion.setPuntoLimpio(puntoLimpioNew);
            }
            notificacion = em.merge(notificacion);
            if (puntoLimpioOld != null && !puntoLimpioOld.equals(puntoLimpioNew)) {
                puntoLimpioOld.getNotificaciones().remove(notificacion);
                puntoLimpioOld = em.merge(puntoLimpioOld);
            }
            if (puntoLimpioNew != null && !puntoLimpioNew.equals(puntoLimpioOld)) {
                puntoLimpioNew.getNotificaciones().add(notificacion);
                puntoLimpioNew = em.merge(puntoLimpioNew);
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
                Long id = notificacion.getId();
                if (findNotificacion(id) == null) {
                    throw new NonexistentEntityException("The notificacion with id " + id + " no longer exists.");
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
            Notificacion notificacion;
            try {
                notificacion = em.getReference(Notificacion.class, id);
                notificacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacion with id " + id + " no longer exists.", enfe);
            }
            PuntoLimpio puntoLimpio = notificacion.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio.getNotificaciones().remove(notificacion);
                puntoLimpio = em.merge(puntoLimpio);
            }
            em.remove(notificacion);
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

    public List<Notificacion> findNotificacionEntities() {
        return findNotificacionEntities(true, -1, -1);
    }

    public List<Notificacion> findNotificacionEntities(int maxResults, int firstResult) {
        return findNotificacionEntities(false, maxResults, firstResult);
    }

    private List<Notificacion> findNotificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificacion.class));
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

    public Notificacion findNotificacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificacion> rt = cq.from(Notificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
