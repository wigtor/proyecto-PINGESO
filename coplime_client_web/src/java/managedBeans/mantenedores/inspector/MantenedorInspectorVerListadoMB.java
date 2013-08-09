/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import ObjectsForManagedBeans.UsuarioPojo;
import entities.Inspector;
import entities.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
@Named(value = "mantenedorInspectorVerListadoMB")
@RequestScoped
public class MantenedorInspectorVerListadoMB {

    @EJB
    private CrudInspectorLocal crudInspector;
    @Inject 
    private MantenedorInspectorConversation mantInsp;
   
    
    private List<UsuarioPojo> listaBusqueda;
    private List<UsuarioPojo> lista; 
    /**
     * Creates a new instance of MantenedorInspectorVerListadoMB
     */
    public MantenedorInspectorVerListadoMB() {
    }
    
    
    @PostConstruct
    public void init() {         
        
        ///////////////VER/////////////////////
        Collection<Inspector> listaTemp = crudInspector.getAllInspectores();
        UsuarioPojo inspectorTemporal;
        
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
    }
    
    public void agregar() {
       CommonFunctions.goToPage("/faces/users/admin/agregarInspector.xhtml");
        
    }
    public void editar(int numInspector) {
        System.out.println("NÚMERO DE INSPECTOR: "+numInspector);
        Usuario usuarioEdit = crudInspector.getInspectorByRut(numInspector);
        if (usuarioEdit != null) {
            this.mantInsp.setRut(new Integer(usuarioEdit.getRut()));
            this.mantInsp.setNombre(usuarioEdit.getNombre());
            this.mantInsp.setApellido1(usuarioEdit.getApellido1());
            this.mantInsp.setApellido2(usuarioEdit.getApellido2());
            this.mantInsp.setMail(usuarioEdit.getEmail());
            this.mantInsp.setUsername(usuarioEdit.getUsername());
            this.mantInsp.setTelefono(usuarioEdit.getTelefono());
            System.out.println(this.mantInsp.getApellido1());
        }
        else {
            //MOSTRAR ERROR
        }
       CommonFunctions.goToPage("/faces/users/admin/editarInspector.xhtml");
       
    }
    
    public void verDetalles(int numInspector) {
        System.out.println("NÚMERO DE INSPECTOR: "+numInspector);
        Usuario usuarioVerDetalle = crudInspector.getInspectorByRut(numInspector);
        if (usuarioVerDetalle != null) {
            this.mantInsp.setRut(new Integer(usuarioVerDetalle.getRut()));
            this.mantInsp.setNombre(usuarioVerDetalle.getNombre());
            this.mantInsp.setApellido1(usuarioVerDetalle.getApellido1());
            this.mantInsp.setApellido2(usuarioVerDetalle.getApellido2());
            this.mantInsp.setMail(usuarioVerDetalle.getEmail());
            this.mantInsp.setUsername(usuarioVerDetalle.getUsername());
            this.mantInsp.setTelefono(usuarioVerDetalle.getTelefono());
            System.out.println(this.mantInsp.getApellido1());
        }
        else {
            //MOSTRAR ERROR
        }
       CommonFunctions.goToPage("/faces/users/verDetallesInspector.xhtml");
       
    }
    
    public void eliminar(int numInspector) {
       System.out.println("NÚMERO DE INSPECTOR: "+numInspector);
       //crudInspector.eliminarInspector(new Integer(numInspector));
       crudInspector.eliminarInspector(numInspector);
       //init();
        CommonFunctions.goToPage("/faces/users/verInspectores.xhtml");
       
       //PONER LA LÓGICA DE ELIMINARCIÓN, MOSTRAR MENSAJE DE CONFIRMACIÓN
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
       
    }

    public List<UsuarioPojo> getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List<UsuarioPojo> listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public List<UsuarioPojo> getLista() {
        return lista;
    }

    public void setLista(List<UsuarioPojo> lista) {
        this.lista = lista;
    }
    
    
}
