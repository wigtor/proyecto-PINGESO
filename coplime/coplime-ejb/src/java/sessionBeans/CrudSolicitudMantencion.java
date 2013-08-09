/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.SolicitudMantencionDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Inspector;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
import entities.SolicitudMantencion;
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
public class CrudSolicitudMantencion implements CrudSolicitudMantencionLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public boolean agregarSolicitudMantencion(Integer numPtoLimpio, String usernameLogueado, Integer numOperario, String detalle) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        
        PuntoLimpioDAO puntDAO = factoryDeDAOs.getPuntoLimpioDAO();
        InspectorDAO inspDAO = factoryDeDAOs.getInspectorDAO();
        OperarioDAO operDAO = factoryDeDAOs.getOperarioDAO();
        SolicitudMantencionDAO solicDAO = factoryDeDAOs.getSolicitudMantencionDAO();
        
        Inspector ins = inspDAO.findByUsername(usernameLogueado);
        OperarioMantencion opAsign = operDAO.findByRut(numOperario);
        PuntoLimpio p = puntDAO.find(numPtoLimpio.intValue());
        
        SolicitudMantencion nvaSolic = new SolicitudMantencion(p, ins, opAsign, detalle);
        nvaSolic.setFecha(Calendar.getInstance());
        solicDAO.insert(nvaSolic);
        return true;
    }

    @Override
    public SolicitudMantencion getSolicitudById(Integer idRevision) {
        if (idRevision == null)
            return null;
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        SolicitudMantencionDAO solicDAO = factoryDeDAOs.getSolicitudMantencionDAO();
        return solicDAO.find(idRevision.intValue());
        
    }
    
    @Override
    public Collection<SolicitudMantencion> getAllSolicitudes(String usernameQuienPregunta) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        SolicitudMantencionDAO solicDAO = factoryDeDAOs.getSolicitudMantencionDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        Usuario userPreguntante = userDAO.find(usernameQuienPregunta);
        if (userPreguntante == null) {
            return null;
        }
        if (userPreguntante.getRol().getNombreRol().equals("Administrador")) {
            return solicDAO.findAll();
        }
        if (userPreguntante.getRol().getNombreRol().equals("Inspector")) {
            return solicDAO.findAllFromInspector(userPreguntante.getId());
        }
        if (userPreguntante.getRol().getNombreRol().equals("Operario")) {
            return solicDAO.findAllFromOperario(userPreguntante.getId());
        }
        return null;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
