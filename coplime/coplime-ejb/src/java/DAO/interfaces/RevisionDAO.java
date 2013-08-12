/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.RevisionPuntoLimpio;
import java.util.Collection;

/**
 *
 * @author victor
 */
public interface RevisionDAO extends genericDAO<RevisionPuntoLimpio> {
    
    /**
     * Busca todas las revisiones de punto limpio realizadas por cierto inspector
     * @param idUsuario
     * @return 
     */
    public Collection<RevisionPuntoLimpio> findAllFromInspector(Integer idUsuario);
}
