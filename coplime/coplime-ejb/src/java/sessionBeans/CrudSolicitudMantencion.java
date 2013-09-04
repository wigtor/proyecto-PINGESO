/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.NotificacionDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.SolicitudMantencionDAO;
import DAO.interfaces.TipoIncidenciaDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Inspector;
import entities.Notificacion;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
import entities.RevisionPuntoLimpio;
import entities.SolicitudMantencion;
import entities.TipoIncidencia;
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
        return agregarSolicitudMantencion( numPtoLimpio, usernameLogueado, numOperario, detalle, null);
    }
    
    @Override
    public boolean agregarSolicitudMantencion(Integer numPtoLimpio, String usernameLogueado, Integer numOperario, String detalle, RevisionPuntoLimpio revisionOriginadora) {
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
        nvaSolic.setRevisionOriginadora(revisionOriginadora); //puede ser null, no hay problema
        solicDAO.insert(nvaSolic);
        
        TipoIncidenciaDAO tpoIncidDAO = factoryDeDAOs.getTipoIncidenciaDAO();
        TipoIncidencia tpoIncid = tpoIncidDAO.find("Solicitud de mantención pendiente");
        if (tpoIncid == null) {
            tpoIncid = new TipoIncidencia("Solicitud de mantención pendiente", false);
            tpoIncidDAO.insert(tpoIncid);
        }
        
        NotificacionDAO notifDAO = factoryDeDAOs.getNotificacionDAO();
        Notificacion nvaNotif = new Notificacion();
        nvaNotif.setComentario("Tiene una nueva solicitud de mantención "
                .concat("pendiente del punto limpio \"").concat(p.getId().toString())
                .concat(" - ").concat(p.getNombre()).concat("\""));
        nvaNotif.setFechaHora(Calendar.getInstance());
        nvaNotif.setPuntoLimpio(p);
        nvaNotif.setResuelto(false);
        nvaNotif.setRevisado(false);
        nvaNotif.setUsuarioEncargado(opAsign.getUsuario());
        nvaNotif.setTipoIncidencia(tpoIncid);
        notifDAO.insert(nvaNotif);
        
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
    
    @Override
    public Integer obtenerCantidadSolicitudes(String username) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        SolicitudMantencionDAO solDAO = factoryDeDAOs.getSolicitudMantencionDAO();
        return solDAO.countPorRevisar(username);
    }
    
    @Override
    public boolean checkResuelta(Integer idsol, boolean check) {
        if (idsol == null) {
            return false;
        }
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        SolicitudMantencionDAO solDAO = factory.getSolicitudMantencionDAO();
        SolicitudMantencion solic = solDAO.find(idsol);
        if (solic == null ) {
            return false;
        }
        solic.setResuelto(check);
        solDAO.update(solic);
        return true;
    }
    
    @Override
    public boolean checkRevisada(Integer idsol, boolean check) {
        if (idsol == null) {
            return false;
        }
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        SolicitudMantencionDAO solDAO = factory.getSolicitudMantencionDAO();
        SolicitudMantencion solic = solDAO.find(idsol);
        if (solic == null ) {
            return false;
        }
        solic.setRevisado(check);
        solDAO.update(solic);
        return true;
    }
    
}
