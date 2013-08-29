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

    boolean agregarEstadoPuntoLimpio(String nuevoEstadoPL);

    boolean editarEstadoPuntoLimpio(String antiguoEstadoPL, String nuevoEstadoPL);

    boolean eliminarEstadoPuntoLimpio(String eliminarEstadoPL);

    Estado getEstadoPuntoLimpio(String nombreEstadoPLBusq);

    Collection getAllEstadosPuntoLimpio();
    
}
