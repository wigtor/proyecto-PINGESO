/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.estado;

import entities.Comuna;
import entities.Estado;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudEstadoPuntoLimpioLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorEstadoEditarMB")
@RequestScoped
public class MantenedorEstadoEditarMB {

    @EJB
    private CrudEstadoPuntoLimpioLocal crudEstado;
    
    @Inject
    private MantenedorEstadoConversation mantEstado;
    
    private String nombreEstadoAntiguo;
    private String nombreEstado;
    private Integer idEstado;

    public CrudEstadoPuntoLimpioLocal getCrudEstado() {
        return crudEstado;
    }

    public void setCrudEstado(CrudEstadoPuntoLimpioLocal crudEstadoPL) {
        this.crudEstado = crudEstadoPL;
    }

    public MantenedorEstadoConversation getMantEstado() {
        return mantEstado;
    }

    public void setMantEstado(MantenedorEstadoConversation mantEst) {
        this.mantEstado = mantEst;
    }

    public String getNombreEstadoAntiguo() {
        return nombreEstadoAntiguo;
    }

    public void setNombreEstadoAntiguo(String nombreEstAntiguo) {
        this.nombreEstadoAntiguo = nombreEstAntiguo;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    @PostConstruct
    public void init(){
        if(mantEstado.getIdEstado()!=null){
            idEstado = mantEstado.getIdEstado();
            cargarDatosEstado(idEstado);
        } else {
            volverToLista();
        }
    }
    
    public void cargarDatosEstado(Integer idComunaCargar){
        Estado est = crudEstado.getEstadoPuntoLimpioPorID(idComunaCargar);
        if(est != null){
            this.nombreEstado = est.getNombreEstado();
            nombreEstadoAntiguo = nombreEstado;
        } else {
            volverToLista();
        }
    }
    
    public void guardarDatosComuna (){
        if(nombreEstado.equals(nombreEstadoAntiguo)){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, "Error: ese nombre ya está guardado.", "Ese nombre ya está guardado." );
        } else {
            mantEstado.endConversation();
            try{
                crudEstado.editarEstadoPuntoLimpio(idEstado, nombreEstadoAntiguo, nombreEstado);
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han guardado los datos del estado.",
                    "Se han guardado los datos del estado \"".concat(nombreEstado).concat("\""));
                CommonFunctions.goToPage("/faces/users/admin/configuracionSistema.xhtml?faces-redirect=true");
            } catch (Exception e){
                 CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                        e.getMessage(), 
                        e.getMessage());
                CommonFunctions.goToPage("/faces/users/admin/editarEstado.xhtml?faces-redirect=true");
            }
        }
    }
    
    public void volverToLista(){
        mantEstado.endConversation();
        CommonFunctions.goToPage("/faces/users/admin/configuracionSistema.xhtml");
    }
    
    public MantenedorEstadoEditarMB() {
        
    }
}
