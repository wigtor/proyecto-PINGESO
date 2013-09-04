/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.comuna;

import entities.Comuna;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudComunaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorComunaEditarMB")
@RequestScoped
public class MantenedorComunaEditarMB {

    @EJB
    private CrudComunaLocal crudComuna;
    
    @Inject
    private MantenedorComunaConversation mantCom;
    
    private String nombreComunaAntiguo;
    private String nombreComuna;
    private Integer idComuna;

    public CrudComunaLocal getCrudComuna() {
        return crudComuna;
    }

    public void setCrudComuna(CrudComunaLocal crudComuna) {
        this.crudComuna = crudComuna;
    }

    public MantenedorComunaConversation getMantCom() {
        return mantCom;
    }

    public void setMantCom(MantenedorComunaConversation mantCom) {
        this.mantCom = mantCom;
    }

    public String getNombreComunaAntiguo() {
        return nombreComunaAntiguo;
    }

    public void setNombreComunaAntiguo(String nombreComunaAntiguo) {
        this.nombreComunaAntiguo = nombreComunaAntiguo;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }

    public Integer getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Integer idComuna) {
        this.idComuna = idComuna;
    }
    
    @PostConstruct
    public void init(){
        if(mantCom.getIdComuna()!=null){
            idComuna = mantCom.getIdComuna();
            cargarDatosComuna(idComuna);
        } else {
            volverToLista();
        }
    }
    
    public void cargarDatosComuna(Integer idComunaCargar){
        Comuna com = crudComuna.getComunaByID(idComunaCargar);
        if(com != null){
            this.nombreComuna = com.getNombre();
            nombreComunaAntiguo = nombreComuna;
        } else {
            volverToLista();
        }
    }
    
    public void guardarDatosComuna (){
        if(nombreComuna.equals(nombreComunaAntiguo)){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, "Error: ese nombre ya está guardado.", "Ese nombre ya está guardado." );
        } else {
            mantCom.endConversation();
            try{
                crudComuna.editarComuna(idComuna, nombreComunaAntiguo, nombreComuna);
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han guardado los datos de la comuna.",
                    "Se han guardado los datos de la comuna \"".concat(nombreComuna).concat("\""));
                CommonFunctions.goToPage("/faces/users/admin/configuracionSistema.xhtml?faces-redirect=true");
            } catch (Exception e){
                 CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                        e.getMessage(), 
                        e.getMessage());
                CommonFunctions.goToPage("/faces/users/admin/editarComuna.xhtml?faces-redirect=true");
            }
        }
    }
    
    public void volverToLista(){
        mantCom.endConversation();
        CommonFunctions.goToPage("/faces/users/admin/configuracionSistema.xhtml");
    }
    
    public MantenedorComunaEditarMB() {
    }
}
