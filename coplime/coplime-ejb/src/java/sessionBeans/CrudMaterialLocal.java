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

    boolean agregarMaterial(String nombreMaterial);

    boolean editarMaterial(String nombreMaterialOriginal, String nombreMaterialNuevo);

    boolean eliminarMaterial(String nombreMaterial);

    Collection getAllMateriales();

    Material getMaterialByName(String nombreMaterial);
    
}
