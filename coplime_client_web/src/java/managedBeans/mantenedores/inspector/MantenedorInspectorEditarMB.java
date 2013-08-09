/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import javax.annotation.PostConstruct;
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
@Named(value = "mantenedorInspectorEditarMB")
@RequestScoped
public class MantenedorInspectorEditarMB {

    @EJB
    private CrudInspectorLocal crudInspector;
           
    @Inject 
    private MantenedorInspectorConversation mantInsp;
    
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
        System.out.println("inspector en vista edit"+this.mantInsp.getApellido1());
       
        this.rut = this.mantInsp.getRut();
        this.nombre = this.mantInsp.getNombre();
        this.apellido1 = this.mantInsp.getApellido1();
        this.apellido2 = this.mantInsp.getApellido2();
        this.mail = this.mantInsp.getMail();
        this.username = this.mantInsp.getUsername();
        this.telefono = this.mantInsp.getTelefono();
    }
    
    public MantenedorInspectorEditarMB() {
    }
    
    public void guardarCambiosInspector(){
        System.out.println("this.rut: "+this.rut);
        System.out.println("Se va a guardar los cambios de un inspector");
        System.out.println("idUser="+rut+" username:"+username+" nombre:"+nombre+" ap1:"+apellido1+" ap2:"+apellido2);
        crudInspector.editarInspector( rut, username, nombre, apellido1, apellido2, mail, checkContraseña, telefono);
        CommonFunctions.goToPage("/faces/users/verInspectores.xhtml");
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/verInspectores.xhtml");
       
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
