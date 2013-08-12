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

    /**
     * Registra un nuevo administrador en el sistema, no debe repetirse el username ni rut.
     * @param username El nombre de usuario del nuevo administrador
     * @param rut El rut del nuevo aministrador
     * @param nombre El primer nombre del nuevo administrador
     * @param apellido1 El primer apellido del nuevo administrador
     * @param apellido2 El segundo apellido del nuevo administrador
     * @param mail El correo electrónico del nuevo administrador
     * @param telefono El teléfono de contacto del nuevo administrador
     */
    void agregarAdministrador(String username, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono);

    public boolean eliminarAdministrador(Integer rutUser);
    
    public Collection<Administrador> getAllAdministradores();
    
    public Usuario getAdministradorByRut(Integer rutUser);
    
    public void editarAdministrador(Integer rutUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono); 

    /**
     * Convierte un String a una cadena String de  largo 32 que corresponde al hash MD% ela entrada
     * @param password
     * @return 
     */
    public String convertToMD5(String password);

}
