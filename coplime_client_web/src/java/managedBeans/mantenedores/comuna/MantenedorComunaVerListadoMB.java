package managedBeans.mantenedores.comuna;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Comuna;
import entities.Estado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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

    public List<SelectElemPojo> getListaComunasPOJOBusq() {
        return listaComunasPOJOBusq;
    }

    public void setListaComunasPOJOBusq(List<SelectElemPojo> listaComunaPOJOBusq) {
        this.listaComunasPOJOBusq = listaComunaPOJOBusq;
    }
}
