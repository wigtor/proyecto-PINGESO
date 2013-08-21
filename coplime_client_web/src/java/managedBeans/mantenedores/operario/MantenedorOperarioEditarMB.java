/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import entities.OperarioMantencion;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudOperarioLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorOperarioEditarMB")
@RequestScoped
public class MantenedorOperarioEditarMB {

    @EJB
    private CrudOperarioLocal crudOperario;
    
    @Inject 
    private MantenedorOperarioConversation mantOp;
    
    private Integer numeroOperario;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private String password;
    private Integer rut;
    private String mail;
    private Integer telefono;
    private boolean checkContraseña;
    
    public MantenedorOperarioEditarMB() {
    }
    
    @PostConstruct
    public void init() { 
        if (mantOp.getIdUsuarioDetalles()!= null) {
            Integer numOp = mantOp.getIdUsuarioDetalles();
            cargarDatosOperario(numOp);
        }
        else {
            //MOSTRAR ERROR
            
            volverToLista();
        }
    }
    
    private void cargarDatosOperario(Integer numAdmin) {
        OperarioMantencion operarioSelect = crudOperario.getOperarioByRut(numAdmin);
        if (operarioSelect == null) {
            volverToLista();
            return;
        }
        this.numeroOperario = operarioSelect.getId();
        this.rut = operarioSelect.getUsuario().getRut();
        this.nombre = operarioSelect.getUsuario().getNombre();
        this.apellido1 = operarioSelect.getUsuario().getApellido1();
        this.apellido2 = operarioSelect.getUsuario().getApellido2();
        this.mail = operarioSelect.getUsuario().getEmail();
        this.username = operarioSelect.getUsuario().getUsername();
        this.telefono = operarioSelect.getUsuario().getTelefono();
    }
    
    public void guardarCambiosOperario(){
        mantOp.endConversation();
        try {
            crudOperario.editarOperario(rut, username, nombre, apellido1, apellido2, mail, checkContraseña, telefono);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han guardado los datos del operario de mantención",
                    "Se han guardado los datos del operario de mantención \"".concat(username).concat("\""));
            CommonFunctions.goToPage("/faces/users/verOperariosMantencion.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            CommonFunctions.goToPage("/faces/users/admin/editarOperarioMantencion.xhtml?faces-redirect=true");
        }
    }
    
    public void volverToLista() {
        mantOp.endConversation();
        CommonFunctions.goToPage("/faces/users/verOperariosMantencion.xhtml");
    }

    public MantenedorOperarioConversation getMantOp() {
        return mantOp;
    }

    public void setMantOp(MantenedorOperarioConversation mantOp) {
        this.mantOp = mantOp;
    }
    
    public Integer getNumeroOperario() {
        return numeroOperario;
    }

    public void setNumeroOperario(Integer numeroOperario) {
        this.numeroOperario = numeroOperario;
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
