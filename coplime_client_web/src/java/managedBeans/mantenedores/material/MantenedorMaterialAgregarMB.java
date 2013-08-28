/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.material;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.CrudMaterialLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorMaterialAgregarMB")
@RequestScoped
public class MantenedorMaterialAgregarMB {

    @EJB
    private CrudMaterialLocal crudMat;
    
    private String nombreMaterial;

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }
    
    /**
     * Creates a new instance of MantenedorMaterialAgregarMB
     */
    public MantenedorMaterialAgregarMB() {
    }
    
    public void agregarMaterial(){
        if(nombreMaterial.length()==0){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_WARN, "Ingrese un nombre de material", "No ha ingresado el nombre del nuevo material.");
        } else {
            try{
            crudMat.agregarMaterial(nombreMaterial);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha agregado el nuevo material.",
                    "Se ha creado el material \"".concat(nombreMaterial).concat("\""));
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
