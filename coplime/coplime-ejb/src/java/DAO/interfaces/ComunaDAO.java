/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Comuna;

/**
 *
 * @author victor
 */
public interface ComunaDAO extends genericDAO<Comuna>{
    
    /**
     * Busca una comuna a través de su nombre en lugar de su id.
     * @param nombreComuna El nombre de la comuna que se está buscando
     * @return Un objeto "Comuna" si pudo encontrarse, null si no se encuentra
     */
    public Comuna find(String nombreComuna);
}
