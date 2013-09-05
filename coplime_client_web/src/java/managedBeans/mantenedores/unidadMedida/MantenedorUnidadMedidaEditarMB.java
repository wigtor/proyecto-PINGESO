/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.unidadMedida;


import entities.UnidadMedida;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudUnidadMedidaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorUnidadMedidaEditarMB")
@RequestScoped
public class MantenedorUnidadMedidaEditarMB {

    @EJB
    private CrudUnidadMedidaLocal crudUnidadMedida;
    
    @Inject
    private MantenedorUnidadMedidaConversation mantUnidadMedida;
    
    private String nombreUnidadMedidaAntiguo;
    private String nombreUnidadMedida;
    private Integer idUnidadMedida;

    public CrudUnidadMedidaLocal getCrudUnidadMedida() {
        return crudUnidadMedida;
    }

    public void setCrudUnidadMedida(CrudUnidadMedidaLocal crudUnidad) {
        this.crudUnidadMedida = crudUnidad;
    }

    public MantenedorUnidadMedidaConversation getMantUnidadMedida() {
        return mantUnidadMedida;
    }

    public void setMantUnidadMedida(MantenedorUnidadMedidaConversation mantUni) {
        this.mantUnidadMedida = mantUni;
    }

    public String getNombreUnidadMedidaAntiguo() {
        return nombreUnidadMedidaAntiguo;
    }

    public void setNombreUnidadMedidaAntiguo(String nombreUnidadAntiguo) {
        this.nombreUnidadMedidaAntiguo = nombreUnidadAntiguo;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public Integer getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(Integer idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }
    
    @PostConstruct
    public void init(){
        if(mantUnidadMedida.getIdUnidadMedida()!=null){
            idUnidadMedida = mantUnidadMedida.getIdUnidadMedida();
            cargarDatosEstado(idUnidadMedida);
        } else {
            volverToLista();
        }
    }
    
    public void cargarDatosEstado(Integer idMaterialCargar){
        UnidadMedida uni= crudUnidadMedida.getUnidadByID(idMaterialCargar);
        if(uni != null){
            this.nombreUnidadMedida = uni.getNombreUnidad();
            nombreUnidadMedidaAntiguo = nombreUnidadMedida;
        } else {
            volverToLista();
        }
    }
    
    public void guardarDatosUnidadMedida (){
        if(nombreUnidadMedida.equals(nombreUnidadMedidaAntiguo)){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, "Error: ese nombre ya está guardado.", "Ese nombre ya está guardado." );
        } else {
            mantUnidadMedida.endConversation();
            try{
                crudUnidadMedida.editarUnidadMedida(idUnidadMedida, nombreUnidadMedidaAntiguo, nombreUnidadMedida);
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han guardado los datos de la unidad de medida.",
                    "Se han guardado los datos de la unidad de medida \"".concat(nombreUnidadMedida).concat("\""));
                CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml?faces-redirect=true");
            } catch (Exception e){
                 CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                        e.getMessage(), 
                        e.getMessage());
                CommonFunctions.goToPage("/faces/users/admin/config/editarUnidadMedida.xhtml?faces-redirect=true");
            }
        }
    }
    
    public void volverToLista(){
        mantUnidadMedida.endConversation();
        CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
    }
    
    public MantenedorUnidadMedidaEditarMB() {
        
    }
}
