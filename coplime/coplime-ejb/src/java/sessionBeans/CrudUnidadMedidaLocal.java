/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.UnidadMedida;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Armando
 */
@Local
public interface CrudUnidadMedidaLocal {

    boolean agregarUnidadMedida(String nuevaUnidad) throws Exception;

    boolean editarUnidadMedida(Integer idUnidadMedida, String unidadAntigua, String unidadNueva) throws Exception;

    boolean eliminarUnidadMedida(Integer idUnidadMedida, String unidadEliminar) throws Exception;

    Collection getAllUnidadesMedida();

    UnidadMedida getUnidadByNombre(String nombreUnidadBusq);
    
    UnidadMedida getUnidadByID(Integer idUnidadMedida);
}
