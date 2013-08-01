/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.administrador;

import entities.Administrador;
import entities.Inspector;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import managedBeans.mantenedores.inspector.MantenedorInspector;
import otros.CommonFunctions;
import sessionBeans.CrudAdministradorLocal;
import sessionBeans.CrudInspectorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "MantenedorAdministradorVerDetalles")
@RequestScoped
public class MantenedorAdministradorVerDetalles {

    @Inject 
    private MantenedorAdministrador mantAdm;
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private Integer rut;
    private String mail;
    private Integer telefono;
    
    public MantenedorAdministradorVerDetalles() {
    }
    
    
    @PostConstruct
    public void init() { 
        System.out.println("inspector en vista ver"+this.mantAdm.getApellido1());
       
        this.rut = this.mantAdm.getRut();
        this.nombre = this.mantAdm.getNombre();
        this.apellido1 = this.mantAdm.getApellido1();
        this.apellido2 = this.mantAdm.getApellido2();
        this.mail = this.mantAdm.getMail();
        this.username = this.mantAdm.getUsername();
        this.telefono = this.mantAdm.getTelefono();
               
    }
    
    public void volver() {
        CommonFunctions.goToPage("/faces/admin/verAdministradores.xhtml");
       
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
