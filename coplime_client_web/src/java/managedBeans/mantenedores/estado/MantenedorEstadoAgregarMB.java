/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.estado;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.CrudEstadoPuntoLimpioLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorEstadoAgregarMB")
@RequestScoped
public class MantenedorEstadoAgregarMB {

    @EJB
    private CrudEstadoPuntoLimpioLocal crudE;
    
    private String nombreEstado;

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEst) {
        this.nombreEstado = nombreEst;
    }
    
    /**
     * Creates a new instance of MantenedorEstadoAgregarMB
     */
    public MantenedorEstadoAgregarMB() {
    }
    
    public void agregarEstado(){
        if(nombreEstado.length()==0){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "Ingrese un estado", "No ha ingresado el nombre del nuevo estado.");
        } else {
            try{
            crudE.agregarEstadoPuntoLimpio(nombreEstado);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha agregado el nuevo estado.",
                    "Se ha creado el estado \"".concat(nombreEstado).concat("\""));
            volver();
            }catch(Exception e){
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "No se pudo agregar el nuevo material.", "Error:".concat(e.toString()));
            }
        }  
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
        
    }
}
