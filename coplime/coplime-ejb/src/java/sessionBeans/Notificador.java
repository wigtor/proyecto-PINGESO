/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.NotificacionDAO;
import entities.Notificacion;
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
