/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.RevisionPuntoLimpio;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudRevisionPuntoLimpioLocal {

    public boolean agregarRevision(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado) throws Exception;
    
    public RevisionPuntoLimpio getRevisionById(Integer numRevision);
    
    public Collection<RevisionPuntoLimpio> getAllRevisiones(String usernameQuienPregunta);

    public boolean agregarRevisionConSolicitud(Integer numPtoLimpio, String usernameLogueado, Integer numOperario, String detalle, Integer nvoEstado) throws Exception;
}
