/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import JPA.exceptions.IllegalOrphanException;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import Modelo.Cuenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Persona;
import Modelo.Pictograma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (cuenta.getPictogramaList() == null) {
            cuenta.setPictogramaList(new ArrayList<Pictograma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona usuarionombre = cuenta.getUsuarionombre();
            if (usuarionombre != null) {
                usuarionombre = em.getReference(usuarionombre.getClass(), usuarionombre.getNombre());
                cuenta.setUsuarionombre(usuarionombre);
            }
            List<Pictograma> attachedPictogramaList = new ArrayList<Pictograma>();
            for (Pictograma pictogramaListPictogramaToAttach : cuenta.getPictogramaList()) {
                pictogramaListPictogramaToAttach = em.getReference(pictogramaListPictogramaToAttach.getClass(), pictogramaListPictogramaToAttach.getId());
                attachedPictogramaList.add(pictogramaListPictogramaToAttach);
            }
            cuenta.setPictogramaList(attachedPictogramaList);
            em.persist(cuenta);
            if (usuarionombre != null) {
                usuarionombre.getCuentaList().add(cuenta);
                usuarionombre = em.merge(usuarionombre);
            }
            for (Pictograma pictogramaListPictograma : cuenta.getPictogramaList()) {
                Cuenta oldCuentaidOfPictogramaListPictograma = pictogramaListPictograma.getCuentaid();
                pictogramaListPictograma.setCuentaid(cuenta);
                pictogramaListPictograma = em.merge(pictogramaListPictograma);
                if (oldCuentaidOfPictogramaListPictograma != null) {
                    oldCuentaidOfPictogramaListPictograma.getPictogramaList().remove(pictogramaListPictograma);
                    oldCuentaidOfPictogramaListPictograma = em.merge(oldCuentaidOfPictogramaListPictograma);
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
            Persona usuarionombreOld = persistentCuenta.getUsuarionombre();
            Persona usuarionombreNew = cuenta.getUsuarionombre();
            List<Pictograma> pictogramaListOld = persistentCuenta.getPictogramaList();
            List<Pictograma> pictogramaListNew = cuenta.getPictogramaList();
            List<String> illegalOrphanMessages = null;
            for (Pictograma pictogramaListOldPictograma : pictogramaListOld) {
                if (!pictogramaListNew.contains(pictogramaListOldPictograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pictograma " + pictogramaListOldPictograma + " since its cuentaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarionombreNew != null) {
                usuarionombreNew = em.getReference(usuarionombreNew.getClass(), usuarionombreNew.getNombre());
                cuenta.setUsuarionombre(usuarionombreNew);
            }
            List<Pictograma> attachedPictogramaListNew = new ArrayList<Pictograma>();
            for (Pictograma pictogramaListNewPictogramaToAttach : pictogramaListNew) {
                pictogramaListNewPictogramaToAttach = em.getReference(pictogramaListNewPictogramaToAttach.getClass(), pictogramaListNewPictogramaToAttach.getId());
                attachedPictogramaListNew.add(pictogramaListNewPictogramaToAttach);
            }
            pictogramaListNew = attachedPictogramaListNew;
            cuenta.setPictogramaList(pictogramaListNew);
            cuenta = em.merge(cuenta);
            if (usuarionombreOld != null && !usuarionombreOld.equals(usuarionombreNew)) {
                usuarionombreOld.getCuentaList().remove(cuenta);
                usuarionombreOld = em.merge(usuarionombreOld);
            }
            if (usuarionombreNew != null && !usuarionombreNew.equals(usuarionombreOld)) {
                usuarionombreNew.getCuentaList().add(cuenta);
                usuarionombreNew = em.merge(usuarionombreNew);
            }
            for (Pictograma pictogramaListNewPictograma : pictogramaListNew) {
                if (!pictogramaListOld.contains(pictogramaListNewPictograma)) {
                    Cuenta oldCuentaidOfPictogramaListNewPictograma = pictogramaListNewPictograma.getCuentaid();
                    pictogramaListNewPictograma.setCuentaid(cuenta);
                    pictogramaListNewPictograma = em.merge(pictogramaListNewPictograma);
                    if (oldCuentaidOfPictogramaListNewPictograma != null && !oldCuentaidOfPictogramaListNewPictograma.equals(cuenta)) {
                        oldCuentaidOfPictogramaListNewPictograma.getPictogramaList().remove(pictogramaListNewPictograma);
                        oldCuentaidOfPictogramaListNewPictograma = em.merge(oldCuentaidOfPictogramaListNewPictograma);
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
            List<Pictograma> pictogramaListOrphanCheck = cuenta.getPictogramaList();
            for (Pictograma pictogramaListOrphanCheckPictograma : pictogramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Pictograma " + pictogramaListOrphanCheckPictograma + " in its pictogramaList field has a non-nullable cuentaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona usuarionombre = cuenta.getUsuarionombre();
            if (usuarionombre != null) {
                usuarionombre.getCuentaList().remove(cuenta);
                usuarionombre = em.merge(usuarionombre);
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
