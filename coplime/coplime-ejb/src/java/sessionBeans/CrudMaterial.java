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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean agregarMaterial(String nombreMaterial) {
        try{
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
        Material nuevo = new Material(nombreMaterial);
        matDAO.insert(nuevo);
        } catch (Exception e){
            Logger.getLogger(CrudMaterial.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar el material ".concat(nombreMaterial).concat(":".concat(e.toString())));
            return false;
        }
        return true;
        
    }

    @Override
    public boolean editarMaterial(String nombreMaterialOriginal, String nombreMaterialNuevo) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
            //Se busca el material a editar
            Material matEditar;
            matEditar = matDAO.find(nombreMaterialOriginal);
            //TODO failsafe si no encuentra materiales por ese nombre
            if (matEditar != null){
                matEditar.setNombre_material(nombreMaterialNuevo);
                matDAO.update(matEditar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudMaterial.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar el material ".concat(nombreMaterialOriginal).concat(":".concat(e.toString())));
            return false;
        }
        
    }

    @Override
    public boolean eliminarMaterial(String nombreMaterial) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            MaterialDAO matDAO = fabricaDAO.getMaterialDAO();
            //Se busca el material a editar
            Material matEditar;
            matEditar = matDAO.find(nombreMaterial);
            //TODO failsafe si no encuentra materiales por ese nombre
            if (matEditar != null){
                matDAO.delete(matEditar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudMaterial.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar el material ".concat(nombreMaterial).concat(":".concat(e.toString())));
            return false;
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
            //Se busca el material a editar
            Material mat;
            mat = matDAO.find(nombreMaterial);
            //TODO failsafe si no encuentra materiales por ese nombre
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
    
    
    
    
    
    

    
}
