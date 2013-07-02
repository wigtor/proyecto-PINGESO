/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Usuario;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface UserServiceLocal {

    Usuario buscarUsuario(String username, String password);
    
}
