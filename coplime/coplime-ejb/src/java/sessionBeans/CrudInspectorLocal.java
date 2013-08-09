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

    public void agregarInspector(String username, String password, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono);

    public Collection<Inspector> getAllInspectores();
    
    public Usuario getInspector(String userName);
    
    public Usuario getUsuarioByRut(Integer rutUSer);
    
    public Inspector getInspectorByRut(Integer rutUSer);
    
    public void editarInspector(Integer idUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono);
    
    public boolean eliminarInspector(Integer idUser);
}
