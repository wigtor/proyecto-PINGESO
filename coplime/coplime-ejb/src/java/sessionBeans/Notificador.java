/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.NotificacionDAO;
import DAO.interfaces.SolicitudMantencionDAO;
import entities.Notificacion;
import entities.NotificacionDeUsuario;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class Notificador implements NotificadorLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    @Override
    public Integer obtenerCantidadNotificaciones(String username) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        NotificacionDAO notifDAO = factoryDeDAOs.getNotificacionDAO();
        return notifDAO.countPorRevisar(username);
    }
    
    @Override
    public Integer obtenerCantidadSolicitudesMantencion(String username) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        SolicitudMantencionDAO solicDAO = factoryDeDAOs.getSolicitudMantencionDAO();
        return solicDAO.countPorRevisar(username);
    }
    
    @Override
    public Collection<Notificacion> getAllNotificaciones(String username) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        NotificacionDAO notifDAO = factoryDeDAOs.getNotificacionDAO();
        return notifDAO.findAllOfUser(username);
    }
    
    @Override
    public Notificacion getNotificacion(Integer id) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        NotificacionDAO notifDAO = factoryDeDAOs.getNotificacionDAO();
        return notifDAO.find(id);
    }
    
    @Override
    public String getOrigenIncidencia(Notificacion notif) {
        if (this.isNotificacionUsuario(notif)) {
            return "Notificación de usuario";
        }
        return "Notificación del sistema";
    }

    @Override
    public boolean isNotificacionUsuario(Notificacion notif) {
        if (NotificacionDeUsuario.class.isInstance(notif)) {
            return true;
        }
        return false;
        
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean checkResuelta(Integer idNotif, boolean check) {
        if (idNotif == null) {
            return false;
        }
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        NotificacionDAO notifDAO = factory.getNotificacionDAO();
        Notificacion notif = notifDAO.find(idNotif);
        System.out.println("idNotif: "+idNotif + " poniendo en: "+check + " busqueda: "+notif);
        if (notif == null ) {
            return false;
        }
        notif.setResuelto(check);
        notifDAO.update(notif);
        return true;
    }
    
    @Override
    public boolean checkRevisada(Integer idNotif, boolean check) {
        if (idNotif == null) {
            return false;
        }
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        NotificacionDAO notifDAO = factory.getNotificacionDAO();
        Notificacion notif = notifDAO.find(idNotif);
        System.out.println("idNotif: "+idNotif + " poniendo en: "+check + " busqueda: "+notif);
        if (notif == null ) {
            return false;
        }
        notif.setRevisado(check);
        notifDAO.update(notif);
        return true;
    }
    
    @Override
    public boolean eliminarNotificacion(Integer idNotif){
        if (idNotif != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            NotificacionDAO notifDAO = factoryDeDAOs.getNotificacionDAO();
            return notifDAO.delete(idNotif);
        }
        return false;
    }

}
