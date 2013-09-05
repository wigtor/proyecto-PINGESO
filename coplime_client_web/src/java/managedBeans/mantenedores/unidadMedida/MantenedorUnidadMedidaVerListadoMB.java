/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.unidadMedida;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.UnidadMedida;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
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
    
    @Inject
    private MantenedorUnidadMedidaConversation mantUni;
    
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

    public void editar(Integer idUnidad) {
        UnidadMedida unidadEdit = crudU.getUnidadByID(idUnidad);
        if (unidadEdit != null) {
            this.mantUni.beginConversation();
            this.mantUni.setIdUnidadMedida(idUnidad);
            CommonFunctions.goToPage("/faces/users/admin/config/editarUnidadMedida.xhtml?cid=".concat(this.mantUni.getConversation().getId()));
        } else {
            //MOSTRAR ERROR
            this.mantUni.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
        }
    }
    
    public void eliminar(Integer idUnidad) {
        try {
            boolean resultado = crudU.eliminarUnidadMedida(idUnidad);
            if (resultado) {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                        "Se ha eliminado la unidad de medida",
                        "Se ha eliminado correctamente la unidad de medida");
            } else {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al eliminar la unidad de medida",
                        "Error, no se ha podido eliminar la unidad de medida seleccionada");
            }
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "No ha sido posible eliminar la unidad de medida, quizá se encuentra utilizada",
                        "No ha sido posible eliminar la unidad de medida, quizá se encuentra utilizada por algún contenedor");
        }
        CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml?faces-redirect=true");
    }
    
    public List<SelectElemPojo> getListaUnidadesPOJOBusq() {
        return listaUnidadesPOJOBusq;
    }

    public void setListaUnidadesPOJOBusq(List<SelectElemPojo> listaUnidadPOJOBusq) {
        this.listaUnidadesPOJOBusq = listaUnidadPOJOBusq;
    }
}
