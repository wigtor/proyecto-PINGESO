/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.CrudOperarioLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorOperarioAgregarMB")
@RequestScoped
public class MantenedorOperarioAgregarMB {

    @EJB
    private CrudOperarioLocal crudOperario;
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private Integer rut;
    private String mail;
    private Integer telefono;
    
    public MantenedorOperarioAgregarMB() {
    }
    
    public void agregarOperario() {
        try {
            crudOperario.agregarOperario( username, rut, nombre, apellido1, apellido2, mail, telefono);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha creado un nuevo operario de mantención",
                    "Se ha creado el operario de mantención \"".concat(username).concat("\""));
            CommonFunctions.goToPage("/faces/users/verOperariosMantencion.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            CommonFunctions.goToPage("/faces/users/admin/agregarOperarioMantencion.xhtml?faces-redirect=true");
        }
        
    }
    
    public void volver() {
        CommonFunctions.goToPage("/faces/users/verOperariosMantencion.xhtml");
       
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
    
    
}
