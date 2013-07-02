/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import sessionBeans.CrudAdministradorLocal;

/**
 *
 * @author victor
 */
@Named(value = "creacionAdministrador")
@RequestScoped
public class creacionAdministrador {
    @EJB
    private CrudAdministradorLocal crudAdministrador;
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    String nombre;
    String apellido1;
    String apellido2;
    String mail;
    
    /**
     * Creates a new instance of creacionAdministrador
     */
    public creacionAdministrador() {
    }
    
    
    public void nuevoAdministrador() {
        
        
        
        try {
            crudAdministrador.agregarAdministrador( username, password, nombre, apellido1, apellido2, mail);
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
        catch (Exception e) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("enviarAvisoIncidencia.xhtml");
            }
            catch (Exception e2) {
                
            }
        }
    }
}
