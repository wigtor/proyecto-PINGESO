/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Estado;

/**
 *
 * @author victor
 */
public interface EstadoDAO extends genericDAO<Estado>{
    
    /**
     * Busca un estado a través de su nombre en lugar de su id.
     * @param estadoName El nombre del estado que se está buscando
     * @return Un objeto "Estado" si pudo encontrarse, null si no se encuentra
     */
    public Estado find(String estadoName);
}
