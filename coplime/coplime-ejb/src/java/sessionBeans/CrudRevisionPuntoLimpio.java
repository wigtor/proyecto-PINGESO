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
import DAO.interfaces.UsuarioDAO;
import entities.Estado;
import entities.Inspector;
import entities.PuntoLimpio;
import entities.RevisionPuntoLimpio;
import entities.Usuario;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class CrudRevisionPuntoLimpio implements CrudRevisionPuntoLimpioLocal {
    @EJB
    private CrudSolicitudMantencionLocal crudSolicitudMantencion;
    
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public boolean agregarRevision(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado) throws Exception {
        if (numPtoLimpio == null) {
            throw new Exception("El N° de punto limpio no puede ser nulo");
        }
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
    public boolean agregarRevisionConSolicitud(Integer numPtoLimpio, String usernameLogueado, Integer numOperario, String detalle, Integer nvoEstado) throws Exception{
        if (numPtoLimpio == null) {
            throw new Exception("El N° de punto limpio no puede ser nulo");
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO puntDAO = factoryDeDAOs.getPuntoLimpioDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        InspectorDAO inspDAO = factoryDeDAOs.getInspectorDAO();
        RevisionDAO revDAO = factoryDeDAOs.getRevisionDAO();
        
        if (usernameLogueado == null) {
            throw new Exception("El nombre de usuario no puede ser nulo");
        }
        Inspector ins = inspDAO.findByUsername(usernameLogueado);
        if (ins == null) {
            throw new Exception("El nombre de usuario no ha sido encontrado");
        }
        if (nvoEstado == null) {
            throw new Exception("El nuevo estado del punto limpio no puede ser nulo");
        }
        Estado e = estDAO.find(nvoEstado);
        if (e == null) {
            throw new Exception("El estado no ha sido encontrado");
        }
        PuntoLimpio p = puntDAO.find(numPtoLimpio.intValue());
        if (p == null) {
            throw new Exception("El punto limpio no ha sido encontrado");
        }
        p.setEstadoGlobal(e);
        puntDAO.update(p);
        
        RevisionPuntoLimpio nvaRev = new RevisionPuntoLimpio(p, ins, detalle);
        nvaRev.setFecha(Calendar.getInstance());
        revDAO.insert(nvaRev);
        
        crudSolicitudMantencion.agregarSolicitudMantencion(numPtoLimpio, usernameLogueado, numOperario, detalle, nvaRev);
        
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
