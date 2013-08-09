/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import ObjectsForManagedBeans.UsuarioPojo;
import entities.Inspector;
import entities.OperarioMantencion;
import entities.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import otros.CommonFunctions;
import sessionBeans.CrudOperarioLocal;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorOperario")
@SessionScoped
public class MantenedorOperario implements Serializable{
    @EJB
    private CrudOperarioLocal crudOperario;

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private String password;
    private Integer rut;
    private String mail;
    private boolean checkContraseña;
    private Integer telefono;    
    private List<UsuarioPojo> lista;    
    
    
    

    public Collection<UsuarioPojo> getLista() {
        return lista;
    }

    /**
     * Creates a new instance of mantenedorInspector
     */
    public MantenedorOperario() {
    }
    
    @PostConstruct
    public void init() {
        ///////////////VER/////////////////////
        Collection<OperarioMantencion> listaTemp = crudOperario.getAllOperarios();
        UsuarioPojo inspectorTemporal;
        //this.lista = new LinkedList();
        this.lista = new ArrayList();
        for(OperarioMantencion insp_iter : listaTemp) {
            inspectorTemporal = new UsuarioPojo();
            
            inspectorTemporal.setNombre(insp_iter.getUsuario().getNombre()+" "+
                    insp_iter.getUsuario().getApellido1()+" "+
                    insp_iter.getUsuario().getApellido2());
            inspectorTemporal.setNum(insp_iter.getUsuario().getRut());
            inspectorTemporal.setUserName(insp_iter.getUsuario().getUsername());
            this.lista.add(inspectorTemporal);
            
        } 
        //////////Edit/////////////////////
        try{
            Map<String,Object> variableSession = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            Integer idUsuario = (Integer)variableSession.get("idUserToEdit");
            if (idUsuario != null) {
                variableSession.remove("idUserToEdit");
                Usuario usuarioEdit = crudOperario.getOperarioByRut(idUsuario);
                if (usuarioEdit != null) {
                    this.rut = new Integer(usuarioEdit.getRut());
                    this.nombre = usuarioEdit.getNombre();
                    this.apellido1 = usuarioEdit.getApellido1();
                    this.apellido2 = usuarioEdit.getApellido2();
                    this.mail = usuarioEdit.getEmail();
                    this.username = usuarioEdit.getUsername();
                    this.telefono = usuarioEdit.getTelefono();
                    System.out.println(" idUser="+this.rut);
                }
                else {
                    //MOSTRAR ERROR
                }
            }
            
            
        }
        catch(Exception e){
            System.out.println("no hay cookie"); 
        }
        
    }
    
    public void agregarOperario() {
        crudOperario.agregarOperario( username, password, rut, nombre, apellido1, apellido2, mail, telefono);
        this.init();
        CommonFunctions.goToPage("/faces/users/verOperariosMantencion.xhtml");
        
    }
    
    public void guardarCambiosOperario(){
        System.out.println("this.rut: "+this.rut);
        System.out.println("Se va a guardar los cambios de un Operario");
        System.out.println("idUser="+rut+" username:"+username+" nombre:"+nombre+" ap1:"+apellido1+" ap2:"+apellido2);
        crudOperario.editarOperario(rut, username, nombre, apellido1, apellido2, mail, checkContraseña, telefono);
        this.init();
        CommonFunctions.goToPage("/faces/users/verOperariosMantencion.xhtml");
    }
    
    public void editar(int numOperario) {
       System.out.println("NÚMERO DE Operario: "+numOperario);
       FacesContext context = FacesContext.getCurrentInstance();
       Map<String,Object> variableSession = context.getExternalContext().getSessionMap();
       variableSession.put("idUserToEdit", new Integer(numOperario));      
       CommonFunctions.goToPage("/faces/admin/editarOperarioMantencion.xhtml");
       
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
       
    }
    
    public void agregar() {
        CommonFunctions.goToPage("/faces/admin/agregarOperarioMantencion.xhtml");
       
    }
    public String ObtineUserName(int rut){
        for(UsuarioPojo use_iter : lista){
            if(use_iter.getNum()== rut)
                return use_iter.userName;
        }
        return null;        
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
    
    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
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
