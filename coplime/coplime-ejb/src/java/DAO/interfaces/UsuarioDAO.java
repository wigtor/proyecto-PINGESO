/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Usuario;
import java.util.Collection;

/**
 *
 * @author victor
 */
public interface UsuarioDAO extends genericDAO<Usuario>{
    // Interface that all AdministradorDAOs must support

    public Usuario find(String username, String password);
    public Usuario find(String username);
    public Usuario findByRut(int rut);
}
