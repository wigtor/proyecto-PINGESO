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
public interface RevisionDAO extends genericDAO<RevisionPuntoLimpio>{
    // Interface that all AdministradorDAOs must support

    public Collection<RevisionPuntoLimpio> findAllFromInspector(Integer idUsuario);
}
