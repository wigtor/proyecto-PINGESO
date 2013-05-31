/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO_impl;

import DAO_impl.exceptions.NonexistentEntityException;
import DAO_impl.exceptions.RollbackFailureException;
import entities.MantencionPuntoLimpio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author victor
 */
public class MantencionPuntoLimpioJpaController implements Serializable {

    public MantencionPuntoLimpioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MantencionPuntoLimpio mantencionPuntoLimpio) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OperarioMantencion operarioMantencion = mantencionPuntoLimpio.getOperarioMantencion();
            if (operarioMantencion != null) {
                operarioMantencion = em.getReference(operarioMantencion.getClass(), operarioMantencion.getId());
                mantencionPuntoLimpio.setOperarioMantencion(operarioMantencion);
            }
            PuntoLimpio puntoLimpio = mantencionPuntoLimpio.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio = em.getReference(puntoLimpio.getClass(), puntoLimpio.getId());
                mantencionPuntoLimpio.setPuntoLimpio(puntoLimpio);
            }
            em.persist(mantencionPuntoLimpio);
            if (operarioMantencion != null) {
                operarioMantencion.getMantencionesRealizadas().add(mantencionPuntoLimpio);
                operarioMantencion = em.merge(operarioMantencion);
            }
            if (puntoLimpio != null) {
                puntoLimpio.getMantenciones().add(mantencionPuntoLimpio);
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

    public void edit(MantencionPuntoLimpio mantencionPuntoLimpio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MantencionPuntoLimpio persistentMantencionPuntoLimpio = em.find(MantencionPuntoLimpio.class, mantencionPuntoLimpio.getNum());
            OperarioMantencion operarioMantencionOld = persistentMantencionPuntoLimpio.getOperarioMantencion();
            OperarioMantencion operarioMantencionNew = mantencionPuntoLimpio.getOperarioMantencion();
            PuntoLimpio puntoLimpioOld = persistentMantencionPuntoLimpio.getPuntoLimpio();
            PuntoLimpio puntoLimpioNew = mantencionPuntoLimpio.getPuntoLimpio();
            if (operarioMantencionNew != null) {
                operarioMantencionNew = em.getReference(operarioMantencionNew.getClass(), operarioMantencionNew.getId());
                mantencionPuntoLimpio.setOperarioMantencion(operarioMantencionNew);
            }
            if (puntoLimpioNew != null) {
                puntoLimpioNew = em.getReference(puntoLimpioNew.getClass(), puntoLimpioNew.getId());
                mantencionPuntoLimpio.setPuntoLimpio(puntoLimpioNew);
            }
            mantencionPuntoLimpio = em.merge(mantencionPuntoLimpio);
            if (operarioMantencionOld != null && !operarioMantencionOld.equals(operarioMantencionNew)) {
                operarioMantencionOld.getMantencionesRealizadas().remove(mantencionPuntoLimpio);
                operarioMantencionOld = em.merge(operarioMantencionOld);
            }
            if (operarioMantencionNew != null && !operarioMantencionNew.equals(operarioMantencionOld)) {
                operarioMantencionNew.getMantencionesRealizadas().add(mantencionPuntoLimpio);
                operarioMantencionNew = em.merge(operarioMantencionNew);
            }
            if (puntoLimpioOld != null && !puntoLimpioOld.equals(puntoLimpioNew)) {
                puntoLimpioOld.getMantenciones().remove(mantencionPuntoLimpio);
                puntoLimpioOld = em.merge(puntoLimpioOld);
            }
            if (puntoLimpioNew != null && !puntoLimpioNew.equals(puntoLimpioOld)) {
                puntoLimpioNew.getMantenciones().add(mantencionPuntoLimpio);
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
                Long id = mantencionPuntoLimpio.getNum();
                if (findMantencionPuntoLimpio(id) == null) {
                    throw new NonexistentEntityException("The mantencionPuntoLimpio with id " + id + " no longer exists.");
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
            MantencionPuntoLimpio mantencionPuntoLimpio;
            try {
                mantencionPuntoLimpio = em.getReference(MantencionPuntoLimpio.class, id);
                mantencionPuntoLimpio.getNum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mantencionPuntoLimpio with id " + id + " no longer exists.", enfe);
            }
            OperarioMantencion operarioMantencion = mantencionPuntoLimpio.getOperarioMantencion();
            if (operarioMantencion != null) {
                operarioMantencion.getMantencionesRealizadas().remove(mantencionPuntoLimpio);
                operarioMantencion = em.merge(operarioMantencion);
            }
            PuntoLimpio puntoLimpio = mantencionPuntoLimpio.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio.getMantenciones().remove(mantencionPuntoLimpio);
                puntoLimpio = em.merge(puntoLimpio);
            }
            em.remove(mantencionPuntoLimpio);
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

    public List<MantencionPuntoLimpio> findMantencionPuntoLimpioEntities() {
        return findMantencionPuntoLimpioEntities(true, -1, -1);
    }

    public List<MantencionPuntoLimpio> findMantencionPuntoLimpioEntities(int maxResults, int firstResult) {
        return findMantencionPuntoLimpioEntities(false, maxResults, firstResult);
    }

    private List<MantencionPuntoLimpio> findMantencionPuntoLimpioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MantencionPuntoLimpio.class));
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

    public MantencionPuntoLimpio findMantencionPuntoLimpio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MantencionPuntoLimpio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMantencionPuntoLimpioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MantencionPuntoLimpio> rt = cq.from(MantencionPuntoLimpio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
