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
 * @author victor
 */
@Local
public interface AvisosIncidenciaLocal {

    Collection<TipoIncidencia> getTiposAvisos();
    
}
