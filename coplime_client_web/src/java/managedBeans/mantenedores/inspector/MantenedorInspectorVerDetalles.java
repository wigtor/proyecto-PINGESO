/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import entities.Inspector;
import java.util.Collection;
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
@Named(value = "MantenedorInspectorVerDetalles")
@RequestScoped
public class MantenedorInspectorVerDetalles {

    @EJB
    private CrudInspectorLocal crudInspector;
    @Inject 
    private MantenedorInspector mantInsp;
    
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
    
    public MantenedorInspectorVerDetalles() {
    }
    
    @PostConstruct
    public void init() { 
        System.out.println("inspector en vista ver"+this.mantInsp.getApellido1());
       
        this.rut = this.mantInsp.getRut();
        this.nombre = this.mantInsp.getNombre();
        this.apellido1 = this.mantInsp.getApellido1();
        this.apellido2 = this.mantInsp.getApellido2();
        this.mail = this.mantInsp.getMail();
        this.username = this.mantInsp.getUsername();
        this.telefono = this.mantInsp.getTelefono();
        
        
        Collection<Inspector> listaTemp = crudInspector.getAllInspectores();
        
        for(Inspector insp_iter : listaTemp){
            if(this.rut.equals(insp_iter.getUsuario().getRut()) ){
               
               this.numPuntosLimpios = insp_iter.getPuntosLimpios().size();
               this.numRevisionesRealizadas = insp_iter.getRevisionesRealizadas().size();
               this.numSolicitudesMantencionesRealizadas = insp_iter.getSolicitudesMantencionRealizadas().size();
               break;
            }
        }
        
    }
    
    public void volver() {
        CommonFunctions.goToPage("/faces/users/verInspectores.xhtml");
       
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
