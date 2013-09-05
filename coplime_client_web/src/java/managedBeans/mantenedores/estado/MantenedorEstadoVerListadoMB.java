/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.estado;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Estado;
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
import sessionBeans.CrudEstadoPuntoLimpioLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorEstadoVerListadoMB")
@RequestScoped
public class MantenedorEstadoVerListadoMB {
    @EJB
    private CrudEstadoPuntoLimpioLocal crudE;
    
    @Inject
    private MantenedorEstadoConversation mantEst;
    
    
    private List<SelectElemPojo> listaEstadosPOJO;
    private List<SelectElemPojo> listaEstadosPOJOBusq;

    public List<SelectElemPojo> getListaEstadosPOJO() {
        return listaEstadosPOJO;
    }

    public void setListaEstadosPOJO(List<SelectElemPojo> listaEstadoPOJO) {
        this.listaEstadosPOJO = listaEstadoPOJO;
    }
     

    /**
     * Creates a new instance of MantenedorEstadoVerListadoMB
     */
    public MantenedorEstadoVerListadoMB() {
        
    }
    
    @PostConstruct
    public void init(){
        Collection<Estado> listaTemp = crudE.getAllEstadosPuntoLimpio();
        SelectElemPojo estTemp;
        this.listaEstadosPOJO = new ArrayList<>();
        if (listaTemp == null){
            estTemp = new SelectElemPojo("1","Vacío");
            this.listaEstadosPOJO.add(estTemp);
        } else {
            for(Estado est_iter: listaTemp){
                estTemp = new SelectElemPojo();
                estTemp.setId(est_iter.getId().toString());
                estTemp.setLabel(est_iter.getNombreEstado());
                listaEstadosPOJO.add(estTemp);
            }
        }
        
    }
    
     public void volver() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");        
    }
     
    public void aAgregarNuevoEstado(){
       CommonFunctions.goToPage("/faces/users/admin/config/agregarEstado.xhtml");
    }

    public void editar(Integer idEstado) {
        Estado estadoEdit = crudE.getEstadoPuntoLimpioPorID(idEstado);
        if (estadoEdit != null) {
            this.mantEst.beginConversation();
            this.mantEst.setIdEstado(idEstado);
            CommonFunctions.goToPage("/faces/users/admin/config/editarEstado.xhtml?cid=".concat(this.mantEst.getConversation().getId()));
        } else {
            //MOSTRAR ERROR
            this.mantEst.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml");
        }
    }
    
    public void eliminar(Integer idEstado) {
        try {
            boolean resultado = crudE.eliminarEstadoPuntoLimpio(idEstado);
            if (resultado) {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                        "Se ha eliminado el estado",
                        "Se ha eliminado correctamente el estado");
            } else {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al eliminar el estado",
                        "Error, no se ha podido eliminar el estado seleccionado");
            }
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "No ha sido posible eliminar el estado, quizá se encuentra utilizado",
                        "No ha sido posible eliminar el estado, quizá se encuentra utilizado por algún punto limpio o contenedor");
        }
        CommonFunctions.goToPage("/faces/users/admin/config/configuracionSistema.xhtml?faces-redirect=true");
    }
    
    public List<SelectElemPojo> getListaEstadosPOJOBusq() {
        return listaEstadosPOJOBusq;
    }

    public void setListaEstadosPOJOBusq(List<SelectElemPojo> listaMatPOJOBusq) {
        this.listaEstadosPOJOBusq = listaMatPOJOBusq;
    }
}
