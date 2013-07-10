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

    boolean cambiarDatosContacto(int telefono, String email);
    public String getNombres();
    
    public String getApellidos();
    
    public String getRol();
    
    public String getIdUsuario();
    
    public String getEmail();

    public int getTelefono();
    
    public boolean setUsuarioLogueado(String username);

    public int getRut();

}
