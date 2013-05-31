/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO_impl;

import DAO_impl.exceptions.NonexistentEntityException;
import DAO_impl.exceptions.RollbackFailureException;
import entities.Contenedor;
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
public class ContenedorJpaController implements Serializable {

    public ContenedorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contenedor contenedor) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PuntoLimpio puntoLimpio = contenedor.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio = em.getReference(puntoLimpio.getClass(), puntoLimpio.getId());
                contenedor.setPuntoLimpio(puntoLimpio);
            }
            em.persist(contenedor);
            if (puntoLimpio != null) {
                puntoLimpio.getContenedores().add(contenedor);
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

    public void edit(Contenedor contenedor) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contenedor persistentContenedor = em.find(Contenedor.class, contenedor.getId());
            PuntoLimpio puntoLimpioOld = persistentContenedor.getPuntoLimpio();
            PuntoLimpio puntoLimpioNew = contenedor.getPuntoLimpio();
            if (puntoLimpioNew != null) {
                puntoLimpioNew = em.getReference(puntoLimpioNew.getClass(), puntoLimpioNew.getId());
                contenedor.setPuntoLimpio(puntoLimpioNew);
            }
            contenedor = em.merge(contenedor);
            if (puntoLimpioOld != null && !puntoLimpioOld.equals(puntoLimpioNew)) {
                puntoLimpioOld.getContenedores().remove(contenedor);
                puntoLimpioOld = em.merge(puntoLimpioOld);
            }
            if (puntoLimpioNew != null && !puntoLimpioNew.equals(puntoLimpioOld)) {
                puntoLimpioNew.getContenedores().add(contenedor);
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
                Long id = contenedor.getId();
                if (findContenedor(id) == null) {
                    throw new NonexistentEntityException("The contenedor with id " + id + " no longer exists.");
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
            Contenedor contenedor;
            try {
                contenedor = em.getReference(Contenedor.class, id);
                contenedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenedor with id " + id + " no longer exists.", enfe);
            }
            PuntoLimpio puntoLimpio = contenedor.getPuntoLimpio();
            if (puntoLimpio != null) {
                puntoLimpio.getContenedores().remove(contenedor);
                puntoLimpio = em.merge(puntoLimpio);
            }
            em.remove(contenedor);
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

    public List<Contenedor> findContenedorEntities() {
        return findContenedorEntities(true, -1, -1);
    }

    public List<Contenedor> findContenedorEntities(int maxResults, int firstResult) {
        return findContenedorEntities(false, maxResults, firstResult);
    }

    private List<Contenedor> findContenedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contenedor.class));
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

    public Contenedor findContenedor(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contenedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contenedor> rt = cq.from(Contenedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
