/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.SolicitudMantencion;
import java.util.Collection;

/**
 *
 * @author victor
 */
public interface SolicitudMantencionDAO extends genericDAO<SolicitudMantencion>{
    // Interface that all AdministradorDAOs must support

    public Collection<SolicitudMantencion> findAllFromInspector(Integer idUsuario);
    public Collection<SolicitudMantencion> findAllFromOperario(Integer idUsuario);
}
