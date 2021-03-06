/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.CrudInspectorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorInspectorAgregarMB")
@RequestScoped
public class MantenedorInspectorAgregarMB {

    @EJB
    private CrudInspectorLocal crudInspector;
           
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private Integer rut;
    private String mail;
    private Integer telefono;
    
    public MantenedorInspectorAgregarMB() {
    }
    
    public void agregarInspector() {
        try {
            crudInspector.agregarInspector(username, rut, nombre, apellido1, apellido2, mail, telefono);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha creado un nuevo inspector",
                    "Se ha creado el inspector \"".concat(username).concat("\""));
            volver();
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            CommonFunctions.goToPage("/faces/users/admin/agregarInspector.xhtml?faces-redirect=true");
        }
    }
    
    public void volver() {
        CommonFunctions.goToPage("/faces/users/verInspectores.xhtml?faces-redirect=true");
       
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
