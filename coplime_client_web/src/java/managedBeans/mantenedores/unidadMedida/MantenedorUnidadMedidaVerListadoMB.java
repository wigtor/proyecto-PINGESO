/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.unidadMedida;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Estado;
import entities.UnidadMedida;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import otros.CommonFunctions;
import sessionBeans.CrudEstadoPuntoLimpioLocal;
import sessionBeans.CrudUnidadMedidaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorUnidadMedidaVerListadoMB")
@RequestScoped
public class MantenedorUnidadMedidaVerListadoMB {
    @EJB
    private CrudUnidadMedidaLocal crudU;
    
    private List<SelectElemPojo> listaUnidadesPOJO;
    private List<SelectElemPojo> listaUnidadesPOJOBusq;

    public List<SelectElemPojo> getListaUnidadesPOJO() {
        return listaUnidadesPOJO;
    }

    public void setListaUnidadesPOJO(List<SelectElemPojo> listaUnidadPOJO) {
        this.listaUnidadesPOJO = listaUnidadPOJO;
    }
     

    /**
     * Creates a new instance of MantenedorEstadoVerListadoMB
     */
    public MantenedorUnidadMedidaVerListadoMB() {
        
    }
    
    @PostConstruct
    public void init(){
        Collection<UnidadMedida> listaTemp = crudU.getAllUnidadesMedida();
        SelectElemPojo uniTemp;
        this.listaUnidadesPOJO = new ArrayList<>();
        if (listaTemp == null){
            uniTemp = new SelectElemPojo("1","Vacío");
            this.listaUnidadesPOJO.add(uniTemp);
        } else {
            for(UnidadMedida est_iter: listaTemp){
                uniTemp = new SelectElemPojo();
                uniTemp.setId(est_iter.getId().toString());
                uniTemp.setLabel(est_iter.getNombreUnidad());
                listaUnidadesPOJO.add(uniTemp);
            }
        }
        
    }
    
     public void volver() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");        
    }
     
    public void aAgregarNuevaUnidad(){
       CommonFunctions.goToPage("/faces/users/admin/config/agregarUnidadMedida.xhtml");
    }

    public List<SelectElemPojo> getListaUnidadesPOJOBusq() {
        return listaUnidadesPOJOBusq;
    }

    public void setListaUnidadesPOJOBusq(List<SelectElemPojo> listaUnidadPOJOBusq) {
        this.listaUnidadesPOJOBusq = listaUnidadPOJOBusq;
    }
}
