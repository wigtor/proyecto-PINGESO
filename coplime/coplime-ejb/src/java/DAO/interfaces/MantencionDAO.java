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
    // Interface that all AdministradorDAOs must support
    public Collection<MantencionPuntoLimpio> findAllFromOperario(Integer idUsuario);
}
