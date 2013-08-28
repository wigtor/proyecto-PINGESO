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

    public List<SelectElemPojo> getListaMatPOJOBusq() {
        return listaMatPOJOBusq;
    }

    public void setListaMatPOJOBusq(List<SelectElemPojo> listaMatPOJOBusq) {
        this.listaMatPOJOBusq = listaMatPOJOBusq;
    }
     
     
}
