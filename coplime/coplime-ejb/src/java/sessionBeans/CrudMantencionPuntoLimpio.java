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
import DAO.interfaces.UsuarioDAO;
import entities.Estado;
import entities.MantencionPuntoLimpio;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
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
public class CrudMantencionPuntoLimpio implements CrudMantencionPuntoLimpioLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    @Override
    public boolean agregarMantencion(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado) throws Exception{
        if (numPtoLimpio == null) {
            throw new Exception("El N° de punto limpio no puede ser nulo");
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO puntDAO = factoryDeDAOs.getPuntoLimpioDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        OperarioDAO operDAO = factoryDeDAOs.getOperarioDAO();
        MantencionDAO mantDAO = factoryDeDAOs.getMantencionDAO();
        
        if (usernameLogueado == null) {
            throw new Exception("El nombre de usuario no puede ser nulo");
        }
        OperarioMantencion operario = operDAO.findByUsername(usernameLogueado);
        if (operario == null) {
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
        
        MantencionPuntoLimpio nvaMant = new MantencionPuntoLimpio(p, operario, detalle);
        nvaMant.setFecha(Calendar.getInstance());
        mantDAO.insert(nvaMant);
        
        return true;
    }
    
    @Override
    public MantencionPuntoLimpio getMantencionById(Integer idMantencion) {
        if (idMantencion == null)
            return null;
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        MantencionDAO mantDAO = factoryDeDAOs.getMantencionDAO();
        return mantDAO.find(idMantencion.intValue());
        
    }

    @Override
    public Collection<MantencionPuntoLimpio> getAllMantenciones(String usernameQuienPregunta) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        MantencionDAO revDAO = factoryDeDAOs.getMantencionDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        Usuario userPreguntante = userDAO.find(usernameQuienPregunta);
        if (userPreguntante == null) {
            return null;
        }
        if (userPreguntante.getRol().getNombreRol().equals("Administrador")) {
            return revDAO.findAll();
        }
        if (userPreguntante.getRol().getNombreRol().equals("Operario")) {
            return revDAO.findAllFromOperario(userPreguntante.getId());
        }
        return null; //El inspector no tiene permiso para ver las revisiones
    }
}
