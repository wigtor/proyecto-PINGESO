/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.MantencionPuntoLimpio;
import java.util.Collection;

/**
 *
 * @author victor
 */
public interface MantencionDAO extends genericDAO<MantencionPuntoLimpio>{
    
    /**
     * Busca todas las mantenciones que ha realizado un operario.
     * @param idUsuario El identificador primario del usuario de ese operario
     * @return Devuelve una colección de objetos MantencionPuntoLimpio que ha realizado el operario
     */
    public Collection<MantencionPuntoLimpio> findAllFromOperario(Integer idUsuario);
}
