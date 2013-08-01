/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RevisionDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Estado;
import entities.Inspector;
import entities.PuntoLimpio;
import entities.RevisionPuntoLimpio;
import entities.Usuario;
import java.util.Calendar;
import java.util.Collection;
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

    @Override
    public RevisionPuntoLimpio getRevisionById(Integer idRevision) {
        if (idRevision == null)
            return null;
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        RevisionDAO revDAO = factoryDeDAOs.getRevisionDAO();
        return revDAO.find(idRevision.intValue());
        
    }

    @Override
    public Collection<RevisionPuntoLimpio> getAllRevisiones(String usernameQuienPregunta) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        RevisionDAO revDAO = factoryDeDAOs.getRevisionDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        Usuario userPreguntante = userDAO.find(usernameQuienPregunta);
        if (userPreguntante == null) {
            return null;
        }
        if (userPreguntante.getRol().getNombreRol().equals("Administrador")) {
            return revDAO.findAll();
        }
        if (userPreguntante.getRol().getNombreRol().equals("Inspector")) {
            return revDAO.findAllFromInspector(userPreguntante.getId());
        }
        return null; //El operario no tiene permiso para ver las revisiones
    }
}
