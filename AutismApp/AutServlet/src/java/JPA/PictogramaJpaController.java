/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Categoria;
import Modelo.Cuenta;
import Modelo.Pictograma;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author luisf
 */
public class PictogramaJpaController implements Serializable {

    public PictogramaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pictograma pictograma) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoriaid = pictograma.getCategoriaid();
            if (categoriaid != null) {
                categoriaid = em.getReference(categoriaid.getClass(), categoriaid.getId());
                pictograma.setCategoriaid(categoriaid);
            }
            Cuenta cuentaid = pictograma.getCuentaid();
            if (cuentaid != null) {
                cuentaid = em.getReference(cuentaid.getClass(), cuentaid.getId());
                pictograma.setCuentaid(cuentaid);
            }
            em.persist(pictograma);
            if (categoriaid != null) {
                categoriaid.getPictogramaCollection().add(pictograma);
                categoriaid = em.merge(categoriaid);
            }
            if (cuentaid != null) {
                cuentaid.getPictogramaCollection().add(pictograma);
                cuentaid = em.merge(cuentaid);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPictograma(pictograma.getId()) != null) {
                throw new PreexistingEntityException("Pictograma " + pictograma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pictograma pictograma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pictograma persistentPictograma = em.find(Pictograma.class, pictograma.getId());
            Categoria categoriaidOld = persistentPictograma.getCategoriaid();
            Categoria categoriaidNew = pictograma.getCategoriaid();
            Cuenta cuentaidOld = persistentPictograma.getCuentaid();
            Cuenta cuentaidNew = pictograma.getCuentaid();
            if (categoriaidNew != null) {
                categoriaidNew = em.getReference(categoriaidNew.getClass(), categoriaidNew.getId());
                pictograma.setCategoriaid(categoriaidNew);
            }
            if (cuentaidNew != null) {
                cuentaidNew = em.getReference(cuentaidNew.getClass(), cuentaidNew.getId());
                pictograma.setCuentaid(cuentaidNew);
            }
            pictograma = em.merge(pictograma);
            if (categoriaidOld != null && !categoriaidOld.equals(categoriaidNew)) {
                categoriaidOld.getPictogramaCollection().remove(pictograma);
                categoriaidOld = em.merge(categoriaidOld);
            }
            if (categoriaidNew != null && !categoriaidNew.equals(categoriaidOld)) {
                categoriaidNew.getPictogramaCollection().add(pictograma);
                categoriaidNew = em.merge(categoriaidNew);
            }
            if (cuentaidOld != null && !cuentaidOld.equals(cuentaidNew)) {
                cuentaidOld.getPictogramaCollection().remove(pictograma);
                cuentaidOld = em.merge(cuentaidOld);
            }
            if (cuentaidNew != null && !cuentaidNew.equals(cuentaidOld)) {
                cuentaidNew.getPictogramaCollection().add(pictograma);
                cuentaidNew = em.merge(cuentaidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pictograma.getId();
                if (findPictograma(id) == null) {
                    throw new NonexistentEntityException("The pictograma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pictograma pictograma;
            try {
                pictograma = em.getReference(Pictograma.class, id);
                pictograma.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pictograma with id " + id + " no longer exists.", enfe);
            }
            Categoria categoriaid = pictograma.getCategoriaid();
            if (categoriaid != null) {
                categoriaid.getPictogramaCollection().remove(pictograma);
                categoriaid = em.merge(categoriaid);
            }
            Cuenta cuentaid = pictograma.getCuentaid();
            if (cuentaid != null) {
                cuentaid.getPictogramaCollection().remove(pictograma);
                cuentaid = em.merge(cuentaid);
            }
            em.remove(pictograma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pictograma> findPictogramaEntities() {
        return findPictogramaEntities(true, -1, -1);
    }

    public List<Pictograma> findPictogramaEntities(int maxResults, int firstResult) {
        return findPictogramaEntities(false, maxResults, firstResult);
    }

    private List<Pictograma> findPictogramaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pictograma.class));
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

    public Pictograma findPictograma(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pictograma.class, id);
        } finally {
            em.close();
        }
    }

    public int getPictogramaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pictograma> rt = cq.from(Pictograma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
