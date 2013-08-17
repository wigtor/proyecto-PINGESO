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
    
    /**
     * Comprueba si existe un usuario con cierto rut ya registrado en el sistema.
     * @param rut El rut que se desea verificar si existe
     * @return true si el rut existe, false si no
     */
    public boolean rutExist(Integer rut);
    
    /**
     * Comprueba si existe un usuario con cierto username ya registrado en el sistema.
     * @param username El username que se desea verificar si existe
     * @return true si el username existe, false si no
     */
    public boolean usernameExist(String username);
}
