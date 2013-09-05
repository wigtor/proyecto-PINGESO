package managedBeans.mantenedores.comuna;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Comuna;
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
import sessionBeans.CrudComunaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorComunaVerListadoMB")
@RequestScoped
public class MantenedorComunaVerListadoMB {
    @EJB
    private CrudComunaLocal crudC;
    
    @Inject
    private MantenedorComunaConversation mantCom;
    
    private List<SelectElemPojo> listaComunasPOJO;
    private List<SelectElemPojo> listaComunasPOJOBusq;

    public List<SelectElemPojo> getListaComunasPOJO() {
        return listaComunasPOJO;
    }

    public void setListaComunasPOJO(List<SelectElemPojo> listaComunaPOJO) {
        this.listaComunasPOJO = listaComunaPOJO;
    }
     

    /**
     * Creates a new instance of MantenedorEstadoVerListadoMB
     */
    public MantenedorComunaVerListadoMB() {
        
    }
    
    @PostConstruct
    public void init(){
        Collection<Comuna> listaTemp = crudC.getAllComunas();
        SelectElemPojo comTemp;
        this.listaComunasPOJO = new ArrayList<>();
        if (listaTemp == null){
            comTemp = new SelectElemPojo("1","Vacío");
            this.listaComunasPOJO.add(comTemp);
        } else {
            for(Comuna com_iter: listaTemp){
                comTemp = new SelectElemPojo();
                comTemp.setId(com_iter.getId().toString());
                comTemp.setLabel(com_iter.getNombre());
                listaComunasPOJO.add(comTemp);
            }
        }
        
    }
    
     public void volver() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");        
    }
     
    public void aAgregarNuevaComuna(){
       CommonFunctions.goToPage("/faces/users/admin/config/agregarComuna.xhtml");
    }

    public void editar(Integer idComuna) {
        Comuna comunaEdit = crudC.getComunaByID(idComuna);
        if (comunaEdit != null) {
            this.mantCom.beginConversation();
            this.mantCom.setIdComuna(idComuna);
            CommonFunctions.goToPage("/faces/users/admin/config/editarComuna.xhtml?cid=".concat(this.mantCom.getConversation().getId()));
        } else {
            //MOSTRAR ERROR
            this.mantCom.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
        }
    }
    
    public void eliminar(Integer idComuna) {
        try {
            boolean resultado = crudC.eliminarComuna(idComuna);
            if (resultado) {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                        "Se ha eliminado la comuna",
                        "Se ha eliminado correctamente la comuna");
            } else {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al eliminar la comuna",
                        "Error, no se ha podido eliminar la comuna seleccionada");
            }
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        e.getMessage(),
                        e.getMessage());
        }
        CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml?faces-redirect=true");
    }
    
    public List<SelectElemPojo> getListaComunasPOJOBusq() {
        return listaComunasPOJOBusq;
    }

    public void setListaComunasPOJOBusq(List<SelectElemPojo> listaComunaPOJOBusq) {
        this.listaComunasPOJOBusq = listaComunaPOJOBusq;
    }
}
