/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.PuntoLimpio;

/**
 *
 * @author victor
 */
public interface PuntoLimpioDAO extends genericDAO<PuntoLimpio>{
    
    /**
     * Obtiene un punto limpio a través de su nombre
     * @param puntoLimpioName El nombre de usuario del punto limpio
     * @return El objeto PuntoLimpio buscado
     */
    public PuntoLimpio find(String puntoLimpioName);
}
