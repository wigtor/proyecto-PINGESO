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
    public boolean agregarEstadoPuntoLimpio(String nuevoEstadoPL) throws Exception {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            EstadoDAO estadosDAO = fabricaDAO.getEstadoDAO();
            if(estadosDAO.find(nuevoEstadoPL)!=null){
                throw new Exception("El estado ya existe.");
            } else {
                Estado nuevoEstado;
                nuevoEstado = new Estado();
                nuevoEstado.setNombreEstado(nuevoEstadoPL);
                estadosDAO.insert(nuevoEstado);
            }
        } catch (Exception e){
            Logger.getLogger(CrudEstadoPuntoLimpio.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar el estado \"".concat(nuevoEstadoPL).concat("\":".concat(e.toString())));
            throw new Exception ("Error al intentar agregar el nuevo estado.");
        }
        return true;
    }

    @Override
    public boolean editarEstadoPuntoLimpio(Integer idEstado, String antiguoEstadoPL, String nuevoEstadoPL) throws Exception {
        try{
            //failsafe
            if(antiguoEstadoPL.equals(nuevoEstadoPL)){
               throw new Exception("Los nombres de estado son iguales.");
            }
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            EstadoDAO estadosDAO = fabricaDAO.getEstadoDAO();
            Estado estadoEditar;
            estadoEditar = estadosDAO.find(idEstado.intValue());
            if (estadoEditar == null){
                throw new Exception("No se puede editar el estado, el ID ".concat(idEstado.toString()).concat(" no existe."));
            } else {
                if(!(estadoEditar.getNombreEstado().equals(antiguoEstadoPL))){
                   throw new Exception("No se puede editar el estado, el nombre asociado al ID es diferente. "); 
                } else {
                   estadoEditar.setNombreEstado(nuevoEstadoPL);
                   estadosDAO.update(estadoEditar);
                   return true; 
                }
            }    
            
        } catch (Exception e){
            Logger.getLogger(CrudEstadoPuntoLimpio.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar el estado \"".concat(antiguoEstadoPL).concat("\":".concat(e.toString())));
            throw new Exception ("Error al intentar editar el estado.");
        }
    }

    @Override
    public boolean eliminarEstadoPuntoLimpio(Integer idEstado, String eliminarEstadoPL) throws Exception {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            EstadoDAO estadosDAO = fabricaDAO.getEstadoDAO();
            Estado estadoEliminar;
            estadoEliminar = estadosDAO.find(idEstado.intValue());
            if (estadoEliminar == null){
                throw new Exception("No se puede eliminar el estado, el identificador ".concat(idEstado.toString()).concat(" no existe."));
            } else {
                if(!(estadoEliminar.getNombreEstado().equals(eliminarEstadoPL))){
                    throw new Exception ("No se puede eliminar el estado, el nombre asociado al ID es diferente.");
                } else {
                    estadosDAO.delete(estadoEliminar);
                    return true;
                }
            }
        } catch (Exception e){
            Logger.getLogger(CrudEstadoPuntoLimpio.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar el estado \"".concat(eliminarEstadoPL).concat("\":".concat(e.toString())));
            throw new Exception("Error al intentar eliminar el estado.");
        }
    }

    @Override
    public Estado getEstadoPuntoLimpioPorNombre(String nombreEstadoPLBusq) {
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
