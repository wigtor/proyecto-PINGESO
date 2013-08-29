/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.tipoIncidencia;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.CrudTipoIncidenciaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorTipoIncidenciaAgregarMB")
@RequestScoped
public class MantenedorTipoIncidenciaAgregarMB {

    @EJB
    private CrudTipoIncidenciaLocal crudTipoInc;
    
    private String nombreTipoInc;
    private boolean esVisibleUsuario;

    public String getNombreTipoInc() {
        return nombreTipoInc;
    }

    public void setNombreTipoInc(String nombreTipoInc) {
        this.nombreTipoInc = nombreTipoInc;
    }

    public boolean isEsVisibleUsuario() {
        return esVisibleUsuario;
    }

    public void setEsVisibleUsuario(boolean esVisibleUsuario) {
        this.esVisibleUsuario = esVisibleUsuario;
    }

        
    /**
     * Creates a new instance of MantenedorTipoIncidenciaAgregarMB
     */
    public MantenedorTipoIncidenciaAgregarMB() {
    }
    
    public void agregarTipoInc(){
        if(nombreTipoInc.length()==0){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "Ingrese un tipo de incidencia", "No ha ingresado el nombre del nuevo tipo de incidencia.");
        } else {
            try{
            crudTipoInc.agregarTipoIncidencia(nombreTipoInc,esVisibleUsuario);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha agregado el nuevo tipo de incidencia.",
                    "Se ha creado el tipo de incidencia \"".concat(nombreTipoInc).concat("\""));
            volver();
            }catch(Exception e){
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "No se pudo agregar el tipo de incidencia.", "Error:".concat(e.toString()));
            }
        }  
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
        
    }
}
