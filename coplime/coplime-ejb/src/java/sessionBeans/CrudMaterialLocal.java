/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Material;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Armando
 */
@Local
public interface CrudMaterialLocal {

    boolean agregarMaterial(String nombreMaterial) throws Exception;

    boolean editarMaterial(Integer idMaterial, String nombreMaterialOriginal, String nombreMaterialNuevo) throws Exception;

    boolean eliminarMaterial(Integer idMaterial, String nombreMaterial)throws Exception;

    Collection getAllMateriales();

    Material getMaterialByName(String nombreMaterial);
    
    Material getMaterialByID(Integer idMaterial);
    
}
