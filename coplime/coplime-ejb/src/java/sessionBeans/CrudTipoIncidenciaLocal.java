/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.TipoIncidencia;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Armando
 */
@Local
public interface CrudTipoIncidenciaLocal {

    boolean agregarTipoIncidencia(String tipoIncidencia, boolean visibleAlUsuario);

    boolean editarTipoIncidencia(String tipoIncidenciaOrig, String tipoIncidenciaNuevo, boolean visibleAlUsuarioOrig, boolean visibleAlUsuarioNuevo);

    boolean eliminarTipoIncidencia(String tipoIncidencia);

    TipoIncidencia getTipoIncidenciaByName(String tipoIncidenciaBuscada);

    Collection getAllTiposIncidencia();
    
}
