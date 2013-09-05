/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.material;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Material;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import sessionBeans.CrudMaterialLocal;
import otros.CommonFunctions;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorMaterialVerListadoMB")
@RequestScoped
public class MantenedorMaterialVerListadoMB {
    @EJB
    private CrudMaterialLocal crudMat;
    
    @Inject
    private MantenedorMaterialConversation mantMat;
    
    private List<SelectElemPojo> listaMatPOJO;
    private List<SelectElemPojo> listaMatPOJOBusq;

    public CrudMaterialLocal getCrudMat() {
        return crudMat;
    }

    public void setCrudMat(CrudMaterialLocal crudMat) {
        this.crudMat = crudMat;
    }

    public List<SelectElemPojo> getListaMatPOJO() {
        return listaMatPOJO;
    }

    public void setListaMatPOJO(List<SelectElemPojo> listaMatPOJO) {
        this.listaMatPOJO = listaMatPOJO;
    }
     

    /**
     * Creates a new instance of MantenedorMaterialVerListadoMB
     */
    public MantenedorMaterialVerListadoMB() {
        
    }
    
    @PostConstruct
    public void init(){
        Collection<Material> listaTemp = crudMat.getAllMateriales();
        SelectElemPojo matTemp;
        this.listaMatPOJO = new ArrayList<>();
        if (listaTemp == null){
            matTemp = new SelectElemPojo("1","Latas");
            this.listaMatPOJO.add(matTemp);
        } else {
            for(Material mat_iter: listaTemp){
                matTemp = new SelectElemPojo();
                matTemp.setId(mat_iter.getId().toString());
                matTemp.setLabel(mat_iter.getNombre_material());
                listaMatPOJO.add(matTemp);
            }
        }
        
    }
    
     public void volver() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        
    }
     
    public void aAgregarNuevoMat(){
       CommonFunctions.goToPage("/faces/users/admin/config/agregarMaterial.xhtml");
    }

    public void editar(Integer idMaterial) {
        Material materialEdit = crudMat.getMaterialByID(idMaterial);
        if (materialEdit != null) {
            this.mantMat.beginConversation();
            this.mantMat.setIdMaterial(idMaterial);
            CommonFunctions.goToPage("/faces/users/admin/config/editarMaterial.xhtml?cid=".concat(this.mantMat.getConversation().getId()));
        } else {
            //MOSTRAR ERROR
            this.mantMat.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
        }
    }
    
    public void eliminar(Integer idMaterial) {
        System.out.println("Se quiere eliminar el material: "+idMaterial);
        try {
            boolean resultado = crudMat.eliminarMaterial(idMaterial);
            if (resultado) {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                        "Se ha eliminado el material",
                        "Se ha eliminado correctamente el material");
            } else {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al eliminar el material",
                        "Error, no se ha podido eliminar el material seleccionado");
            }
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "No ha sido posible eliminar el material, quizá se encuentra utilizado",
                        "No ha sido posible eliminar el material, quizá se encuentra utilizado por algún contenedor");
        }
        CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml?faces-redirect=true");
    }
    
    public List<SelectElemPojo> getListaMatPOJOBusq() {
        return listaMatPOJOBusq;
    }

    public void setListaMatPOJOBusq(List<SelectElemPojo> listaMatPOJOBusq) {
        this.listaMatPOJOBusq = listaMatPOJOBusq;
    }
     
     
}
