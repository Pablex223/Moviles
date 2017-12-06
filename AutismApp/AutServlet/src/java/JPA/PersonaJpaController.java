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
import Modelo.Analisis;
import Modelo.Cuenta;
import Modelo.Persona;
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
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws PreexistingEntityException, Exception {
        if (persona.getCuentaCollection() == null) {
            persona.setCuentaCollection(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Analisis analisisid = persona.getAnalisisid();
            if (analisisid != null) {
                analisisid = em.getReference(analisisid.getClass(), analisisid.getId());
                persona.setAnalisisid(analisisid);
            }
            Collection<Cuenta> attachedCuentaCollection = new ArrayList<Cuenta>();
            for (Cuenta cuentaCollectionCuentaToAttach : persona.getCuentaCollection()) {
                cuentaCollectionCuentaToAttach = em.getReference(cuentaCollectionCuentaToAttach.getClass(), cuentaCollectionCuentaToAttach.getId());
                attachedCuentaCollection.add(cuentaCollectionCuentaToAttach);
            }
            persona.setCuentaCollection(attachedCuentaCollection);
            em.persist(persona);
            if (analisisid != null) {
                analisisid.getPersonaCollection().add(persona);
                analisisid = em.merge(analisisid);
            }
            for (Cuenta cuentaCollectionCuenta : persona.getCuentaCollection()) {
                Persona oldPersonausuarioOfCuentaCollectionCuenta = cuentaCollectionCuenta.getPersonausuario();
                cuentaCollectionCuenta.setPersonausuario(persona);
                cuentaCollectionCuenta = em.merge(cuentaCollectionCuenta);
                if (oldPersonausuarioOfCuentaCollectionCuenta != null) {
                    oldPersonausuarioOfCuentaCollectionCuenta.getCuentaCollection().remove(cuentaCollectionCuenta);
                    oldPersonausuarioOfCuentaCollectionCuenta = em.merge(oldPersonausuarioOfCuentaCollectionCuenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getUsuario()) != null) {
                throw new PreexistingEntityException("Persona " + persona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getUsuario());
            Analisis analisisidOld = persistentPersona.getAnalisisid();
            Analisis analisisidNew = persona.getAnalisisid();
            Collection<Cuenta> cuentaCollectionOld = persistentPersona.getCuentaCollection();
            Collection<Cuenta> cuentaCollectionNew = persona.getCuentaCollection();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaCollectionOldCuenta : cuentaCollectionOld) {
                if (!cuentaCollectionNew.contains(cuentaCollectionOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaCollectionOldCuenta + " since its personausuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (analisisidNew != null) {
                analisisidNew = em.getReference(analisisidNew.getClass(), analisisidNew.getId());
                persona.setAnalisisid(analisisidNew);
            }
            Collection<Cuenta> attachedCuentaCollectionNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaCollectionNewCuentaToAttach : cuentaCollectionNew) {
                cuentaCollectionNewCuentaToAttach = em.getReference(cuentaCollectionNewCuentaToAttach.getClass(), cuentaCollectionNewCuentaToAttach.getId());
                attachedCuentaCollectionNew.add(cuentaCollectionNewCuentaToAttach);
            }
            cuentaCollectionNew = attachedCuentaCollectionNew;
            persona.setCuentaCollection(cuentaCollectionNew);
            persona = em.merge(persona);
            if (analisisidOld != null && !analisisidOld.equals(analisisidNew)) {
                analisisidOld.getPersonaCollection().remove(persona);
                analisisidOld = em.merge(analisisidOld);
            }
            if (analisisidNew != null && !analisisidNew.equals(analisisidOld)) {
                analisisidNew.getPersonaCollection().add(persona);
                analisisidNew = em.merge(analisisidNew);
            }
            for (Cuenta cuentaCollectionNewCuenta : cuentaCollectionNew) {
                if (!cuentaCollectionOld.contains(cuentaCollectionNewCuenta)) {
                    Persona oldPersonausuarioOfCuentaCollectionNewCuenta = cuentaCollectionNewCuenta.getPersonausuario();
                    cuentaCollectionNewCuenta.setPersonausuario(persona);
                    cuentaCollectionNewCuenta = em.merge(cuentaCollectionNewCuenta);
                    if (oldPersonausuarioOfCuentaCollectionNewCuenta != null && !oldPersonausuarioOfCuentaCollectionNewCuenta.equals(persona)) {
                        oldPersonausuarioOfCuentaCollectionNewCuenta.getCuentaCollection().remove(cuentaCollectionNewCuenta);
                        oldPersonausuarioOfCuentaCollectionNewCuenta = em.merge(oldPersonausuarioOfCuentaCollectionNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = persona.getUsuario();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cuenta> cuentaCollectionOrphanCheck = persona.getCuentaCollection();
            for (Cuenta cuentaCollectionOrphanCheckCuenta : cuentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Cuenta " + cuentaCollectionOrphanCheckCuenta + " in its cuentaCollection field has a non-nullable personausuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Analisis analisisid = persona.getAnalisisid();
            if (analisisid != null) {
                analisisid.getPersonaCollection().remove(persona);
                analisisid = em.merge(analisisid);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
