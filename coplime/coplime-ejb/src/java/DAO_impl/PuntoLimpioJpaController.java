/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO_impl;

import DAO_impl.exceptions.NonexistentEntityException;
import DAO_impl.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Inspector;
import entities.Contenedor;
import java.util.ArrayList;
import java.util.List;
import entities.MantencionPuntoLimpio;
import entities.RevisionPuntoLimpio;
import entities.SolicitudMantencion;
import entities.Notificacion;
import entities.PuntoLimpio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author victor
 */
public class PuntoLimpioJpaController implements Serializable {

    public PuntoLimpioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PuntoLimpio puntoLimpio) throws RollbackFailureException, Exception {
        if (puntoLimpio.getContenedores() == null) {
            puntoLimpio.setContenedores(new ArrayList<Contenedor>());
        }
        if (puntoLimpio.getMantenciones() == null) {
            puntoLimpio.setMantenciones(new ArrayList<MantencionPuntoLimpio>());
        }
        if (puntoLimpio.getRevisiones() == null) {
            puntoLimpio.setRevisiones(new ArrayList<RevisionPuntoLimpio>());
        }
        if (puntoLimpio.getSolicitudesMantencion() == null) {
            puntoLimpio.setSolicitudesMantencion(new ArrayList<SolicitudMantencion>());
        }
        if (puntoLimpio.getNotificaciones() == null) {
            puntoLimpio.setNotificaciones(new ArrayList<Notificacion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Inspector inspectorEncargado = puntoLimpio.getInspectorEncargado();
            if (inspectorEncargado != null) {
                inspectorEncargado = em.getReference(inspectorEncargado.getClass(), inspectorEncargado.getId());
                puntoLimpio.setInspectorEncargado(inspectorEncargado);
            }
            List<Contenedor> attachedContenedores = new ArrayList<Contenedor>();
            for (Contenedor contenedoresContenedorToAttach : puntoLimpio.getContenedores()) {
                contenedoresContenedorToAttach = em.getReference(contenedoresContenedorToAttach.getClass(), contenedoresContenedorToAttach.getId());
                attachedContenedores.add(contenedoresContenedorToAttach);
            }
            puntoLimpio.setContenedores(attachedContenedores);
            List<MantencionPuntoLimpio> attachedMantenciones = new ArrayList<MantencionPuntoLimpio>();
            for (MantencionPuntoLimpio mantencionesMantencionPuntoLimpioToAttach : puntoLimpio.getMantenciones()) {
                mantencionesMantencionPuntoLimpioToAttach = em.getReference(mantencionesMantencionPuntoLimpioToAttach.getClass(), mantencionesMantencionPuntoLimpioToAttach.getNum());
                attachedMantenciones.add(mantencionesMantencionPuntoLimpioToAttach);
            }
            puntoLimpio.setMantenciones(attachedMantenciones);
            List<RevisionPuntoLimpio> attachedRevisiones = new ArrayList<RevisionPuntoLimpio>();
            for (RevisionPuntoLimpio revisionesRevisionPuntoLimpioToAttach : puntoLimpio.getRevisiones()) {
                revisionesRevisionPuntoLimpioToAttach = em.getReference(revisionesRevisionPuntoLimpioToAttach.getClass(), revisionesRevisionPuntoLimpioToAttach.getNum());
                attachedRevisiones.add(revisionesRevisionPuntoLimpioToAttach);
            }
            puntoLimpio.setRevisiones(attachedRevisiones);
            List<SolicitudMantencion> attachedSolicitudesMantencion = new ArrayList<SolicitudMantencion>();
            for (SolicitudMantencion solicitudesMantencionSolicitudMantencionToAttach : puntoLimpio.getSolicitudesMantencion()) {
                solicitudesMantencionSolicitudMantencionToAttach = em.getReference(solicitudesMantencionSolicitudMantencionToAttach.getClass(), solicitudesMantencionSolicitudMantencionToAttach.getNum());
                attachedSolicitudesMantencion.add(solicitudesMantencionSolicitudMantencionToAttach);
            }
            puntoLimpio.setSolicitudesMantencion(attachedSolicitudesMantencion);
            List<Notificacion> attachedNotificaciones = new ArrayList<Notificacion>();
            for (Notificacion notificacionesNotificacionToAttach : puntoLimpio.getNotificaciones()) {
                notificacionesNotificacionToAttach = em.getReference(notificacionesNotificacionToAttach.getClass(), notificacionesNotificacionToAttach.getId());
                attachedNotificaciones.add(notificacionesNotificacionToAttach);
            }
            puntoLimpio.setNotificaciones(attachedNotificaciones);
            em.persist(puntoLimpio);
            if (inspectorEncargado != null) {
                inspectorEncargado.getPuntosLimpios().add(puntoLimpio);
                inspectorEncargado = em.merge(inspectorEncargado);
            }
            for (Contenedor contenedoresContenedor : puntoLimpio.getContenedores()) {
                PuntoLimpio oldPuntoLimpioOfContenedoresContenedor = contenedoresContenedor.getPuntoLimpio();
                contenedoresContenedor.setPuntoLimpio(puntoLimpio);
                contenedoresContenedor = em.merge(contenedoresContenedor);
                if (oldPuntoLimpioOfContenedoresContenedor != null) {
                    oldPuntoLimpioOfContenedoresContenedor.getContenedores().remove(contenedoresContenedor);
                    oldPuntoLimpioOfContenedoresContenedor = em.merge(oldPuntoLimpioOfContenedoresContenedor);
                }
            }
            for (MantencionPuntoLimpio mantencionesMantencionPuntoLimpio : puntoLimpio.getMantenciones()) {
                PuntoLimpio oldPuntoLimpioOfMantencionesMantencionPuntoLimpio = mantencionesMantencionPuntoLimpio.getPuntoLimpio();
                mantencionesMantencionPuntoLimpio.setPuntoLimpio(puntoLimpio);
                mantencionesMantencionPuntoLimpio = em.merge(mantencionesMantencionPuntoLimpio);
                if (oldPuntoLimpioOfMantencionesMantencionPuntoLimpio != null) {
                    oldPuntoLimpioOfMantencionesMantencionPuntoLimpio.getMantenciones().remove(mantencionesMantencionPuntoLimpio);
                    oldPuntoLimpioOfMantencionesMantencionPuntoLimpio = em.merge(oldPuntoLimpioOfMantencionesMantencionPuntoLimpio);
                }
            }
            for (RevisionPuntoLimpio revisionesRevisionPuntoLimpio : puntoLimpio.getRevisiones()) {
                PuntoLimpio oldPuntoLimpioOfRevisionesRevisionPuntoLimpio = revisionesRevisionPuntoLimpio.getPuntoLimpio();
                revisionesRevisionPuntoLimpio.setPuntoLimpio(puntoLimpio);
                revisionesRevisionPuntoLimpio = em.merge(revisionesRevisionPuntoLimpio);
                if (oldPuntoLimpioOfRevisionesRevisionPuntoLimpio != null) {
                    oldPuntoLimpioOfRevisionesRevisionPuntoLimpio.getRevisiones().remove(revisionesRevisionPuntoLimpio);
                    oldPuntoLimpioOfRevisionesRevisionPuntoLimpio = em.merge(oldPuntoLimpioOfRevisionesRevisionPuntoLimpio);
                }
            }
            for (SolicitudMantencion solicitudesMantencionSolicitudMantencion : puntoLimpio.getSolicitudesMantencion()) {
                PuntoLimpio oldPuntoLimpioOfSolicitudesMantencionSolicitudMantencion = solicitudesMantencionSolicitudMantencion.getPuntoLimpio();
                solicitudesMantencionSolicitudMantencion.setPuntoLimpio(puntoLimpio);
                solicitudesMantencionSolicitudMantencion = em.merge(solicitudesMantencionSolicitudMantencion);
                if (oldPuntoLimpioOfSolicitudesMantencionSolicitudMantencion != null) {
                    oldPuntoLimpioOfSolicitudesMantencionSolicitudMantencion.getSolicitudesMantencion().remove(solicitudesMantencionSolicitudMantencion);
                    oldPuntoLimpioOfSolicitudesMantencionSolicitudMantencion = em.merge(oldPuntoLimpioOfSolicitudesMantencionSolicitudMantencion);
                }
            }
            for (Notificacion notificacionesNotificacion : puntoLimpio.getNotificaciones()) {
                PuntoLimpio oldPuntoLimpioOfNotificacionesNotificacion = notificacionesNotificacion.getPuntoLimpio();
                notificacionesNotificacion.setPuntoLimpio(puntoLimpio);
                notificacionesNotificacion = em.merge(notificacionesNotificacion);
                if (oldPuntoLimpioOfNotificacionesNotificacion != null) {
                    oldPuntoLimpioOfNotificacionesNotificacion.getNotificaciones().remove(notificacionesNotificacion);
                    oldPuntoLimpioOfNotificacionesNotificacion = em.merge(oldPuntoLimpioOfNotificacionesNotificacion);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PuntoLimpio puntoLimpio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PuntoLimpio persistentPuntoLimpio = em.find(PuntoLimpio.class, puntoLimpio.getId());
            Inspector inspectorEncargadoOld = persistentPuntoLimpio.getInspectorEncargado();
            Inspector inspectorEncargadoNew = puntoLimpio.getInspectorEncargado();
            List<Contenedor> contenedoresOld = persistentPuntoLimpio.getContenedores();
            List<Contenedor> contenedoresNew = puntoLimpio.getContenedores();
            List<MantencionPuntoLimpio> mantencionesOld = persistentPuntoLimpio.getMantenciones();
            List<MantencionPuntoLimpio> mantencionesNew = puntoLimpio.getMantenciones();
            List<RevisionPuntoLimpio> revisionesOld = persistentPuntoLimpio.getRevisiones();
            List<RevisionPuntoLimpio> revisionesNew = puntoLimpio.getRevisiones();
            List<SolicitudMantencion> solicitudesMantencionOld = persistentPuntoLimpio.getSolicitudesMantencion();
            List<SolicitudMantencion> solicitudesMantencionNew = puntoLimpio.getSolicitudesMantencion();
            List<Notificacion> notificacionesOld = persistentPuntoLimpio.getNotificaciones();
            List<Notificacion> notificacionesNew = puntoLimpio.getNotificaciones();
            if (inspectorEncargadoNew != null) {
                inspectorEncargadoNew = em.getReference(inspectorEncargadoNew.getClass(), inspectorEncargadoNew.getId());
                puntoLimpio.setInspectorEncargado(inspectorEncargadoNew);
            }
            List<Contenedor> attachedContenedoresNew = new ArrayList<Contenedor>();
            for (Contenedor contenedoresNewContenedorToAttach : contenedoresNew) {
                contenedoresNewContenedorToAttach = em.getReference(contenedoresNewContenedorToAttach.getClass(), contenedoresNewContenedorToAttach.getId());
                attachedContenedoresNew.add(contenedoresNewContenedorToAttach);
            }
            contenedoresNew = attachedContenedoresNew;
            puntoLimpio.setContenedores(contenedoresNew);
            List<MantencionPuntoLimpio> attachedMantencionesNew = new ArrayList<MantencionPuntoLimpio>();
            for (MantencionPuntoLimpio mantencionesNewMantencionPuntoLimpioToAttach : mantencionesNew) {
                mantencionesNewMantencionPuntoLimpioToAttach = em.getReference(mantencionesNewMantencionPuntoLimpioToAttach.getClass(), mantencionesNewMantencionPuntoLimpioToAttach.getNum());
                attachedMantencionesNew.add(mantencionesNewMantencionPuntoLimpioToAttach);
            }
            mantencionesNew = attachedMantencionesNew;
            puntoLimpio.setMantenciones(mantencionesNew);
            List<RevisionPuntoLimpio> attachedRevisionesNew = new ArrayList<RevisionPuntoLimpio>();
            for (RevisionPuntoLimpio revisionesNewRevisionPuntoLimpioToAttach : revisionesNew) {
                revisionesNewRevisionPuntoLimpioToAttach = em.getReference(revisionesNewRevisionPuntoLimpioToAttach.getClass(), revisionesNewRevisionPuntoLimpioToAttach.getNum());
                attachedRevisionesNew.add(revisionesNewRevisionPuntoLimpioToAttach);
            }
            revisionesNew = attachedRevisionesNew;
            puntoLimpio.setRevisiones(revisionesNew);
            List<SolicitudMantencion> attachedSolicitudesMantencionNew = new ArrayList<SolicitudMantencion>();
            for (SolicitudMantencion solicitudesMantencionNewSolicitudMantencionToAttach : solicitudesMantencionNew) {
                solicitudesMantencionNewSolicitudMantencionToAttach = em.getReference(solicitudesMantencionNewSolicitudMantencionToAttach.getClass(), solicitudesMantencionNewSolicitudMantencionToAttach.getNum());
                attachedSolicitudesMantencionNew.add(solicitudesMantencionNewSolicitudMantencionToAttach);
            }
            solicitudesMantencionNew = attachedSolicitudesMantencionNew;
            puntoLimpio.setSolicitudesMantencion(solicitudesMantencionNew);
            List<Notificacion> attachedNotificacionesNew = new ArrayList<Notificacion>();
            for (Notificacion notificacionesNewNotificacionToAttach : notificacionesNew) {
                notificacionesNewNotificacionToAttach = em.getReference(notificacionesNewNotificacionToAttach.getClass(), notificacionesNewNotificacionToAttach.getId());
                attachedNotificacionesNew.add(notificacionesNewNotificacionToAttach);
            }
            notificacionesNew = attachedNotificacionesNew;
            puntoLimpio.setNotificaciones(notificacionesNew);
            puntoLimpio = em.merge(puntoLimpio);
            if (inspectorEncargadoOld != null && !inspectorEncargadoOld.equals(inspectorEncargadoNew)) {
                inspectorEncargadoOld.getPuntosLimpios().remove(puntoLimpio);
                inspectorEncargadoOld = em.merge(inspectorEncargadoOld);
            }
            if (inspectorEncargadoNew != null && !inspectorEncargadoNew.equals(inspectorEncargadoOld)) {
                inspectorEncargadoNew.getPuntosLimpios().add(puntoLimpio);
                inspectorEncargadoNew = em.merge(inspectorEncargadoNew);
            }
            for (Contenedor contenedoresOldContenedor : contenedoresOld) {
                if (!contenedoresNew.contains(contenedoresOldContenedor)) {
                    contenedoresOldContenedor.setPuntoLimpio(null);
                    contenedoresOldContenedor = em.merge(contenedoresOldContenedor);
                }
            }
            for (Contenedor contenedoresNewContenedor : contenedoresNew) {
                if (!contenedoresOld.contains(contenedoresNewContenedor)) {
                    PuntoLimpio oldPuntoLimpioOfContenedoresNewContenedor = contenedoresNewContenedor.getPuntoLimpio();
                    contenedoresNewContenedor.setPuntoLimpio(puntoLimpio);
                    contenedoresNewContenedor = em.merge(contenedoresNewContenedor);
                    if (oldPuntoLimpioOfContenedoresNewContenedor != null && !oldPuntoLimpioOfContenedoresNewContenedor.equals(puntoLimpio)) {
                        oldPuntoLimpioOfContenedoresNewContenedor.getContenedores().remove(contenedoresNewContenedor);
                        oldPuntoLimpioOfContenedoresNewContenedor = em.merge(oldPuntoLimpioOfContenedoresNewContenedor);
                    }
                }
            }
            for (MantencionPuntoLimpio mantencionesOldMantencionPuntoLimpio : mantencionesOld) {
                if (!mantencionesNew.contains(mantencionesOldMantencionPuntoLimpio)) {
                    mantencionesOldMantencionPuntoLimpio.setPuntoLimpio(null);
                    mantencionesOldMantencionPuntoLimpio = em.merge(mantencionesOldMantencionPuntoLimpio);
                }
            }
            for (MantencionPuntoLimpio mantencionesNewMantencionPuntoLimpio : mantencionesNew) {
                if (!mantencionesOld.contains(mantencionesNewMantencionPuntoLimpio)) {
                    PuntoLimpio oldPuntoLimpioOfMantencionesNewMantencionPuntoLimpio = mantencionesNewMantencionPuntoLimpio.getPuntoLimpio();
                    mantencionesNewMantencionPuntoLimpio.setPuntoLimpio(puntoLimpio);
                    mantencionesNewMantencionPuntoLimpio = em.merge(mantencionesNewMantencionPuntoLimpio);
                    if (oldPuntoLimpioOfMantencionesNewMantencionPuntoLimpio != null && !oldPuntoLimpioOfMantencionesNewMantencionPuntoLimpio.equals(puntoLimpio)) {
                        oldPuntoLimpioOfMantencionesNewMantencionPuntoLimpio.getMantenciones().remove(mantencionesNewMantencionPuntoLimpio);
                        oldPuntoLimpioOfMantencionesNewMantencionPuntoLimpio = em.merge(oldPuntoLimpioOfMantencionesNewMantencionPuntoLimpio);
                    }
                }
            }
            for (RevisionPuntoLimpio revisionesOldRevisionPuntoLimpio : revisionesOld) {
                if (!revisionesNew.contains(revisionesOldRevisionPuntoLimpio)) {
                    revisionesOldRevisionPuntoLimpio.setPuntoLimpio(null);
                    revisionesOldRevisionPuntoLimpio = em.merge(revisionesOldRevisionPuntoLimpio);
                }
            }
            for (RevisionPuntoLimpio revisionesNewRevisionPuntoLimpio : revisionesNew) {
                if (!revisionesOld.contains(revisionesNewRevisionPuntoLimpio)) {
                    PuntoLimpio oldPuntoLimpioOfRevisionesNewRevisionPuntoLimpio = revisionesNewRevisionPuntoLimpio.getPuntoLimpio();
                    revisionesNewRevisionPuntoLimpio.setPuntoLimpio(puntoLimpio);
                    revisionesNewRevisionPuntoLimpio = em.merge(revisionesNewRevisionPuntoLimpio);
                    if (oldPuntoLimpioOfRevisionesNewRevisionPuntoLimpio != null && !oldPuntoLimpioOfRevisionesNewRevisionPuntoLimpio.equals(puntoLimpio)) {
                        oldPuntoLimpioOfRevisionesNewRevisionPuntoLimpio.getRevisiones().remove(revisionesNewRevisionPuntoLimpio);
                        oldPuntoLimpioOfRevisionesNewRevisionPuntoLimpio = em.merge(oldPuntoLimpioOfRevisionesNewRevisionPuntoLimpio);
                    }
                }
            }
            for (SolicitudMantencion solicitudesMantencionOldSolicitudMantencion : solicitudesMantencionOld) {
                if (!solicitudesMantencionNew.contains(solicitudesMantencionOldSolicitudMantencion)) {
                    solicitudesMantencionOldSolicitudMantencion.setPuntoLimpio(null);
                    solicitudesMantencionOldSolicitudMantencion = em.merge(solicitudesMantencionOldSolicitudMantencion);
                }
            }
            for (SolicitudMantencion solicitudesMantencionNewSolicitudMantencion : solicitudesMantencionNew) {
                if (!solicitudesMantencionOld.contains(solicitudesMantencionNewSolicitudMantencion)) {
                    PuntoLimpio oldPuntoLimpioOfSolicitudesMantencionNewSolicitudMantencion = solicitudesMantencionNewSolicitudMantencion.getPuntoLimpio();
                    solicitudesMantencionNewSolicitudMantencion.setPuntoLimpio(puntoLimpio);
                    solicitudesMantencionNewSolicitudMantencion = em.merge(solicitudesMantencionNewSolicitudMantencion);
                    if (oldPuntoLimpioOfSolicitudesMantencionNewSolicitudMantencion != null && !oldPuntoLimpioOfSolicitudesMantencionNewSolicitudMantencion.equals(puntoLimpio)) {
                        oldPuntoLimpioOfSolicitudesMantencionNewSolicitudMantencion.getSolicitudesMantencion().remove(solicitudesMantencionNewSolicitudMantencion);
                        oldPuntoLimpioOfSolicitudesMantencionNewSolicitudMantencion = em.merge(oldPuntoLimpioOfSolicitudesMantencionNewSolicitudMantencion);
                    }
                }
            }
            for (Notificacion notificacionesOldNotificacion : notificacionesOld) {
                if (!notificacionesNew.contains(notificacionesOldNotificacion)) {
                    notificacionesOldNotificacion.setPuntoLimpio(null);
                    notificacionesOldNotificacion = em.merge(notificacionesOldNotificacion);
                }
            }
            for (Notificacion notificacionesNewNotificacion : notificacionesNew) {
                if (!notificacionesOld.contains(notificacionesNewNotificacion)) {
                    PuntoLimpio oldPuntoLimpioOfNotificacionesNewNotificacion = notificacionesNewNotificacion.getPuntoLimpio();
                    notificacionesNewNotificacion.setPuntoLimpio(puntoLimpio);
                    notificacionesNewNotificacion = em.merge(notificacionesNewNotificacion);
                    if (oldPuntoLimpioOfNotificacionesNewNotificacion != null && !oldPuntoLimpioOfNotificacionesNewNotificacion.equals(puntoLimpio)) {
                        oldPuntoLimpioOfNotificacionesNewNotificacion.getNotificaciones().remove(notificacionesNewNotificacion);
                        oldPuntoLimpioOfNotificacionesNewNotificacion = em.merge(oldPuntoLimpioOfNotificacionesNewNotificacion);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = puntoLimpio.getId();
                if (findPuntoLimpio(id) == null) {
                    throw new NonexistentEntityException("The puntoLimpio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PuntoLimpio puntoLimpio;
            try {
                puntoLimpio = em.getReference(PuntoLimpio.class, id);
                puntoLimpio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puntoLimpio with id " + id + " no longer exists.", enfe);
            }
            Inspector inspectorEncargado = puntoLimpio.getInspectorEncargado();
            if (inspectorEncargado != null) {
                inspectorEncargado.getPuntosLimpios().remove(puntoLimpio);
                inspectorEncargado = em.merge(inspectorEncargado);
            }
            List<Contenedor> contenedores = puntoLimpio.getContenedores();
            for (Contenedor contenedoresContenedor : contenedores) {
                contenedoresContenedor.setPuntoLimpio(null);
                contenedoresContenedor = em.merge(contenedoresContenedor);
            }
            List<MantencionPuntoLimpio> mantenciones = puntoLimpio.getMantenciones();
            for (MantencionPuntoLimpio mantencionesMantencionPuntoLimpio : mantenciones) {
                mantencionesMantencionPuntoLimpio.setPuntoLimpio(null);
                mantencionesMantencionPuntoLimpio = em.merge(mantencionesMantencionPuntoLimpio);
            }
            List<RevisionPuntoLimpio> revisiones = puntoLimpio.getRevisiones();
            for (RevisionPuntoLimpio revisionesRevisionPuntoLimpio : revisiones) {
                revisionesRevisionPuntoLimpio.setPuntoLimpio(null);
                revisionesRevisionPuntoLimpio = em.merge(revisionesRevisionPuntoLimpio);
            }
            List<SolicitudMantencion> solicitudesMantencion = puntoLimpio.getSolicitudesMantencion();
            for (SolicitudMantencion solicitudesMantencionSolicitudMantencion : solicitudesMantencion) {
                solicitudesMantencionSolicitudMantencion.setPuntoLimpio(null);
                solicitudesMantencionSolicitudMantencion = em.merge(solicitudesMantencionSolicitudMantencion);
            }
            List<Notificacion> notificaciones = puntoLimpio.getNotificaciones();
            for (Notificacion notificacionesNotificacion : notificaciones) {
                notificacionesNotificacion.setPuntoLimpio(null);
                notificacionesNotificacion = em.merge(notificacionesNotificacion);
            }
            em.remove(puntoLimpio);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PuntoLimpio> findPuntoLimpioEntities() {
        return findPuntoLimpioEntities(true, -1, -1);
    }

    public List<PuntoLimpio> findPuntoLimpioEntities(int maxResults, int firstResult) {
        return findPuntoLimpioEntities(false, maxResults, firstResult);
    }

    private List<PuntoLimpio> findPuntoLimpioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PuntoLimpio.class));
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

    public PuntoLimpio findPuntoLimpio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PuntoLimpio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuntoLimpioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PuntoLimpio> rt = cq.from(PuntoLimpio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
