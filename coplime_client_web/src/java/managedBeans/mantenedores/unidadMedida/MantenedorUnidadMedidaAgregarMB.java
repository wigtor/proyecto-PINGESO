/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.unidadMedida;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.CrudUnidadMedidaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorUnidadMedidaAgregarMB")
@RequestScoped
public class MantenedorUnidadMedidaAgregarMB {

    @EJB
    private CrudUnidadMedidaLocal crudU;
    
    private String nombreUnidad;

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUni) {
        this.nombreUnidad = nombreUni;
    }
    
    /**
     * Creates a new instance of MantenedorEstadoAgregarMB
     */
    public MantenedorUnidadMedidaAgregarMB() {
    }
    
    public void agregarUnidad(){
        if(nombreUnidad.length()==0){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "Ingrese una unidad", "No ha ingresado el nombre de la nueva unidad de medida.");
        } else {
            try{
            crudU.agregarUnidadMedida(nombreUnidad);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha agregado la nueva unidad.",
                    "Se ha creado la nueva unidad de medida \"".concat(nombreUnidad).concat("\""));
            volver();
            }catch(Exception e){
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "No se pudo agregar la nueva unidad de medida.", "Error:".concat(e.toString()));
            }
        }  
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
        
    }
}
