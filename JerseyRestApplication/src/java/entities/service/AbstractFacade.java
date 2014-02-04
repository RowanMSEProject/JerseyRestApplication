/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author mse
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    
    //@PersistenceContext(unitName = "JerseyRestDerbyPU")
    private EntityManager em = Persistence.createEntityManagerFactory("JerseyRestApplicationPU").createEntityManager();


    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Create a new user
     * @param entity 
     */
    public void create(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
        em.close();
    }
    
    /**
     * Execute a query received from a service
     * @param query 
     */
    public void executeQuery(String query) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createQuery(query).executeUpdate();
        tx.commit();
        em.close();
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void testRemove(Object id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.find(entityClass, id));
        tx.commit();
        em.close();
    }
    
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
