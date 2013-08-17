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
    
    /**
     * Comprueba si existe un punto limpio con cierto número ya registrado en el sistema.
     * @param rut El número que se desea verificar si existe
     * @return true si el rut número, false si no
     */
    public boolean numExist(Integer num);
    
    /**
     * Comprueba si existe un punto limpio con cierto nombre ya registrado en el sistema.
     * @param nombre El nombre que se desea verificar si existe
     * @return true si el nombre existe, false si no
     */
    public boolean nombreExist(String nombre);
}
