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
public interface CrudUsuariosComunLocal {

    /**
     * Busca un usuario registrado en el sistema usando su nombre de usuario.
     * @param userName El nombre de usuario de quien se busca
     * @return Un objeto "Usuario", null si no se encontró
     */
    public Usuario getUsuarioByUsername(String userName);
    
    /**
     * Busca un usuario registrado en el sistema usando su rut.
     * @param rutUser El rut del usuario buscado
     * @return Un objeto "Usuario", null si no se encontró
     */
    public Usuario getUsuarioByRut(Integer rutUser);
    
    /**
     * Convierte un String a una cadena String de  largo 32 que corresponde al hash MD% ela entrada
     * @param password
     * @return 
     */
    public String convertToMD5(String password);
    
    /**
     * Indica si ya existe un usuario con cierto rut registrado en el sistema.
     * @param rut El rut del usuario que se desea comprobar
     * @return Devuelve true si el usuario ya existe, falso en caso contrario
     */
    public boolean existeRut(Integer rut);

    /**
     * Indica si ya existe un usuario con cierto username registrado en el sistema.
     * @param username El username del usuario que se desea comprobar
     * @return Devuelve true si el usuario ya existe, falso en caso contrario
     */
    public boolean existeUsername(String username);
}
