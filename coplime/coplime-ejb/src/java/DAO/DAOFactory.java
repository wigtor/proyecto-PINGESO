/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.ComunaDAO;
import DAO.interfaces.ContenedorDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.HistoricoContenedorDAO;
import DAO.interfaces.MantencionDAO;
import DAO.interfaces.MaterialDAO;
import DAO.interfaces.NotificacionDAO;
import DAO.interfaces.RevisionDAO;
import DAO.interfaces.SolicitudMantencionDAO;
import DAO.interfaces.TipoIncidenciaDAO;
import DAO.interfaces.UnidadMedidaDAO;
import DAO.interfaces.UsuarioDAO;
import entities.HistoricoContenedor;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public abstract class DAOFactory {

    /**
     * Indica que se va a utilizar JPA como proveedor de datos para los DAO
     */
    public static final int JPA = 1;
    //Poner acá otros int indicando otra implementaciones de DAO que se puedan hacer

    // Estos serán los métodos para cada DAO que puede ser creado.
    // Las factory en concreto deben implementar estos métodos.
    public abstract AdministradorDAO getAdministradorDAO();

    public abstract PuntoLimpioDAO getPuntoLimpioDAO();

    public abstract ContenedorDAO getContenedorDAO();

    public abstract UsuarioDAO getUsuarioDAO();

    public abstract RolDAO getRolDAO();

    public abstract InspectorDAO getInspectorDAO();

    public abstract OperarioDAO getOperarioDAO();

    public abstract EstadoDAO getEstadoDAO();

    public abstract TipoIncidenciaDAO getTipoIncidenciaDAO();

    public abstract MaterialDAO getMaterialDAO();

    public abstract NotificacionDAO getNotificacionDAO();

    public abstract ComunaDAO getComunaDAO();

    public abstract UnidadMedidaDAO getUnidadMedidaDAO();

    public abstract RevisionDAO getRevisionDAO();

    public abstract MantencionDAO getMantencionDAO();

    public abstract SolicitudMantencionDAO getSolicitudMantencionDAO();
    
    public abstract HistoricoContenedorDAO getHistoricoContenedorDAO();
    
    public abstract void close();

    /**
     * Obtiene una factory en concreto según la implementación que se pida.
     * @param whichFactory Identificador de la implementación de DAO que se desea utilizar
     * @param em EntityManager para los DAO
     * @return La factory en concreto que se va a utilizar que implemente la interface "DAOFactory"
     */
    public static DAOFactory getDAOFactory(int whichFactory, EntityManager em) {

        switch (whichFactory) {
            case JPA:
                return new JPADAOFactory(em);
            /*
             case ORACLE    : 
             return new OracleDAOFactory();      
             case SYBASE    : 
             return new SybaseDAOFactory();
             */
            default:
                return null;
        }
    }
}
