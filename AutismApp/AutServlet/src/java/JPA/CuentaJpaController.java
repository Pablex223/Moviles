/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import Modelo.Cuenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Persona;
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
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) throws PreexistingEntityException, Exception {
        if (cuenta.getPictogramaCollection() == null) {
            cuenta.setPictogramaCollection(new ArrayList<Pictograma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personausuario = cuenta.getPersonausuario();
            if (personausuario != null) {
                personausuario = em.getReference(personausuario.getClass(), personausuario.getUsuario());
                cuenta.setPersonausuario(personausuario);
            }
            Collection<Pictograma> attachedPictogramaCollection = new ArrayList<Pictograma>();
            for (Pictograma pictogramaCollectionPictogramaToAttach : cuenta.getPictogramaCollection()) {
                pictogramaCollectionPictogramaToAttach = em.getReference(pictogramaCollectionPictogramaToAttach.getClass(), pictogramaCollectionPictogramaToAttach.getId());
                attachedPictogramaCollection.add(pictogramaCollectionPictogramaToAttach);
            }
            cuenta.setPictogramaCollection(attachedPictogramaCollection);
            em.persist(cuenta);
            if (personausuario != null) {
                personausuario.getCuentaCollection().add(cuenta);
                personausuario = em.merge(personausuario);
            }
            for (Pictograma pictogramaCollectionPictograma : cuenta.getPictogramaCollection()) {
                Cuenta oldCuentaidOfPictogramaCollectionPictograma = pictogramaCollectionPictograma.getCuentaid();
                pictogramaCollectionPictograma.setCuentaid(cuenta);
                pictogramaCollectionPictograma = em.merge(pictogramaCollectionPictograma);
                if (oldCuentaidOfPictogramaCollectionPictograma != null) {
                    oldCuentaidOfPictogramaCollectionPictograma.getPictogramaCollection().remove(pictogramaCollectionPictograma);
                    oldCuentaidOfPictogramaCollectionPictograma = em.merge(oldCuentaidOfPictogramaCollectionPictograma);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuenta(cuenta.getId()) != null) {
                throw new PreexistingEntityException("Cuenta " + cuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getId());
            Persona personausuarioOld = persistentCuenta.getPersonausuario();
            Persona personausuarioNew = cuenta.getPersonausuario();
            Collection<Pictograma> pictogramaCollectionOld = persistentCuenta.getPictogramaCollection();
            Collection<Pictograma> pictogramaCollectionNew = cuenta.getPictogramaCollection();
            List<String> illegalOrphanMessages = null;
            for (Pictograma pictogramaCollectionOldPictograma : pictogramaCollectionOld) {
                if (!pictogramaCollectionNew.contains(pictogramaCollectionOldPictograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pictograma " + pictogramaCollectionOldPictograma + " since its cuentaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personausuarioNew != null) {
                personausuarioNew = em.getReference(personausuarioNew.getClass(), personausuarioNew.getUsuario());
                cuenta.setPersonausuario(personausuarioNew);
            }
            Collection<Pictograma> attachedPictogramaCollectionNew = new ArrayList<Pictograma>();
            for (Pictograma pictogramaCollectionNewPictogramaToAttach : pictogramaCollectionNew) {
                pictogramaCollectionNewPictogramaToAttach = em.getReference(pictogramaCollectionNewPictogramaToAttach.getClass(), pictogramaCollectionNewPictogramaToAttach.getId());
                attachedPictogramaCollectionNew.add(pictogramaCollectionNewPictogramaToAttach);
            }
            pictogramaCollectionNew = attachedPictogramaCollectionNew;
            cuenta.setPictogramaCollection(pictogramaCollectionNew);
            cuenta = em.merge(cuenta);
            if (personausuarioOld != null && !personausuarioOld.equals(personausuarioNew)) {
                personausuarioOld.getCuentaCollection().remove(cuenta);
                personausuarioOld = em.merge(personausuarioOld);
            }
            if (personausuarioNew != null && !personausuarioNew.equals(personausuarioOld)) {
                personausuarioNew.getCuentaCollection().add(cuenta);
                personausuarioNew = em.merge(personausuarioNew);
            }
            for (Pictograma pictogramaCollectionNewPictograma : pictogramaCollectionNew) {
                if (!pictogramaCollectionOld.contains(pictogramaCollectionNewPictograma)) {
                    Cuenta oldCuentaidOfPictogramaCollectionNewPictograma = pictogramaCollectionNewPictograma.getCuentaid();
                    pictogramaCollectionNewPictograma.setCuentaid(cuenta);
                    pictogramaCollectionNewPictograma = em.merge(pictogramaCollectionNewPictograma);
                    if (oldCuentaidOfPictogramaCollectionNewPictograma != null && !oldCuentaidOfPictogramaCollectionNewPictograma.equals(cuenta)) {
                        oldCuentaidOfPictogramaCollectionNewPictograma.getPictogramaCollection().remove(pictogramaCollectionNewPictograma);
                        oldCuentaidOfPictogramaCollectionNewPictograma = em.merge(oldCuentaidOfPictogramaCollectionNewPictograma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getId();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pictograma> pictogramaCollectionOrphanCheck = cuenta.getPictogramaCollection();
            for (Pictograma pictogramaCollectionOrphanCheckPictograma : pictogramaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Pictograma " + pictogramaCollectionOrphanCheckPictograma + " in its pictogramaCollection field has a non-nullable cuentaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona personausuario = cuenta.getPersonausuario();
            if (personausuario != null) {
                personausuario.getCuentaCollection().remove(cuenta);
                personausuario = em.merge(personausuario);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
