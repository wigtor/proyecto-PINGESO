/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.administrador;

import ObjectsForManagedBeans.UsuarioPojo;
import entities.Administrador;
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
import sessionBeans.CrudAdministradorLocal;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorAdministradorVerListadoMB")
@RequestScoped
public class MantenedorAdministradorVerListadoMB {

    @EJB
    private CrudAdministradorLocal crudAdministrador;
    @Inject 
    private MantenedorAdministradorConversation mantAdm;
   
    
    private List<UsuarioPojo> listaBusqueda;
    private List<UsuarioPojo> lista; 
    
    
    public MantenedorAdministradorVerListadoMB() {
    }
    
    
    @PostConstruct
    public void init() {         
        
        Collection<Administrador> listaTemp = crudAdministrador.getAllAdministradores();
        UsuarioPojo administradorTemporal;
        
        this.lista = new ArrayList();
        for(Administrador adm_iter : listaTemp) {
            administradorTemporal = new UsuarioPojo();
            
            administradorTemporal.setNombre(adm_iter.getUsuario().getNombre()+" "+
                    adm_iter.getUsuario().getApellido1()+" "+
                    adm_iter.getUsuario().getApellido2());
            administradorTemporal.setNum(adm_iter.getUsuario().getRut());
            administradorTemporal.setUserName(adm_iter.getUsuario().getUsername());
            this.lista.add(administradorTemporal);
            
        }
    }
    public void agregar() {
       CommonFunctions.goToPage("/faces/users/admin/agregarAdministrador.xhtml");
    }
    
    public void editar(int numAdministrador) {
        Usuario usuarioEdit = crudAdministrador.getAdministradorByRut(numAdministrador);
        if (usuarioEdit != null) {
            this.mantAdm.setIdUsuarioDetalles(numAdministrador);
            CommonFunctions.goToPage("/faces/users/admin/editarAdministrador.xhtml");
        }
        else {
            //MOSTRAR ERROR
            this.mantAdm.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/admin/verAdministradores.xhtml");
        }
    }
    
    public void verDetalles(int numAdministrador) {
        Usuario usuarioVerDetalle = crudAdministrador.getAdministradorByRut(numAdministrador);
        if (usuarioVerDetalle != null) { //Verifico que exista
            this.mantAdm.setIdUsuarioDetalles(numAdministrador);
            CommonFunctions.goToPage("/faces/users/admin/verDetallesAdministrador.xhtml");
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/admin/verAdministradores.xhtml");
        }
    }
    
    public void eliminar(int numAdministrador) {
       crudAdministrador.eliminarAdministrador(numAdministrador);
       CommonFunctions.goToPage("/faces/users/admin/verAdministradores.xhtml");
       
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
