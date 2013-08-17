/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import entities.Inspector;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudInspectorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorInspectorEditarMB")
@RequestScoped
public class MantenedorInspectorEditarMB {

    @EJB
    private CrudInspectorLocal crudInspector;
           
    @Inject 
    private MantenedorInspectorConversation mantInsp;
    
    private Integer numeroInspector;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private String password;
    private Integer rut;
    private String mail;
    private Integer telefono;
    private boolean checkContraseña;
    
    @PostConstruct
    public void init() { 
        if (mantInsp.getIdUsuarioDetalles()!= null) {
            Integer numInsp = mantInsp.getIdUsuarioDetalles();
            cargarDatosInspector(numInsp);
        }
        else {
            //MOSTRAR ERROR
            volverToLista();
        }
    }
    
    private void cargarDatosInspector(Integer numInsp) {
        Inspector inspSelect = crudInspector.getInspectorByRut(numInsp);
        if (inspSelect == null) {
            volverToLista();
            return;
        }
        this.numeroInspector = inspSelect.getId();
        this.rut = inspSelect.getUsuario().getRut();
        this.nombre = inspSelect.getUsuario().getNombre();
        this.apellido1 = inspSelect.getUsuario().getApellido1();
        this.apellido2 = inspSelect.getUsuario().getApellido2();
        this.mail = inspSelect.getUsuario().getEmail();
        this.username = inspSelect.getUsuario().getUsername();
        this.telefono = inspSelect.getUsuario().getTelefono();
    }
    
    
    public void guardarCambiosInspector(){
        try {
            crudInspector.editarInspector( rut, username, nombre, apellido1, apellido2, mail, checkContraseña, telefono);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han guardado los datos del inspector",
                    "Se han guardado los datos del inspector \"".concat(username).concat("\""));
            CommonFunctions.goToPage("/faces/users/verInspectores.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            CommonFunctions.goToPage("/faces/users/admin/editarInspector.xhtml?faces-redirect=true");
        }
    }
    
    public void volverToLista() {
       CommonFunctions.goToPage("/faces/users/verInspectores.xhtml");
       
    }
    
    
    public MantenedorInspectorEditarMB() {
    }

    public MantenedorInspectorConversation getMantInsp() {
        return mantInsp;
    }

    public void setMantInsp(MantenedorInspectorConversation mantInsp) {
        this.mantInsp = mantInsp;
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

    public Integer getNumeroInspector() {
        return numeroInspector;
    }

    public void setNumeroInspector(Integer numeroInspector) {
        this.numeroInspector = numeroInspector;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isCheckContraseña() {
        return checkContraseña;
    }

    public void setCheckContraseña(boolean checkContraseña) {
        this.checkContraseña = checkContraseña;
    }
    
    
}
