/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.tipoIncidencia;



import entities.TipoIncidencia;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudTipoIncidenciaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorTipoIncidenciaEditarMB")
@RequestScoped
public class MantenedorTipoIncidenciaEditarMB {

    @EJB
    private CrudTipoIncidenciaLocal crudTipoIncidencia;
    
    @Inject
    private MantenedorTipoIncidenciaConversation mantTipoIncidencia;
    
    private String nombreTipoIncidenciaAntiguo;
    private String nombreTipoIncidencia;
    private Boolean visibleAlUsuarioAntiguo;
    private Boolean visibleAlUsuario;

    public Boolean getVisibleAlUsuario() {
        return visibleAlUsuario;
    }

    public void setVisibleAlUsuario(Boolean visibleAlUsuario) {
        this.visibleAlUsuario = visibleAlUsuario;
    }
    private Integer idTipoIncidencia;

    public CrudTipoIncidenciaLocal getCrudTipoIncidencia() {
        return crudTipoIncidencia;
    }

    public void setCrudTipoIncidencia(CrudTipoIncidenciaLocal crudTipoInc) {
        this.crudTipoIncidencia = crudTipoInc;
    }

    public MantenedorTipoIncidenciaConversation getMantTipoIncidencia() {
        return mantTipoIncidencia;
    }

    public void setMantTipoIncidencia(MantenedorTipoIncidenciaConversation mantTipoInc) {
        this.mantTipoIncidencia = mantTipoInc;
    }

    public String getNombreTipoIncidenciaAntiguo() {
        return nombreTipoIncidenciaAntiguo;
    }

    public void setNombreTipoIncidenciaAntiguo(String nombreTipoIncidenciaAntiguo) {
        this.nombreTipoIncidenciaAntiguo = nombreTipoIncidenciaAntiguo;
    }

    public String getNombreTipoIncidencia() {
        return nombreTipoIncidencia;
    }

    public void setNombreTipoIncidencia(String nombreTipoIncidencia) {
        this.nombreTipoIncidencia = nombreTipoIncidencia;
    }

    public Integer getIdTipoIncidencia() {
        return idTipoIncidencia;
    }

    public void setIdTipoIncidencia(Integer idTipoIncidencia) {
        this.idTipoIncidencia = idTipoIncidencia;
    }
    
    @PostConstruct
    public void init(){
        if(mantTipoIncidencia.getIdTipoInc()!=null){
            idTipoIncidencia = mantTipoIncidencia.getIdTipoInc();
            cargarDatosTipoIncidencia(idTipoIncidencia);
        } else {
            volverToLista();
        }
    }
    
    public void cargarDatosTipoIncidencia(Integer idMaterialCargar){
        TipoIncidencia tipoInc = crudTipoIncidencia.getTipoIncidenciaByID(idMaterialCargar);
        if(tipoInc != null){
            this.nombreTipoIncidencia = tipoInc.getNombreIncidencia();
            nombreTipoIncidenciaAntiguo = nombreTipoIncidencia;
            this.visibleAlUsuario = tipoInc.isVisibleAlUsuario();
            visibleAlUsuarioAntiguo = visibleAlUsuario;
        } else {
            volverToLista();
        }
    }
    
    public void guardarDatosTipoIncidencia (){
            mantTipoIncidencia.endConversation();
            try{
                crudTipoIncidencia.editarTipoIncidencia(idTipoIncidencia, nombreTipoIncidenciaAntiguo, nombreTipoIncidencia,visibleAlUsuarioAntiguo,visibleAlUsuario);
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han guardado los datos del tipo de incidencia.",
                    "Se han guardado los datos del tipo de incidencia \"".concat(nombreTipoIncidencia).concat("\""));
                CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml?faces-redirect=true");
            } catch (Exception e){
                 CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                        e.getMessage(), 
                        e.getMessage());
                CommonFunctions.goToPage("/faces/users/admin/config/editarTipoIncidencia.xhtml?faces-redirect=true");
            }
    }
    
    public void volverToLista(){
        mantTipoIncidencia.endConversation();
        CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
    }
    
    public MantenedorTipoIncidenciaEditarMB() {
        
    }
}
