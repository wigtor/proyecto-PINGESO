/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.comuna;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.CrudComunaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorComunaAgregarMB")
@RequestScoped
public class MantenedorComunaAgregarMB {

    @EJB
    private CrudComunaLocal crudC;
    
    private String nombreComuna;

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreCom) {
        this.nombreComuna = nombreCom;
    }
    
    /**
     * Creates a new instance of MantenedorEstadoAgregarMB
     */
    public MantenedorComunaAgregarMB() {
    }
    
    public void agregarComuna(){
        if(nombreComuna.length()==0){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "Ingrese una comuna", "No ha ingresado el nombre de la nueva comuna.");
        } else {
            try{
            crudC.agregarComuna(nombreComuna);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha agregado la nueva comuna.",
                    "Se ha creado la comuna \"".concat(nombreComuna).concat("\""));
            volver();
            }catch(Exception e){
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "No se pudo agregar la nueva comuna.", "Error:".concat(e.toString()));
            }
        }  
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
        
    }
}
