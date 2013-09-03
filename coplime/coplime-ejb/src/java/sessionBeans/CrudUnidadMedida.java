/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.UnidadMedidaDAO;
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
    public boolean agregarUnidadMedida(String nuevaUnidad) throws Exception {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UnidadMedidaDAO unidadesDAO = fabricaDAO.getUnidadMedidaDAO();
            if(unidadesDAO.find(nuevaUnidad)!=null){
                throw new Exception("La unidad de medida ya existe.");
            } else {
                UnidadMedida nuevo;
                nuevo = new UnidadMedida();
                nuevo.setNombreUnidad(nuevaUnidad);
                unidadesDAO.insert(nuevo);    
            }
        } catch (Exception e){
            Logger.getLogger(CrudUnidadMedida.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar la unidad de medida \"".concat(nuevaUnidad).concat("\":".concat(e.toString())));
            throw new Exception("Error al intentar agregar la nueva unidad de medida.");
        }
        return true;
    }

    @Override
    public boolean editarUnidadMedida(Integer idUnidadMedida, String unidadAntigua, String unidadNueva) throws Exception {
        try{
            //failsafe
            if(unidadAntigua.equals(unidadNueva)){
                throw new Exception("Los nombres de unidad de medida son iguales.");
            }
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UnidadMedidaDAO unidadDAO = fabricaDAO.getUnidadMedidaDAO();
            UnidadMedida unidadEditar;
            unidadEditar = unidadDAO.find(idUnidadMedida.intValue());
            if (unidadEditar == null){
                throw new Exception("No se puede editar la unidad de medida, el ID ".concat(idUnidadMedida.toString()).concat(" no existe."));
            } else {
                if(!(unidadEditar.getNombreUnidad().equals(unidadAntigua))){
                    throw new Exception("No se puede editar la unidad de medida, el nombre asociado al ID es diferente.");
                } else {
                    unidadEditar.setNombreUnidad(unidadNueva);
                    unidadDAO.update(unidadEditar);
                    return true;
                }
            }
        } catch (Exception e){
            Logger.getLogger(CrudUnidadMedida.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar la unidad de medida \"".concat(unidadAntigua).concat("\":".concat(e.toString())));
            throw new Exception("Error al intentar editar la unidad de medida.");
        }
    }

    @Override
    public boolean eliminarUnidadMedida(Integer idUnidadMedida, String unidadEliminar) throws Exception {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UnidadMedidaDAO unidadDAO = fabricaDAO.getUnidadMedidaDAO();
            UnidadMedida unidadAEliminar;
            unidadAEliminar = unidadDAO.find(idUnidadMedida.intValue());
            if (unidadAEliminar == null){
                throw new Exception("No se puede eliminar la unidad de medida, el ID ".concat(idUnidadMedida.toString()).concat(" no existe."));
            } else {
                if(!(unidadAEliminar.getNombreUnidad().equals(unidadAEliminar))){
                    throw new Exception("No se puede eliminar la unidad de medida, el nombre asociado al ID es diferente.");
                } else {
                    unidadDAO.delete(unidadAEliminar);
                    return true;
                }    
            }
        } catch (Exception e){
            Logger.getLogger(CrudUnidadMedida.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar la unidad de medida \"".concat(unidadEliminar).concat("\":".concat(e.toString())));
            throw new Exception("Error al intentar eliminar la unidad de medida.");
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
