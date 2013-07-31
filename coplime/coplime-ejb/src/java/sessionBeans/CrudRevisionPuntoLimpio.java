/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RevisionDAO;
import entities.Estado;
import entities.Inspector;
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
public class CrudRevisionPuntoLimpio implements CrudRevisionPuntoLimpioLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public boolean agregarRevision(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO puntDAO = factoryDeDAOs.getPuntoLimpioDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        InspectorDAO inspDAO = factoryDeDAOs.getInspectorDAO();
        RevisionDAO revDAO = factoryDeDAOs.getRevisionDAO();
        
        Inspector ins = inspDAO.findByUsername(usernameLogueado);
        Estado e = estDAO.find(nvoEstado);
        PuntoLimpio p = puntDAO.find(numPtoLimpio.intValue());
        p.setEstadoGlobal(e);
        puntDAO.update(p);
        
        entities.RevisionPuntoLimpio nvaRev = new entities.RevisionPuntoLimpio(p, ins, detalle);
        nvaRev.setFecha(Calendar.getInstance());
        revDAO.insert(nvaRev);
        
        return true;
    }
}
