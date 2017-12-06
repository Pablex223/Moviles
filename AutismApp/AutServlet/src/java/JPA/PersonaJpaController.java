/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import JPA.exceptions.IllegalOrphanException;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Analisis;
import Modelo.Usuario;
import Modelo.Cuenta;
import Modelo.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (persona.getCuentaList() == null) {
            persona.setCuentaList(new ArrayList<Cuenta>());
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
            Usuario loginusuario = persona.getLoginusuario();
            if (loginusuario != null) {
                loginusuario = em.getReference(loginusuario.getClass(), loginusuario.getUsuario());
                persona.setLoginusuario(loginusuario);
            }
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : persona.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getId());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            persona.setCuentaList(attachedCuentaList);
            em.persist(persona);
            if (analisisid != null) {
                analisisid.getPersonaList().add(persona);
                analisisid = em.merge(analisisid);
            }
            if (loginusuario != null) {
                loginusuario.getPersonaList().add(persona);
                loginusuario = em.merge(loginusuario);
            }
            for (Cuenta cuentaListCuenta : persona.getCuentaList()) {
                Persona oldUsuarionombreOfCuentaListCuenta = cuentaListCuenta.getUsuarionombre();
                cuentaListCuenta.setUsuarionombre(persona);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldUsuarionombreOfCuentaListCuenta != null) {
                    oldUsuarionombreOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldUsuarionombreOfCuentaListCuenta = em.merge(oldUsuarionombreOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getNombre()) != null) {
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
            Persona persistentPersona = em.find(Persona.class, persona.getNombre());
            Analisis analisisidOld = persistentPersona.getAnalisisid();
            Analisis analisisidNew = persona.getAnalisisid();
            Usuario loginusuarioOld = persistentPersona.getLoginusuario();
            Usuario loginusuarioNew = persona.getLoginusuario();
            List<Cuenta> cuentaListOld = persistentPersona.getCuentaList();
            List<Cuenta> cuentaListNew = persona.getCuentaList();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its usuarionombre field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (analisisidNew != null) {
                analisisidNew = em.getReference(analisisidNew.getClass(), analisisidNew.getId());
                persona.setAnalisisid(analisisidNew);
            }
            if (loginusuarioNew != null) {
                loginusuarioNew = em.getReference(loginusuarioNew.getClass(), loginusuarioNew.getUsuario());
                persona.setLoginusuario(loginusuarioNew);
            }
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getId());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            persona.setCuentaList(cuentaListNew);
            persona = em.merge(persona);
            if (analisisidOld != null && !analisisidOld.equals(analisisidNew)) {
                analisisidOld.getPersonaList().remove(persona);
                analisisidOld = em.merge(analisisidOld);
            }
            if (analisisidNew != null && !analisisidNew.equals(analisisidOld)) {
                analisisidNew.getPersonaList().add(persona);
                analisisidNew = em.merge(analisisidNew);
            }
            if (loginusuarioOld != null && !loginusuarioOld.equals(loginusuarioNew)) {
                loginusuarioOld.getPersonaList().remove(persona);
                loginusuarioOld = em.merge(loginusuarioOld);
            }
            if (loginusuarioNew != null && !loginusuarioNew.equals(loginusuarioOld)) {
                loginusuarioNew.getPersonaList().add(persona);
                loginusuarioNew = em.merge(loginusuarioNew);
            }
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Persona oldUsuarionombreOfCuentaListNewCuenta = cuentaListNewCuenta.getUsuarionombre();
                    cuentaListNewCuenta.setUsuarionombre(persona);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldUsuarionombreOfCuentaListNewCuenta != null && !oldUsuarionombreOfCuentaListNewCuenta.equals(persona)) {
                        oldUsuarionombreOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldUsuarionombreOfCuentaListNewCuenta = em.merge(oldUsuarionombreOfCuentaListNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = persona.getNombre();
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
                persona.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuenta> cuentaListOrphanCheck = persona.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable usuarionombre field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Analisis analisisid = persona.getAnalisisid();
            if (analisisid != null) {
                analisisid.getPersonaList().remove(persona);
                analisisid = em.merge(analisisid);
            }
            Usuario loginusuario = persona.getLoginusuario();
            if (loginusuario != null) {
                loginusuario.getPersonaList().remove(persona);
                loginusuario = em.merge(loginusuario);
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
