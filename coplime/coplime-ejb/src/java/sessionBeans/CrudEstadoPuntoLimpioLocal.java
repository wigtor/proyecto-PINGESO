/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Estado;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Armando
 */
@Local
public interface CrudEstadoPuntoLimpioLocal {

    boolean agregarEstadoPuntoLimpio(String nuevoEstadoPL) throws Exception;

    boolean editarEstadoPuntoLimpio(Integer idEstado, String antiguoEstadoPL, String nuevoEstadoPL) throws Exception;

    boolean eliminarEstadoPuntoLimpio(Integer idEstado, String eliminarEstadoPL) throws Exception;

    Estado getEstadoPuntoLimpioPorNombre(String nombreEstadoPLBusq);
    
    

    Collection getAllEstadosPuntoLimpio();
    
}
