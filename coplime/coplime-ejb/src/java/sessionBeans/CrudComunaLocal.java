/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Comuna;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Armando
 */
@Local
public interface CrudComunaLocal {

    boolean agregarComuna(String nombreComuna);

    boolean editarComuna(String nombreAntiguoComuna, String nombreNuevoComuna);

    boolean eliminarComuna(String nombreComunaEliminar);

    Collection getAllComunas();

    Comuna getComunaByName(String nombreComunaBusq);
    
}
