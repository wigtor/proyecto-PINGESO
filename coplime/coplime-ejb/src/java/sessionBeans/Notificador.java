/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.NotificacionDAO;
import entities.Notificacion;
import entities.NotificacionDeUsuario;
import java.io.File;
import java.io.FileInputStream;
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

}
