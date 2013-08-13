/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;


import DAO.interfaces.genericDAO;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author victor
 */
public abstract class genericDAO_impl<T> implements genericDAO<T>{
    
    private Class<T> entityClass ;
    protected EntityManager em;
    
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public genericDAO_impl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Override
    public boolean insert(T nvaEntity) {
        getEntityManager().persist(nvaEntity);
        return true;
    }

    @Override
    public boolean delete(T nvaEntity) {
        getEntityManager().remove(getEntityManager().merge(nvaEntity));
        return true;
    }
    
    @Override
    public boolean delete(int id) {
        T entityToDelete = getEntityManager().find(entityClass, id);
        getEntityManager().remove(getEntityManager().merge(entityToDelete));
        return true;
    }

    @Override
    public T find(int id) {
        return getEntityManager().find(entityClass, id);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T update(T entity) {
        entity = getEntityManager().merge(entity);
        getEntityManager().flush();
        return entity;
    }

    @Override
    public Collection<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    @Override
    public Collection<T> findRange(int inicio, int fin) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(fin - inicio);
        q.setFirstResult(inicio);
        return q.getResultList();
    }
    
    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
