 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.UsuarioPojo;
import entities.Inspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import sessionBeans.CrudInspectorLocal;
import entities.Usuario;
import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;






 

/**
 *
 * @author victor
 */
@Named(value = "mantenedorInspector")
@RequestScoped

//@ViewScoped
public class mantenedorInspector extends commonFunctions implements Serializable{
    @EJB
    private CrudInspectorLocal crudInspector;

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String username;
    private String password;
    private Integer rut;
    private String mail;
    private Integer telefono;
    private boolean checkContraseña;
    private List<UsuarioPojo> lista; //collection
    private List<UsuarioPojo> listaBusqueda;
   
    
    /**
     * Creates a new instance of mantenedorInspector
     */
    public mantenedorInspector() {
               
    }
    
    @PostConstruct
    public void init() { ///void init
        
        ///////////////VER/////////////////////
        Collection<Inspector> listaTemp = crudInspector.getAllInspectores();
        UsuarioPojo inspectorTemporal;
        //this.lista = new LinkedList();
        this.lista = new ArrayList();
        for(Inspector insp_iter : listaTemp) {
            inspectorTemporal = new UsuarioPojo();
            
            inspectorTemporal.setNombre(insp_iter.getUsuario().getNombre()+" "+
                    insp_iter.getUsuario().getApellido1()+" "+
                    insp_iter.getUsuario().getApellido2());
            inspectorTemporal.setNum(insp_iter.getUsuario().getRut());
            inspectorTemporal.setUserName(insp_iter.getUsuario().getUsername());
            this.lista.add(inspectorTemporal);
            
        }
        ///////////////Editar/////////////////////  
        try{
            Map<String,Object> variableSession = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            Integer idUsuario = (Integer)variableSession.get("idUserToEdit");
            if (idUsuario != null) {
                variableSession.remove("idUserToEdit");
                Usuario usuarioEdit = crudInspector.getInspectorByRut(idUsuario);
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
    public void update(SelectEvent event) {
        init();
    // your code here...
}

    public List<UsuarioPojo> getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List<UsuarioPojo> listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }
   
        
    public List<UsuarioPojo> getLista(){
        return lista;
    }

    public boolean isCheckContraseña() {
        return checkContraseña;
    }

    public void setCheckContraseña(boolean checkContraseña) {
        this.checkContraseña = checkContraseña;
    }    
           
    public void agregarInspector() {
        crudInspector.agregarInspector(username, password, rut, nombre, apellido1, apellido2, mail, telefono);
        this.init();
        goToPage("/faces/users/verInspectores.xhtml");       
        
    }
    public void guardarCambiosInspector(){
        System.out.println("this.rut: "+this.rut);
        System.out.println("Se va a guardar los cambios de un inspector");
        System.out.println("idUser="+rut+" username:"+username+" nombre:"+nombre+" ap1:"+apellido1+" ap2:"+apellido2);
        crudInspector.editarInspector( rut, username, nombre, apellido1, apellido2, mail, checkContraseña, telefono);
        this.init();
        goToPage("/faces/users/verInspectores.xhtml");
    }
    
    public void volver() {
       goToPage("/faces/users/verInspectores.xhtml");
       
    }
    
    public void agregar() {
       goToPage("/faces/admin/agregarInspector.xhtml");
        
    }
    public String ObtineUserName(int rut){
        for(UsuarioPojo use_iter : lista){
            if(use_iter.getNum()== rut)
                return use_iter.userName;
        }
        return null;        
    }
    
    public void editar(int numInspector) {
       System.out.println("NÚMERO DE INSPECTOR: "+numInspector);
       FacesContext context = FacesContext.getCurrentInstance();
       Map<String,Object> variableSession = context.getExternalContext().getSessionMap();
       variableSession.put("idUserToEdit", new Integer(numInspector));      
       goToPage("/faces/admin/editarInspector.xhtml");
       
    }
    public void eliminar(int numInspector) {
       System.out.println("NÚMERO DE INSPECTOR: "+numInspector);
       //crudInspector.eliminarInspector(new Integer(numInspector));
       crudInspector.eliminarInspector(numInspector);
       init();
       goToPage("/faces/users/verInspectores.xhtml");
       
       //PONER LA LÓGICA DE ELIMINARCIÓN, MOSTRAR MENSAJE DE CONFIRMACIÓN
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
}
