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

    boolean agregarComuna(String nombreComuna) throws Exception;

    boolean editarComuna(Integer idComuna, String nombreAntiguoComuna, String nombreNuevoComuna) throws Exception;

    boolean eliminarComuna(Integer idComuna, String nombreComunaEliminar) throws Exception;

    Collection getAllComunas();

    Comuna getComunaByName(String nombreComunaBusq);
    
    Comuna getComunaByID(Integer idComuna);
    
}
