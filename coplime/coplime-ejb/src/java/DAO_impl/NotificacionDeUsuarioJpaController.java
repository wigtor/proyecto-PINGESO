/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO_impl;

import DAO_impl.exceptions.NonexistentEntityException;
import DAO_impl.exceptions.RollbackFailureException;
import entities.NotificacionDeUsuario;
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
public class NotificacionDeUsuarioJpaController implements Serializable {

    public NotificacionDeUsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NotificacionDeUsuario notificacionDeUsuario) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PuntoLimpio puntoLimpio = notificacionDeUsuario.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio = em.getReference(puntoLimpio.getClass(), puntoLimpio.getId());
                notificacionDeUsuario.setPuntoLimpio(puntoLimpio);
            }
            em.persist(notificacionDeUsuario);
            if (puntoLimpio != null) {
                puntoLimpio.getNotificaciones().add(notificacionDeUsuario);
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

    public void edit(NotificacionDeUsuario notificacionDeUsuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            NotificacionDeUsuario persistentNotificacionDeUsuario = em.find(NotificacionDeUsuario.class, notificacionDeUsuario.getId());
            PuntoLimpio puntoLimpioOld = persistentNotificacionDeUsuario.getPuntoLimpio();
            PuntoLimpio puntoLimpioNew = notificacionDeUsuario.getPuntoLimpio();
            if (puntoLimpioNew != null) {
                puntoLimpioNew = em.getReference(puntoLimpioNew.getClass(), puntoLimpioNew.getId());
                notificacionDeUsuario.setPuntoLimpio(puntoLimpioNew);
            }
            notificacionDeUsuario = em.merge(notificacionDeUsuario);
            if (puntoLimpioOld != null && !puntoLimpioOld.equals(puntoLimpioNew)) {
                puntoLimpioOld.getNotificaciones().remove(notificacionDeUsuario);
                puntoLimpioOld = em.merge(puntoLimpioOld);
            }
            if (puntoLimpioNew != null && !puntoLimpioNew.equals(puntoLimpioOld)) {
                puntoLimpioNew.getNotificaciones().add(notificacionDeUsuario);
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
                Long id = notificacionDeUsuario.getId();
                if (findNotificacionDeUsuario(id) == null) {
                    throw new NonexistentEntityException("The notificacionDeUsuario with id " + id + " no longer exists.");
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
            NotificacionDeUsuario notificacionDeUsuario;
            try {
                notificacionDeUsuario = em.getReference(NotificacionDeUsuario.class, id);
                notificacionDeUsuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacionDeUsuario with id " + id + " no longer exists.", enfe);
            }
            PuntoLimpio puntoLimpio = notificacionDeUsuario.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio.getNotificaciones().remove(notificacionDeUsuario);
                puntoLimpio = em.merge(puntoLimpio);
            }
            em.remove(notificacionDeUsuario);
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

    public List<NotificacionDeUsuario> findNotificacionDeUsuarioEntities() {
        return findNotificacionDeUsuarioEntities(true, -1, -1);
    }

    public List<NotificacionDeUsuario> findNotificacionDeUsuarioEntities(int maxResults, int firstResult) {
        return findNotificacionDeUsuarioEntities(false, maxResults, firstResult);
    }

    private List<NotificacionDeUsuario> findNotificacionDeUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NotificacionDeUsuario.class));
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

    public NotificacionDeUsuario findNotificacionDeUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NotificacionDeUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacionDeUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NotificacionDeUsuario> rt = cq.from(NotificacionDeUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
