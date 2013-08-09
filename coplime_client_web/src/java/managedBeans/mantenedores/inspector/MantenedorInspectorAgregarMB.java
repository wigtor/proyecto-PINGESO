/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudInspectorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "MantenedorInspectorAgregar")
@RequestScoped
public class MantenedorInspectorAgregar {

    @EJB
    private CrudInspectorLocal crudInspector;
    @Inject 
    private MantenedorInspectorEditar mantInsp;
           
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private String password;
    private Integer rut;
    private String mail;
    private Integer telefono;
    
    public MantenedorInspectorAgregar() {
    }
    
    public void agregarInspector() {
        crudInspector.agregarInspector(username, password, rut, nombre, apellido1, apellido2, mail, telefono);
        CommonFunctions.goToPage("/faces/users/verInspectores.xhtml");       
        
    }
    
    public void volver() {
        
        CommonFunctions.goToPage("/faces/users/verInspectores.xhtml");
       
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
