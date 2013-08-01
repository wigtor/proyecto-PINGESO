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

    public boolean agregarRevision(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado);
    public RevisionPuntoLimpio getRevisionById(Integer numRevision);
    public Collection<RevisionPuntoLimpio> getAllRevisiones(String usernameQuienPregunta);
}
