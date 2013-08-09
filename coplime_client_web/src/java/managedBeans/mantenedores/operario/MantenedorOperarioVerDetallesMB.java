/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import entities.OperarioMantencion;
import entities.Usuario;
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
    
    private Integer numeroOperario;
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
        
        this.numSolicitudesMantencionesRealizadas = operarioSelect.getSolicitudesMantencionRealizadas().size();
    }
    
    public void volverToLista() {
        CommonFunctions.goToPage("/faces/users/verOperariosMantencion.xhtml");
       
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
