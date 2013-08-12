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
     * Registra un nuevo usuario administrador en el sistema, no debe repetirse el username ni rut.
     * @param username El nombre de usuario del nuevo administrador
     * @param rut El rut del nuevo aministrador
     * @param nombre El primer nombre del nuevo administrador
     * @param apellido1 El primer apellido del nuevo administrador
     * @param apellido2 El segundo apellido del nuevo administrador
     * @param mail El correo electrónico del nuevo administrador
     * @param telefono El teléfono de contacto del nuevo administrador
     */
    void agregarAdministrador(String username, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono);

    /**
     * Elimina un administrador y su usuario relacionado de la fuente de datos según el rut ingresado.
     * @param rutUser El rut del usuario administrador que se desea eliminar
     * @return true si el usuario administrador pudo ser borrado, false si hubo un error
     */
    public boolean eliminarAdministrador(Integer rutUser);
    
    /**
     * Obtiene la lista de todos los administradores registrados en el sistema.
     * @return Una colección de objetos "Administrador", puede ser vacía, pero nunca null
     */
    public Collection<Administrador> getAllAdministradores();
    
    /**
     * Busca un administrador en la fuente de datos utilizando su rut.
     * @param rutUser El rut del administrador que se está buscando
     * @return Un Objeto "Administrador", null si no se encontró
     */
    public Usuario getAdministradorByRut(Integer rutUser);
    
    /**
     * Cambia los datos de un administrador en el sistema, no debe repetirse el username ni rut.
     * @param username El nombre de usuario del administrador
     * @param rutUser El rut del aministrador, este no debe cambiar
     * @param nombre El primer nombre del administrador
     * @param apellido1 El primer apellido del administrador
     * @param apellido2 El segundo apellido del administrador
     * @param mail El correo electrónico del administrador
     * @param resetContraseña Indica si debe volverse a poner la contraseña por defecto o no
     * @param telefono El teléfono de contacto del administrador
     */
    public void editarAdministrador(Integer rutUser, String userName, String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono); 

}
