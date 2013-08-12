/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.administrador;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import otros.CommonFunctions;
import sessionBeans.CrudAdministradorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorAdministradorAgregarMB")
@RequestScoped
public class MantenedorAdministradorAgregarMB {

    @EJB
    private CrudAdministradorLocal crudAdministrador;
    
    private String username;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Integer rut;
    private String mail;
    private Integer telefono;   
    
    public MantenedorAdministradorAgregarMB() {
    }
    
    public void agregarAdministrador() {
        crudAdministrador.agregarAdministrador( username, rut, nombre, apellido1, apellido2, mail, telefono);
        CommonFunctions.goToPage("/faces/users/admin/verAdministradores.xhtml");
    }
    
    public void volver() {
        CommonFunctions.goToPage("/faces/users/admin/verAdministradores.xhtml");
       
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
