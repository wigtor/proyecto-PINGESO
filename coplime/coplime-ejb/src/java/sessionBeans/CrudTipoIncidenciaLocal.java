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

    boolean agregarTipoIncidencia(String tipoIncidencia, boolean visibleAlUsuario) throws Exception;

    boolean editarTipoIncidencia(Integer idIncidencia, String tipoIncidenciaOrig, String tipoIncidenciaNuevo, boolean visibleAlUsuarioOrig, boolean visibleAlUsuarioNuevo) throws Exception;

    boolean eliminarTipoIncidencia(Integer idIncidencia, String tipoIncidencia) throws Exception;

    TipoIncidencia getTipoIncidenciaByName(String tipoIncidenciaBuscada);

    Collection getAllTiposIncidencia();
    
    TipoIncidencia getTipoIncidenciaByID(Integer idIncidencia);
    
}
