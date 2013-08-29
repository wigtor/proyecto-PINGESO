/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.MaterialDAO;
import DAO.interfaces.UnidadMedidaDAO;
import entities.Material;
import entities.UnidadMedida;
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
public class CrudUnidadMedida implements CrudUnidadMedidaLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean agregarUnidadMedida(String nuevaUnidad) {
        try{
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UnidadMedidaDAO unidadesDAO = fabricaDAO.getUnidadMedidaDAO();
        UnidadMedida nuevo = new UnidadMedida(nuevaUnidad);
        unidadesDAO.insert(nuevo);
        } catch (Exception e){
            Logger.getLogger(CrudUnidadMedida.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar la unidad de medida \"".concat(nuevaUnidad).concat("\":".concat(e.toString())));
            return false;
        }
        return true;
    }

    @Override
    public boolean editarUnidadMedida(String unidadAntigua, String unidadNueva) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UnidadMedidaDAO unidadDAO = fabricaDAO.getUnidadMedidaDAO();
            UnidadMedida unidadEditar;
            unidadEditar = unidadDAO.find(unidadAntigua);
            if (unidadEditar != null){
                unidadEditar.setNombreUnidad(unidadNueva);
                unidadDAO.update(unidadEditar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudUnidadMedida.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar la unidad de medida \"".concat(unidadAntigua).concat("\":".concat(e.toString())));
            return false;
        }
    }

    @Override
    public boolean eliminarUnidadMedida(String unidadEliminar) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UnidadMedidaDAO unidadDAO = fabricaDAO.getUnidadMedidaDAO();
            UnidadMedida unidadAEliminar;
            unidadAEliminar = unidadDAO.find(unidadEliminar);
            if (unidadAEliminar != null){
                unidadDAO.delete(unidadAEliminar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudUnidadMedida.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar la unidad de medida \"".concat(unidadEliminar).concat("\":".concat(e.toString())));
            return false;
        }
    }

    @Override
    public Collection getAllUnidadesMedida() {
        Collection resultado;
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UnidadMedidaDAO unidadDAO = fabricaDAO.getUnidadMedidaDAO();
            resultado = unidadDAO.findAll();
            return resultado;
            
        } catch (Exception e) {
            Logger.getLogger(CrudUnidadMedida.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar recuperar la lista de unidades de medida: ".concat(e.toString()));
            return new LinkedList();
        }
    }

    @Override
    public UnidadMedida getUnidadByNombre(String nombreUnidadBusq) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UnidadMedidaDAO unidadDAO = fabricaDAO.getUnidadMedidaDAO();
            UnidadMedida uni;
            uni = unidadDAO.find(nombreUnidadBusq);
            if (uni != null){
                return uni;
            } else {
                return null;
            }
        } catch (Exception e){
            Logger.getLogger(CrudUnidadMedida.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar buscar la unidad de medida \"".concat(nombreUnidadBusq).concat("\":".concat(e.toString())));
            return null;
        }
    }
    
    

}
