/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import JPA.exceptions.IllegalOrphanException;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import Modelo.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Pictograma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (categoria.getPictogramaList() == null) {
            categoria.setPictogramaList(new ArrayList<Pictograma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pictograma> attachedPictogramaList = new ArrayList<Pictograma>();
            for (Pictograma pictogramaListPictogramaToAttach : categoria.getPictogramaList()) {
                pictogramaListPictogramaToAttach = em.getReference(pictogramaListPictogramaToAttach.getClass(), pictogramaListPictogramaToAttach.getId());
                attachedPictogramaList.add(pictogramaListPictogramaToAttach);
            }
            categoria.setPictogramaList(attachedPictogramaList);
            em.persist(categoria);
            for (Pictograma pictogramaListPictograma : categoria.getPictogramaList()) {
                Categoria oldCategoriaidOfPictogramaListPictograma = pictogramaListPictograma.getCategoriaid();
                pictogramaListPictograma.setCategoriaid(categoria);
                pictogramaListPictograma = em.merge(pictogramaListPictograma);
                if (oldCategoriaidOfPictogramaListPictograma != null) {
                    oldCategoriaidOfPictogramaListPictograma.getPictogramaList().remove(pictogramaListPictograma);
                    oldCategoriaidOfPictogramaListPictograma = em.merge(oldCategoriaidOfPictogramaListPictograma);
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
            List<Pictograma> pictogramaListOld = persistentCategoria.getPictogramaList();
            List<Pictograma> pictogramaListNew = categoria.getPictogramaList();
            List<String> illegalOrphanMessages = null;
            for (Pictograma pictogramaListOldPictograma : pictogramaListOld) {
                if (!pictogramaListNew.contains(pictogramaListOldPictograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pictograma " + pictogramaListOldPictograma + " since its categoriaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pictograma> attachedPictogramaListNew = new ArrayList<Pictograma>();
            for (Pictograma pictogramaListNewPictogramaToAttach : pictogramaListNew) {
                pictogramaListNewPictogramaToAttach = em.getReference(pictogramaListNewPictogramaToAttach.getClass(), pictogramaListNewPictogramaToAttach.getId());
                attachedPictogramaListNew.add(pictogramaListNewPictogramaToAttach);
            }
            pictogramaListNew = attachedPictogramaListNew;
            categoria.setPictogramaList(pictogramaListNew);
            categoria = em.merge(categoria);
            for (Pictograma pictogramaListNewPictograma : pictogramaListNew) {
                if (!pictogramaListOld.contains(pictogramaListNewPictograma)) {
                    Categoria oldCategoriaidOfPictogramaListNewPictograma = pictogramaListNewPictograma.getCategoriaid();
                    pictogramaListNewPictograma.setCategoriaid(categoria);
                    pictogramaListNewPictograma = em.merge(pictogramaListNewPictograma);
                    if (oldCategoriaidOfPictogramaListNewPictograma != null && !oldCategoriaidOfPictogramaListNewPictograma.equals(categoria)) {
                        oldCategoriaidOfPictogramaListNewPictograma.getPictogramaList().remove(pictogramaListNewPictograma);
                        oldCategoriaidOfPictogramaListNewPictograma = em.merge(oldCategoriaidOfPictogramaListNewPictograma);
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
            List<Pictograma> pictogramaListOrphanCheck = categoria.getPictogramaList();
            for (Pictograma pictogramaListOrphanCheckPictograma : pictogramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Pictograma " + pictogramaListOrphanCheckPictograma + " in its pictogramaList field has a non-nullable categoriaid field.");
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
