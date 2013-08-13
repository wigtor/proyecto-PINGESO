/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import ObjectsForManagedBeans.UsuarioPojo;
import entities.OperarioMantencion;
import entities.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudOperarioLocal;
import sessionBeans.CrudUsuariosComunLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorOperarioVerListadoMB")
@RequestScoped
public class MantenedorOperarioVerListadoMB {
    @EJB
    private CrudUsuariosComunLocal crudUsuariosComun;

    @EJB
    private CrudOperarioLocal crudOperario;
    
    @Inject 
    private MantenedorOperarioConversation mantOp;
    
    private List<UsuarioPojo> listaBusqueda;
    private List<UsuarioPojo> lista;
    
    public MantenedorOperarioVerListadoMB() {
    }
    
    @PostConstruct
    public void init() {
        ///////////////VER/////////////////////
        Collection<OperarioMantencion> listaTemp = crudOperario.getAllOperarios();
        UsuarioPojo operarioTemporal;
        this.lista = new ArrayList();
        for(OperarioMantencion op_iter : listaTemp) {
            operarioTemporal = new UsuarioPojo();
            
            operarioTemporal.setNombre(op_iter.getUsuario().getNombre()+" "+
                    op_iter.getUsuario().getApellido1()+" "+
                    op_iter.getUsuario().getApellido2());
            operarioTemporal.setNum(op_iter.getUsuario().getRut());
            operarioTemporal.setUserName(op_iter.getUsuario().getUsername());
            this.lista.add(operarioTemporal);
            
        }
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        
    }
    
    public void agregar() {
       CommonFunctions.goToPage("/faces/users/admin/agregarOperarioMantencion.xhtml");
        
    }
    
    public void verDetalles(int numOperario) {
        Usuario usuarioVerDetalle = crudUsuariosComun.getUsuarioByRut(numOperario);
        if (usuarioVerDetalle != null) {
            this.mantOp.setIdUsuarioDetalles(numOperario);
            CommonFunctions.goToPage("/faces/users/verDetallesOperarioMantencion.xhtml");
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/admin/verOperariosMantencion.xhtml");
        }
    }
    
    public void editar(int numOperario) {
        Usuario usuarioEdit = crudUsuariosComun.getUsuarioByRut(numOperario);
        if (usuarioEdit != null) {
            this.mantOp.setIdUsuarioDetalles(numOperario);
            CommonFunctions.goToPage("/faces/users/admin/editarOperarioMantencion.xhtml");
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/admin/verOperariosMantencion.xhtml");
        }
    }
    
    public void eliminar(int numOperario) {
        boolean resultado = crudOperario.eliminarOperario(numOperario);
        FacesMessage msg;
        if (resultado) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Notificación eliminada", "Se ha eliminado correctamente el operario de mantención.");
        }
        else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error, no se ha podido eliminar el operario de mantención seleccionada.");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        CommonFunctions.goToPage("/faces/users/verOperariosMantencion.xhtml?faces-redirect=true");
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
