/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.UsuarioPojo;
import entities.Inspector;
import entities.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import sessionBeans.CrudInspectorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "MantenedorInspectorVerListado")
//@ViewScoped
//@SessionScoped
@RequestScoped
public class MantenedorInspectorVerListado extends commonFunctions{

    @EJB
    private CrudInspectorLocal crudInspector;
    @Inject 
    private MantenedorInspector mantInsp;
   
    
    private List<UsuarioPojo> listaBusqueda;
    private List<UsuarioPojo> lista; 
    /**
     * Creates a new instance of MantenedorInspectorVerListado
     */
    public MantenedorInspectorVerListado() {
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
       goToPage("/faces/admin/agregarInspector.xhtml");
        
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
       goToPage("/faces/admin/editarInspector.xhtml");
       
    }
    public void eliminar(int numInspector) {
       System.out.println("NÚMERO DE INSPECTOR: "+numInspector);
       //crudInspector.eliminarInspector(new Integer(numInspector));
       crudInspector.eliminarInspector(numInspector);
       //init();
       goToPage("/faces/users/verInspectores.xhtml");
       
       //PONER LA LÓGICA DE ELIMINARCIÓN, MOSTRAR MENSAJE DE CONFIRMACIÓN
    }
    
    public void volver() {
       goToPage("/faces/users/verPuntosLimpios.xhtml");
       
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
