/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudMantencionPuntoLimpioLocal {

    public boolean agregarMantencion(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado);
    
}
