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

    public void agregarOperario(String username, String password, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono);

    public Collection<OperarioMantencion> getAllOperarios();
    
    public Usuario getOperario(String userName);
    
    public OperarioMantencion getOperarioByRut(Integer rutUser);
    
    public Usuario getUsuarioByRut(Integer rutUser);
    
    public void editarOperario(Integer rutUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono);

    public boolean eliminarOperario(Integer rutUser);
    
}
