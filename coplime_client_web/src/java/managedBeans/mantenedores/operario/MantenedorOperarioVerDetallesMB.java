/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import entities.OperarioMantencion;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudOperarioLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorOperarioVerDetallesMB")
@RequestScoped
public class MantenedorOperarioVerDetallesMB {

    @EJB
    private CrudOperarioLocal crudOperario;
    @Inject 
    private MantenedorOperarioConversation mantOp;
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private Integer rut;
    private String mail;
    private Integer telefono;
    private Integer numSolicitudesMantencionesRealizadas;
    
    public MantenedorOperarioVerDetallesMB() {
    }
    
    @PostConstruct
    public void init() { 
        System.out.println("inspector en vista ver"+this.mantOp.getApellido1());
       
        this.rut = this.mantOp.getRut();
        this.nombre = this.mantOp.getNombre();
        this.apellido1 = this.mantOp.getApellido1();
        this.apellido2 = this.mantOp.getApellido2();
        this.mail = this.mantOp.getMail();
        this.username = this.mantOp.getUsername();
        this.telefono = this.mantOp.getTelefono();
        
        
        Collection<OperarioMantencion> listaTemp = crudOperario.getAllOperarios();
        
        for(OperarioMantencion op_iter : listaTemp){
            if(this.rut.equals(op_iter.getUsuario().getRut()) ){
               
               this.numSolicitudesMantencionesRealizadas = op_iter.getSolicitudesMantencionRealizadas().size();
               break;
            }
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

    public Integer getNumSolicitudesMantencionesRealizadas() {
        return numSolicitudesMantencionesRealizadas;
    }

    public void setNumSolicitudesMantencionesRealizadas(Integer numSolicitudesMantencionesRealizadas) {
        this.numSolicitudesMantencionesRealizadas = numSolicitudesMantencionesRealizadas;
    }
    
    
}
