/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.temporizadorEstimacion;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.ConfiguracionSistemaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorTemporizadorEstimacionProgramarMB")
@RequestScoped
public class MantenedorTemporizadorEstimacionProgramarMB {
    @EJB
    private ConfiguracionSistemaLocal configuracionSistema;
    /**
     * Creates a new instance of MantenedorTemporizadorEstimacionProgramarMB
     */
    Integer numHorasIntervalo,numHorasIntervaloOld;
    @PostConstruct
    public void init(){
        numHorasIntervalo = this.obtenerValorTemporizador();
        numHorasIntervaloOld = numHorasIntervalo;
    }
    public MantenedorTemporizadorEstimacionProgramarMB() {
    }

    public Integer getNumHorasIntervalo() {
        return numHorasIntervalo;
    }

    public void setNumHorasIntervalo(Integer numHorasIntervalo) {
        this.numHorasIntervalo = numHorasIntervalo;
    }
    
    
    
    public void fijarTemporizador(){
        //TODO deshabilitar-habilitar Botón Guardar según se hayan hecho cambios o no
        if(numHorasIntervalo==numHorasIntervaloOld){//¿Ha cambiado este campo?
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,  "Este valor ya está configurado en el sistema.", "Error.");
        } else {
          configuracionSistema.fijarIntervaloEstimacionContenedores(numHorasIntervalo);
          numHorasIntervaloOld = numHorasIntervalo;
          CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO, "Se ha fijado correctamente el nuevo intervalo.", "Éxito.");
          CommonFunctions.goToPage("/faces/users/verInspectores.xhtml?faces-redirect=true");
        }
        
    }
    
    private Integer obtenerValorTemporizador(){
        return configuracionSistema.obtenerIntervaloEstimacionContenedores();
    }
    
    public void btnVolver(){
        //TODO revisar URLs de retorno
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml?faces-redirect=true");
    }
}
