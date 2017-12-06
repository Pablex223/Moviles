/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import JPA.exceptions.IllegalOrphanException;
import JPA.exceptions.NonexistentEntityException;
import Modelo.Analisis;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (analisis.getPersonaList() == null) {
            analisis.setPersonaList(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : analisis.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getNombre());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            analisis.setPersonaList(attachedPersonaList);
            em.persist(analisis);
            for (Persona personaListPersona : analisis.getPersonaList()) {
                Analisis oldAnalisisidOfPersonaListPersona = personaListPersona.getAnalisisid();
                personaListPersona.setAnalisisid(analisis);
                personaListPersona = em.merge(personaListPersona);
                if (oldAnalisisidOfPersonaListPersona != null) {
                    oldAnalisisidOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldAnalisisidOfPersonaListPersona = em.merge(oldAnalisisidOfPersonaListPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Analisis analisis) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Analisis persistentAnalisis = em.find(Analisis.class, analisis.getId());
            List<Persona> personaListOld = persistentAnalisis.getPersonaList();
            List<Persona> personaListNew = analisis.getPersonaList();
            List<String> illegalOrphanMessages = null;
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaListOldPersona + " since its analisisid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getNombre());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            analisis.setPersonaList(personaListNew);
            analisis = em.merge(analisis);
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Analisis oldAnalisisidOfPersonaListNewPersona = personaListNewPersona.getAnalisisid();
                    personaListNewPersona.setAnalisisid(analisis);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldAnalisisidOfPersonaListNewPersona != null && !oldAnalisisidOfPersonaListNewPersona.equals(analisis)) {
                        oldAnalisisidOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldAnalisisidOfPersonaListNewPersona = em.merge(oldAnalisisidOfPersonaListNewPersona);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Persona> personaListOrphanCheck = analisis.getPersonaList();
            for (Persona personaListOrphanCheckPersona : personaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Analisis (" + analisis + ") cannot be destroyed since the Persona " + personaListOrphanCheckPersona + " in its personaList field has a non-nullable analisisid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
