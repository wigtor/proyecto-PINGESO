/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.material;


import entities.Material;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudMaterialLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorMaterialEditarMB")
@RequestScoped
public class MantenedorMaterialEditarMB {

    @EJB
    private CrudMaterialLocal crudMaterial;
    
    @Inject
    private MantenedorMaterialConversation mantMaterial;
    
    private String nombreMaterialAntiguo;
    private String nombreMaterial;
    private Integer idMaterial;

    public CrudMaterialLocal getCrudMaterial() {
        return crudMaterial;
    }

    public void setCrudMaterial(CrudMaterialLocal crudMat) {
        this.crudMaterial = crudMat;
    }

    public MantenedorMaterialConversation getMantMaterial() {
        return mantMaterial;
    }

    public void setMantMaterial(MantenedorMaterialConversation mantMat) {
        this.mantMaterial = mantMat;
    }

    public String getNombreMaterialAntiguo() {
        return nombreMaterialAntiguo;
    }

    public void setNombreMaterialAntiguo(String nombreMatAntiguo) {
        this.nombreMaterialAntiguo = nombreMatAntiguo;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }
    
    @PostConstruct
    public void init(){
        if(mantMaterial.getIdMaterial()!=null){
            idMaterial = mantMaterial.getIdMaterial();
            cargarDatosEstado(idMaterial);
        } else {
            volverToLista();
        }
    }
    
    public void cargarDatosEstado(Integer idMaterialCargar){
        Material mat= crudMaterial.getMaterialByID(idMaterialCargar);
        if(mat != null){
            this.nombreMaterial = mat.getNombre_material();
            nombreMaterialAntiguo = nombreMaterial;
        } else {
            volverToLista();
        }
    }
    
    public void guardarDatosMaterial (){
        if(nombreMaterial.equals(nombreMaterialAntiguo)){
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, "Error: ese nombre ya está guardado.", "Ese nombre ya está guardado." );
        } else {
            mantMaterial.endConversation();
            try{
                crudMaterial.editarMaterial(idMaterial, nombreMaterialAntiguo, nombreMaterial);
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han guardado los datos del material.",
                    "Se han guardado los datos del material \"".concat(nombreMaterial).concat("\""));
                CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml?faces-redirect=true");
            } catch (Exception e){
                 CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                        e.getMessage(), 
                        e.getMessage());
                CommonFunctions.goToPage("/faces/users/admin/config/editarMaterial.xhtml?faces-redirect=true");
            }
        }
    }
    
    public void volverToLista(){
        mantMaterial.endConversation();
        CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
    }
    
    public MantenedorMaterialEditarMB() {
        
    }
}
