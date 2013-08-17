/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import ObjectsForManagedBeans.UsuarioPojo;
import entities.Inspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
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
    public void editar(Integer numInspector) {
        Inspector usuarioEdit = crudInspector.getInspectorByRut(numInspector);
        if (usuarioEdit != null) {
            this.mantInsp.setIdUsuarioDetalles(numInspector);
            CommonFunctions.goToPage("/faces/users/admin/editarInspector.xhtml");
        }
        else {
            //MOSTRAR ERROR
            this.mantInsp.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/admin/verInspectores.xhtml");
        }
    }
    
    public void verDetalles(Integer numInspector) {
        Inspector usuarioVerDetalle = crudInspector.getInspectorByRut(numInspector);
        if (usuarioVerDetalle != null) {
            this.mantInsp.setIdUsuarioDetalles(numInspector);
            CommonFunctions.goToPage("/faces/users/verDetallesInspector.xhtml");
        }
        else {
            //MOSTRAR ERROR
            this.mantInsp.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/admin/verInspectores.xhtml");
        }
    }
    
    public void eliminar(Integer numInspector) {
        boolean resultado = crudInspector.eliminarInspector(numInspector);
        if (resultado) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha eliminado el inspector",
                    "Se ha eliminado correctamente el inspector ");
        } else {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al eliminar el inspector",
                    "Error, no se ha podido eliminar el inspector seleccionado");
        }
        CommonFunctions.goToPage("/faces/users/verInspectores.xhtml?faces-redirect=true");
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
