/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import Modelo.Analisis;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Persona;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author luisf
 */
public class AnalisisJpaController implements Serializable {

    public AnalisisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Analisis analisis) {
        if (analisis.getPersonaCollection() == null) {
            analisis.setPersonaCollection(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Persona> attachedPersonaCollection = new ArrayList<Persona>();
            for (Persona personaCollectionPersonaToAttach : analisis.getPersonaCollection()) {
                personaCollectionPersonaToAttach = em.getReference(personaCollectionPersonaToAttach.getClass(), personaCollectionPersonaToAttach.getUsuario());
                attachedPersonaCollection.add(personaCollectionPersonaToAttach);
            }
            analisis.setPersonaCollection(attachedPersonaCollection);
            em.persist(analisis);
            for (Persona personaCollectionPersona : analisis.getPersonaCollection()) {
                Analisis oldAnalisisidOfPersonaCollectionPersona = personaCollectionPersona.getAnalisisid();
                personaCollectionPersona.setAnalisisid(analisis);
                personaCollectionPersona = em.merge(personaCollectionPersona);
                if (oldAnalisisidOfPersonaCollectionPersona != null) {
                    oldAnalisisidOfPersonaCollectionPersona.getPersonaCollection().remove(personaCollectionPersona);
                    oldAnalisisidOfPersonaCollectionPersona = em.merge(oldAnalisisidOfPersonaCollectionPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Analisis analisis) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Analisis persistentAnalisis = em.find(Analisis.class, analisis.getId());
            Collection<Persona> personaCollectionOld = persistentAnalisis.getPersonaCollection();
            Collection<Persona> personaCollectionNew = analisis.getPersonaCollection();
            Collection<Persona> attachedPersonaCollectionNew = new ArrayList<Persona>();
            for (Persona personaCollectionNewPersonaToAttach : personaCollectionNew) {
                personaCollectionNewPersonaToAttach = em.getReference(personaCollectionNewPersonaToAttach.getClass(), personaCollectionNewPersonaToAttach.getUsuario());
                attachedPersonaCollectionNew.add(personaCollectionNewPersonaToAttach);
            }
            personaCollectionNew = attachedPersonaCollectionNew;
            analisis.setPersonaCollection(personaCollectionNew);
            analisis = em.merge(analisis);
            for (Persona personaCollectionOldPersona : personaCollectionOld) {
                if (!personaCollectionNew.contains(personaCollectionOldPersona)) {
                    personaCollectionOldPersona.setAnalisisid(null);
                    personaCollectionOldPersona = em.merge(personaCollectionOldPersona);
                }
            }
            for (Persona personaCollectionNewPersona : personaCollectionNew) {
                if (!personaCollectionOld.contains(personaCollectionNewPersona)) {
                    Analisis oldAnalisisidOfPersonaCollectionNewPersona = personaCollectionNewPersona.getAnalisisid();
                    personaCollectionNewPersona.setAnalisisid(analisis);
                    personaCollectionNewPersona = em.merge(personaCollectionNewPersona);
                    if (oldAnalisisidOfPersonaCollectionNewPersona != null && !oldAnalisisidOfPersonaCollectionNewPersona.equals(analisis)) {
                        oldAnalisisidOfPersonaCollectionNewPersona.getPersonaCollection().remove(personaCollectionNewPersona);
                        oldAnalisisidOfPersonaCollectionNewPersona = em.merge(oldAnalisisidOfPersonaCollectionNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = analisis.getId();
                if (findAnalisis(id) == null) {
                    throw new NonexistentEntityException("The analisis with id " + id + " no longer exists.");
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
            Analisis analisis;
            try {
                analisis = em.getReference(Analisis.class, id);
                analisis.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The analisis with id " + id + " no longer exists.", enfe);
            }
            Collection<Persona> personaCollection = analisis.getPersonaCollection();
            for (Persona personaCollectionPersona : personaCollection) {
                personaCollectionPersona.setAnalisisid(null);
                personaCollectionPersona = em.merge(personaCollectionPersona);
            }
            em.remove(analisis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Analisis> findAnalisisEntities() {
        return findAnalisisEntities(true, -1, -1);
    }

    public List<Analisis> findAnalisisEntities(int maxResults, int firstResult) {
        return findAnalisisEntities(false, maxResults, firstResult);
    }

    private List<Analisis> findAnalisisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Analisis.class));
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

    public Analisis findAnalisis(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Analisis.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnalisisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Analisis> rt = cq.from(Analisis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
