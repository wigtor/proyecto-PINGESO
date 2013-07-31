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

    public Usuario buscarUsuario(String username);
    public void cambiarDatosContacto(String username, int telefono, String email) throws Exception;
    public void cambiarPass(String username, String passActual, String nvaPass) throws Exception;

}
