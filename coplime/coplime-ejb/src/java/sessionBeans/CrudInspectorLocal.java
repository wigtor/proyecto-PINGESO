/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Inspector;
import entities.Usuario;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudInspectorLocal {

    /**
     * Registra un nuevo usuario inspector en el sistema, no debe repetirse el username ni rut.
     * @param username El nombre de usuario del nuevo inspector
     * @param rut El rut del nuevo inspector
     * @param nombre El primer nombre del nuevo inspector
     * @param apellido1 El primer apellido del nuevo inspector
     * @param apellido2 El segundo apellido del nuevo inspector
     * @param mail El correo electrónico del nuevo inspector
     * @param telefono El teléfono de contacto del nuevo inspector
     */
    public void agregarInspector(String username, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono);

    /**
     * Obtiene la lista de todos los inspectores registrados en el sistema.
     * @return Una colección de objetos "Inspector", puede ser vacía, pero nunca null
     */
    public Collection<Inspector> getAllInspectores();
    
    /**
     * Busca un inspector en la fuente de datos utilizando su rut.
     * @param rutUser El rut del inspectores que se está buscando
     * @return Un Objeto "Inspector", null si no se encontró
     */
    public Usuario getInspector(String userName);
    
    /**
     * Busca un inspector en la fuente de datos según su rut.
     * @param rutUSer El rut del inspector buscado
     * @return Un objeto "Inspector" que se estaba buscando, null si no se encuentra
     */
    public Inspector getInspectorByRut(Integer rutUSer);
    
    /**
     * Cambia los datos de un inspector en el sistema, no debe repetirse el username ni rut.
     * @param username El nombre de usuario del inspector
     * @param rutUser El rut del inspector, este no debe cambiar
     * @param nombre El primer nombre del inspector
     * @param apellido1 El primer apellido del inspector
     * @param apellido2 El segundo apellido del inspector
     * @param mail El correo electrónico del inspector
     * @param resetContraseña Indica si debe volverse a poner la contraseña por defecto o no
     * @param telefono El teléfono de contacto del inspector
     */
    public void editarInspector(Integer rutUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono);
    
    /**
     * Elimina un inspector de la fuente de datos
     * @param rutUser El rut del inspector que se va a eliminar
     * @return true si pudo eliminarse correctamente, false si hubo un error
     */
    public boolean eliminarInspector(Integer rutUser);
}
