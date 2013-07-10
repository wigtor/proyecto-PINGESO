/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.InspectorPojo;
import entities.Inspector;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import sessionBeans.CrudInspectorLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorInspector")
@RequestScoped
public class mantenedorInspector {
    @EJB
    private CrudInspectorLocal crudInspector;

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private String password;
    private int rut;
    private String mail;
    private int telefono;
    
    private Collection<InspectorPojo> lista;

    public Collection<InspectorPojo> getLista() {
        return lista;
    }

    /**
     * Creates a new instance of mantenedorInspector
     */
    public mantenedorInspector() {
    }
    
    @PostConstruct
    public void init() {
        Collection<Inspector> listaTemp = crudInspector.getAllInspectores();
        InspectorPojo inspectorTemporal;
        this.lista = new LinkedList();
        for(Inspector insp_iter : listaTemp) {
            inspectorTemporal = new InspectorPojo();
            
            inspectorTemporal.setNombre(insp_iter.getUsuario().getNombre()+" "+
                    insp_iter.getUsuario().getApellido1()+" "+
                    insp_iter.getUsuario().getApellido2());
            inspectorTemporal.setNum(insp_iter.getUsuario().getRut());
            this.lista.add(inspectorTemporal);
        
        }
        
    }
    
    public void agregarInspector() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            crudInspector.agregarInspector( username, password, rut, nombre, apellido1, apellido2, mail, telefono);
            this.init();
            FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/faces/users/verInspectores.xhtml");
        }
        catch (Exception e) {
            try {
                
                FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/faces/users/verPuntosLimpios.xhtml");
            }
            catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    }
    
    public void volver() {
       ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
       try {
           externalContext.redirect(externalContext.getRequestContextPath() + "/faces/users/verPuntosLimpios.xhtml");
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }
    
    public void agregar() {
       ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
       try {
           externalContext.redirect(externalContext.getRequestContextPath() + "/faces/admin/agregarInspector.xhtml");
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }
    
    public CrudInspectorLocal getCrudInspector() {
        return crudInspector;
    }

    public void setCrudInspector(CrudInspectorLocal crudInspector) {
        this.crudInspector = crudInspector;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }
    
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}