/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.EstadoDAO;
import entities.Estado;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Armando
 */
@Stateless
public class CrudEstadoPuntoLimpio implements CrudEstadoPuntoLimpioLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    @Override
    public boolean agregarEstadoPuntoLimpio(String nuevoEstadoPL) {
        try{
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        EstadoDAO estadosDAO = fabricaDAO.getEstadoDAO();
        Estado nuevoEstado = new Estado(nuevoEstadoPL);
        estadosDAO.insert(nuevoEstado);
        } catch (Exception e){
            Logger.getLogger(CrudEstadoPuntoLimpio.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar el estado \"".concat(nuevoEstadoPL).concat("\":".concat(e.toString())));
            return false;
        }
        return true;
    }

    @Override
    public boolean editarEstadoPuntoLimpio(String antiguoEstadoPL, String nuevoEstadoPL) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            EstadoDAO estadosDAO = fabricaDAO.getEstadoDAO();
            Estado estadoEditar;
            estadoEditar = estadosDAO.find(antiguoEstadoPL);
            if (estadoEditar != null){
                estadoEditar.setNombreEstado(nuevoEstadoPL);
                estadosDAO.update(estadoEditar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudEstadoPuntoLimpio.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar el estado \"".concat(antiguoEstadoPL).concat("\":".concat(e.toString())));
            return false;
        }
    }

    @Override
    public boolean eliminarEstadoPuntoLimpio(String eliminarEstadoPL) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            EstadoDAO estadosDAO = fabricaDAO.getEstadoDAO();
            Estado estadoEliminar;
            estadoEliminar = estadosDAO.find(eliminarEstadoPL);
            if (estadoEliminar != null){
                estadosDAO.delete(estadoEliminar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudEstadoPuntoLimpio.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar el estado \"".concat(eliminarEstadoPL).concat("\":".concat(e.toString())));
            return false;
        }
    }

    @Override
    public Estado getEstadoPuntoLimpio(String nombreEstadoPLBusq) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            EstadoDAO estadosDAO = fabricaDAO.getEstadoDAO();
            Estado est;
            est = estadosDAO.find(nombreEstadoPLBusq);
            if (est != null){
                return est;
            } else {
                return null;
            }
        } catch (Exception e){
            Logger.getLogger(CrudEstadoPuntoLimpio.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar buscar el estado \"".concat(nombreEstadoPLBusq).concat("\":".concat(e.toString())));
            return null;
        }
    }

    @Override
    public Collection getAllEstadosPuntoLimpio() {
        Collection resultado;
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            EstadoDAO estadosDAO = fabricaDAO.getEstadoDAO();
            resultado = estadosDAO.findAll();
            return resultado;
        } catch (Exception e) {
            Logger.getLogger(CrudEstadoPuntoLimpio.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar recuperar la lista de estados: ".concat(e.toString()));
            return new LinkedList();
        }
    }
    
    
}
