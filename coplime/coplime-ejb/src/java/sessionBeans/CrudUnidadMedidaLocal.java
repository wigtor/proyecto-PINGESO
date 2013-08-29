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

    boolean agregarUnidadMedida(String nuevaUnidad);

    boolean editarUnidadMedida(String unidadAntigua, String unidadNueva);

    boolean eliminarUnidadMedida(String unidadEliminar);

    Collection getAllUnidadesMedida();

    UnidadMedida getUnidadByNombre(String nombreUnidadBusq);
    
}
