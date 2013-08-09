/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.administrador;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "MantenedorAdministrador")
@SessionScoped
public class MantenedorAdministrador implements Serializable{

    private String username;
    private String password;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Integer rut;
    private String mail;
    private Integer telefono;
    
    public MantenedorAdministrador() {
    }

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
