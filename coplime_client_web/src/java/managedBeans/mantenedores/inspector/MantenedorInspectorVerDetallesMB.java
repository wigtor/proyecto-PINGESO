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
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudInspectorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorInspectorVerDetallesMB")
@RequestScoped
public class MantenedorInspectorVerDetallesMB {

    @EJB
    private CrudInspectorLocal crudInspector;
    @Inject 
    private MantenedorInspectorConversation mantInsp;
    
    private Integer numeroInspector;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private Integer rut;
    private String mail;
    private Integer telefono;
    private Integer numPuntosLimpios;
    private Integer numRevisionesRealizadas;
    private Integer numSolicitudesMantencionesRealizadas;
    
    public MantenedorInspectorVerDetallesMB() {
    }
    
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
    
        this.numPuntosLimpios = inspSelect.getPuntosLimpios().size();
        this.numRevisionesRealizadas = inspSelect.getRevisionesRealizadas().size();
        this.numSolicitudesMantencionesRealizadas = inspSelect.getSolicitudesMantencionRealizadas().size();
        
    }
    
    public void volverToLista() {
        mantInsp.endConversation();
        CommonFunctions.goToPage("/faces/users/verInspectores.xhtml");
    }

    public Integer getNumeroInspector() {
        return numeroInspector;
    }

    public void setNumeroInspector(Integer numeroInspector) {
        this.numeroInspector = numeroInspector;
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

    public Integer getNumPuntosLimpios() {
        return numPuntosLimpios;
    }

    public void setNumPuntosLimpios(Integer numPuntosLimpios) {
        this.numPuntosLimpios = numPuntosLimpios;
    }

    public Integer getNumRevisionesRealizadas() {
        return numRevisionesRealizadas;
    }

    public void setNumRevisionesRealizadas(Integer numRevisionesRealizadas) {
        this.numRevisionesRealizadas = numRevisionesRealizadas;
    }

    public Integer getNumSolicitudesMantencionesRealizadas() {
        return numSolicitudesMantencionesRealizadas;
    }

    public void setNumSolicitudesMantencionesRealizadas(Integer numSolicitudesMantencionesRealizadas) {
        this.numSolicitudesMantencionesRealizadas = numSolicitudesMantencionesRealizadas;
    }

    
    
    
    
}
