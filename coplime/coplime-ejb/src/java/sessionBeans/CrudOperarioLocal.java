/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.OperarioMantencion;
import entities.Usuario;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudOperarioLocal {

    /**
     * Registra un nuevo usuario operario de mantención en el sistema, no debe repetirse el username ni rut.
     * @param username El nombre de usuario del nuevo operario de mantención
     * @param rut El rut del nuevo operario de mantención
     * @param nombre El primer nombre del nuevo operario de mantención
     * @param apellido1 El primer apellido del nuevo operario de mantención
     * @param apellido2 El segundo apellido del nuevo operario de mantención
     * @param mail El correo electrónico del nuevo operario de mantención
     * @param telefono El teléfono de contacto del nuevo operario de mantención
     */
    public void agregarOperario(String username, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono);

    /**
     * Obtiene la lista de todos los operarios de mantención registrados en el sistema.
     * @return Una colección de objetos "OperarioMantencion", puede ser vacía, pero nunca null
     */
    public Collection<OperarioMantencion> getAllOperarios();
    
    /**
     * Busca un operario en la fuente de datos utilizando su rut.
     * @param rutUser El rut del operario que se está buscando
     * @return Un Objeto "OperarioMantencion", null si no se encontró
     */
    public OperarioMantencion getOperarioByRut(Integer rutUser);
    
    /**
     * Cambia los datos de un operario en el sistema, no debe repetirse el username ni rut.
     * @param username El nombre de usuario del operario
     * @param rutUser El rut del operario, este no debe cambiar
     * @param nombre El primer nombre del operario
     * @param apellido1 El primer apellido del operario
     * @param apellido2 El segundo apellido del operario
     * @param mail El correo electrónico del operario
     * @param resetContraseña Indica si debe volverse a poner la contraseña por defecto o no
     * @param telefono El teléfono de contacto del operario
     */
    public void editarOperario(Integer rutUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono);

    /**
     * Elimina un operario y su usuario relacionado de la fuente de datos según el rut ingresado.
     * @param rutUser El rut del usuario operario que se desea eliminar
     * @return true si el usuario operario pudo ser borrado, false si hubo un error
     */
    public boolean eliminarOperario(Integer rutUser);
    
}
