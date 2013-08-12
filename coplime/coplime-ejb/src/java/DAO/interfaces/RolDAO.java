/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Rol;

/**
 *
 * @author victor
 */
public interface RolDAO extends genericDAO<Rol>{
    
    /**
     * Busca un rol de usuario a través de su nombre.
     * @param rolName El nombre del rol que se está buscando
     * @return Un objeto "Rol" si pudo encontrarse, null si no se encuentra
     */
    public Rol find(String rolName);
}
