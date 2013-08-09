/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.administrador;

import entities.Usuario;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudAdministradorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorAdministradorVerDetallesMB")
@RequestScoped
public class MantenedorAdministradorVerDetallesMB {

    @EJB
    private CrudAdministradorLocal crudAdministrador;
    
    @Inject 
    private MantenedorAdministradorConversation mantAdm;
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private Integer rut;
    private String mail;
    private Integer telefono;
    
    
    @PostConstruct
    public void init() { 
        if (mantAdm.getIdUsuarioDetalles()!= null) {
            Integer numAdmin = mantAdm.getIdUsuarioDetalles();
            cargarDatosAdministrador(numAdmin);
        }
        else {
            //MOSTRAR ERROR
            
            volverToLista();
        }
    }
    
    private void cargarDatosAdministrador(Integer numAdmin) {
        Usuario adminSelect = crudAdministrador.getAdministradorByRut(numAdmin);
        if (adminSelect == null) {
            volverToLista();
            return;
        }
        this.rut = adminSelect.getRut();
        this.nombre = adminSelect.getNombre();
        this.apellido1 = adminSelect.getApellido1();
        this.apellido2 = adminSelect.getApellido2();
        this.mail = adminSelect.getEmail();
        this.username = adminSelect.getUsername();
        this.telefono = adminSelect.getTelefono();
    }
    
    public void volverToLista() {
        CommonFunctions.goToPage("/faces/users/admin/verAdministradores.xhtml");
    }
    
    
    public MantenedorAdministradorVerDetallesMB() {
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
