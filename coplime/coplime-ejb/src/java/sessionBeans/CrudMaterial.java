/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.MaterialDAO;
import java.util.logging.Logger;
import entities.Material;
import java.util.Collection;
import java.util.LinkedList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;

/**
 *
 * @author Armando
 */
@Stateless
public class CrudMaterial implements CrudMaterialLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    @Override
    public boolean agregarMaterial(String nombreMaterial) throws Exception{
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
            if(matDAO.find(nombreMaterial)!=null){
                throw new Exception("El material ya existe.");
            }else{
                Material nuevo; 
                nuevo = new Material();
                nuevo.setNombre_material(nombreMaterial);
                matDAO.insert(nuevo);
            }
        
        } catch (Exception e){
            Logger.getLogger(CrudMaterial.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar el material ".concat(nombreMaterial).concat(":".concat(e.toString())));
            throw new Exception("Error al intentar agregar el nuevo material.");
        }
        return true;
        
    }

    @Override
    public boolean editarMaterial(Integer idMaterial, String nombreMaterialOriginal, String nombreMaterialNuevo) throws Exception {
        try{
            //failsafe
            if(nombreMaterialNuevo.equals(nombreMaterialOriginal)){
                throw new Exception("Los nombres de material son iguales.");
            }
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
            Material matEditar;
            matEditar = matDAO.find(idMaterial.intValue());
            if(matEditar==null){
                throw new Exception("No se puede editar el material, el ID ".concat(idMaterial.toString()).concat(" no existe."));
            } else {
                if(!(matEditar.getNombre_material().equals(nombreMaterialOriginal))){
                    throw new Exception("No se puede editar el material, el nombre asociado al ID es diferente.");
                } else {
                    matEditar.setNombre_material(nombreMaterialNuevo);
                    matDAO.update(matEditar);
                    return true;
                }
            }
        } catch (Exception e){
            Logger.getLogger(CrudMaterial.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar el material ".concat(nombreMaterialOriginal).concat(":".concat(e.toString())));
            throw new Exception("Error al intentar editar el material.");
        }
        
    }

    @Override
    public boolean eliminarMaterial(Integer idMaterial) throws Exception{
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
            Material matEliminar;
            matEliminar = matDAO.find(idMaterial.intValue());
            if (matEliminar == null){
                throw new Exception("No se puede eliminar el material, el identificador ".concat(idMaterial.toString()).concat(" no existe."));
            } else {
                matDAO.delete(matEliminar);
                return true;
            }
        } catch (Exception e){
            Logger.getLogger(CrudMaterial.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar el material".concat(":".concat(e.toString())));
            throw new Exception("Error al intentar eliminar el material.");
        }
    }

    @Override
    public Collection getAllMateriales() {
        Collection resultado;
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
            resultado = matDAO.findAll();
            return resultado;
            
        } catch (Exception e) {
            Logger.getLogger(CrudMaterial.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar recuperar la lista de materiales: ".concat(e.toString()));
            return new LinkedList();
        }
    }

    @Override
    public Material getMaterialByName(String nombreMaterial) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
            Material mat;
            mat = matDAO.find(nombreMaterial);
            if (mat != null){
                return mat;
            } else {
                return null;
            }
        } catch (Exception e){
            Logger.getLogger(CrudMaterial.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar buscar el material ".concat(nombreMaterial).concat(":".concat(e.toString())));
            return null;
        }
    }
    
    @Override
    public Material getMaterialByID(Integer idMaterial){
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
            Material mat;
            mat = matDAO.find(idMaterial);
            if (mat != null){
                return mat;
            } else {
                return null;
            }
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar buscar el material con ID ".concat(idMaterial.toString()).concat(" :").concat(e.toString()));
            return null;
        }
    }
    
    
    
    
    
    

    
}
