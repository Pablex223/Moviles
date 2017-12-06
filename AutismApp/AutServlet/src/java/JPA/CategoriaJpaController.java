/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import Modelo.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Pictograma;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.IllegalOrphanException;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author luisf
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) throws PreexistingEntityException, Exception {
        if (categoria.getPictogramaCollection() == null) {
            categoria.setPictogramaCollection(new ArrayList<Pictograma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pictograma> attachedPictogramaCollection = new ArrayList<Pictograma>();
            for (Pictograma pictogramaCollectionPictogramaToAttach : categoria.getPictogramaCollection()) {
                pictogramaCollectionPictogramaToAttach = em.getReference(pictogramaCollectionPictogramaToAttach.getClass(), pictogramaCollectionPictogramaToAttach.getId());
                attachedPictogramaCollection.add(pictogramaCollectionPictogramaToAttach);
            }
            categoria.setPictogramaCollection(attachedPictogramaCollection);
            em.persist(categoria);
            for (Pictograma pictogramaCollectionPictograma : categoria.getPictogramaCollection()) {
                Categoria oldCategoriaidOfPictogramaCollectionPictograma = pictogramaCollectionPictograma.getCategoriaid();
                pictogramaCollectionPictograma.setCategoriaid(categoria);
                pictogramaCollectionPictograma = em.merge(pictogramaCollectionPictograma);
                if (oldCategoriaidOfPictogramaCollectionPictograma != null) {
                    oldCategoriaidOfPictogramaCollectionPictograma.getPictogramaCollection().remove(pictogramaCollectionPictograma);
                    oldCategoriaidOfPictogramaCollectionPictograma = em.merge(oldCategoriaidOfPictogramaCollectionPictograma);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoria(categoria.getId()) != null) {
                throw new PreexistingEntityException("Categoria " + categoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getId());
            Collection<Pictograma> pictogramaCollectionOld = persistentCategoria.getPictogramaCollection();
            Collection<Pictograma> pictogramaCollectionNew = categoria.getPictogramaCollection();
            List<String> illegalOrphanMessages = null;
            for (Pictograma pictogramaCollectionOldPictograma : pictogramaCollectionOld) {
                if (!pictogramaCollectionNew.contains(pictogramaCollectionOldPictograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pictograma " + pictogramaCollectionOldPictograma + " since its categoriaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Pictograma> attachedPictogramaCollectionNew = new ArrayList<Pictograma>();
            for (Pictograma pictogramaCollectionNewPictogramaToAttach : pictogramaCollectionNew) {
                pictogramaCollectionNewPictogramaToAttach = em.getReference(pictogramaCollectionNewPictogramaToAttach.getClass(), pictogramaCollectionNewPictogramaToAttach.getId());
                attachedPictogramaCollectionNew.add(pictogramaCollectionNewPictogramaToAttach);
            }
            pictogramaCollectionNew = attachedPictogramaCollectionNew;
            categoria.setPictogramaCollection(pictogramaCollectionNew);
            categoria = em.merge(categoria);
            for (Pictograma pictogramaCollectionNewPictograma : pictogramaCollectionNew) {
                if (!pictogramaCollectionOld.contains(pictogramaCollectionNewPictograma)) {
                    Categoria oldCategoriaidOfPictogramaCollectionNewPictograma = pictogramaCollectionNewPictograma.getCategoriaid();
                    pictogramaCollectionNewPictograma.setCategoriaid(categoria);
                    pictogramaCollectionNewPictograma = em.merge(pictogramaCollectionNewPictograma);
                    if (oldCategoriaidOfPictogramaCollectionNewPictograma != null && !oldCategoriaidOfPictogramaCollectionNewPictograma.equals(categoria)) {
                        oldCategoriaidOfPictogramaCollectionNewPictograma.getPictogramaCollection().remove(pictogramaCollectionNewPictograma);
                        oldCategoriaidOfPictogramaCollectionNewPictograma = em.merge(oldCategoriaidOfPictogramaCollectionNewPictograma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getId();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pictograma> pictogramaCollectionOrphanCheck = categoria.getPictogramaCollection();
            for (Pictograma pictogramaCollectionOrphanCheckPictograma : pictogramaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Pictograma " + pictogramaCollectionOrphanCheckPictograma + " in its pictogramaCollection field has a non-nullable categoriaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
