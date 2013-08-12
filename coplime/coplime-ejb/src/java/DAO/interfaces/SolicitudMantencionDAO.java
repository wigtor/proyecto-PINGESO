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
    
    /**
     * Busca todas las solicitudes de mantención realizadas por cierto inspector.
     * @param idUsuario El id del usuario del inspector al que se quiere buscar sus solicitudes
     * @return Una colección de objetos "SolicitudMantencion", puede ser vacia, pero nunca null
     */
    public Collection<SolicitudMantencion> findAllFromInspector(Integer idUsuario);
    
    /**
     * Busca todas las solicitudes de mantención enviaas a cierto operario de mantención.
     * @param idUsuario El id del usuario del operario al que se quiere buscar sus solicitudes
     * @return Una colección de objetos "SolicitudMantencion", puede ser vacia, pero nunca null
     */
    public Collection<SolicitudMantencion> findAllFromOperario(Integer idUsuario);
}
