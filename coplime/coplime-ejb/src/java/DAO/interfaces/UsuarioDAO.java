/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Usuario;

/**
 *
 * @author victor
 */
public interface UsuarioDAO extends genericDAO<Usuario>{
    
    /**
     * Busca un usuario por su nombre de usuario
     * @param username El username del usuario que se está buscando
     * @return Un objeto "Usuario", null si no se encuentra
     */
    public Usuario find(String username);
    
    /**
     * Busca un usuario por su rut.
     * @param rut El rut del usuario que se está buscando
     * @return Un objeto "Usuario", null si no se encuentra
     */
    public Usuario findByRut(int rut);
    
    /**
     * Borra un usuario de la fuente de datos que tenga cierto rut.
     * @param rut El rut del usuario que se desea eliminar
     * @return true si el usuario fue borrado correctamente, false si hubo un error
     */
    public boolean deleteByRut(int rut);
}
