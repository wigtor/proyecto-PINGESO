/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Administrador;
import entities.Usuario;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudAdministradorLocal {

    void agregarAdministrador(String username, String password, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono);

    public boolean eliminarAdministrador(Integer rutUser);
    
    public Collection<Administrador> getAllAdministradores();
    
    public Usuario getAdministradorByRut(Integer rutUser);
    
    public void editarAdministrador(Integer rutUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono); 

}
