/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.MantencionDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import entities.Estado;
import entities.MantencionPuntoLimpio;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class CrudMantencionPuntoLimpio implements CrudMantencionPuntoLimpioLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    @Override
    public boolean agregarMantencion(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO puntDAO = factoryDeDAOs.getPuntoLimpioDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        OperarioDAO operDAO = factoryDeDAOs.getOperarioDAO();
        MantencionDAO mantDAO = factoryDeDAOs.getMantencionDAO();
        
        OperarioMantencion operario = operDAO.findByUsername(usernameLogueado);
        Estado e = estDAO.find(nvoEstado);
        PuntoLimpio p = puntDAO.find(numPtoLimpio.intValue());
        p.setEstadoGlobal(e);
        puntDAO.update(p);
        
        MantencionPuntoLimpio nvaMant = new MantencionPuntoLimpio(p, operario, detalle);
        nvaMant.setFecha(Calendar.getInstance());
        mantDAO.insert(nvaMant);
        
        return true;
    }
}
