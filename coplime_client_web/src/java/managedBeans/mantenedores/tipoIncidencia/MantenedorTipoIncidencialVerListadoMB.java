/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.tipoIncidencia;

import ObjectsForManagedBeans.TipoIncidenciaPojo;
import entities.TipoIncidencia;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import otros.CommonFunctions;
import sessionBeans.CrudTipoIncidenciaLocal;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorTipoIncidenciaVerListadoMB")
@RequestScoped
public class MantenedorTipoIncidencialVerListadoMB {
    @EJB
    private CrudTipoIncidenciaLocal crudTipoInc;
    
    private List<TipoIncidenciaPojo> listaTipoIncPOJO;
    private List<TipoIncidenciaPojo> listaTipoIncPOJOBusq;

    public List<TipoIncidenciaPojo> getListaMatPOJO() {
        return listaTipoIncPOJO;
    }

    public void setListaTipoIncPOJO(List<TipoIncidenciaPojo> listaTipoIncidPOJO) {
        this.listaTipoIncPOJO = listaTipoIncidPOJO;
    }
     

    /**
     * Creates a new instance of MantenedorTipoIncidencialVerListadoMB
     */
    public MantenedorTipoIncidencialVerListadoMB() {
        
    }
    
    @PostConstruct
    public void init(){
        Collection<TipoIncidencia> listaTemp = crudTipoInc.getAllTiposIncidencia();
        TipoIncidenciaPojo tipoIncTemp;
        this.listaTipoIncPOJO = new ArrayList<>();
        if (listaTemp == null){
            tipoIncTemp = new TipoIncidenciaPojo("Contenedor lleno",true);
            this.listaTipoIncPOJO.add(tipoIncTemp);
        } else {
            for(TipoIncidencia tipoInc_iter: listaTemp){
                tipoIncTemp = new TipoIncidenciaPojo();
                tipoIncTemp.setId(tipoInc_iter.getId().toString());
                tipoIncTemp.setNombreTipoIncidencia(tipoInc_iter.getNombreIncidencia());
                tipoIncTemp.setVisibleAlUsuario(tipoInc_iter.isVisibleAlUsuario());
                listaTipoIncPOJO.add(tipoIncTemp);
            }
        }
        
    }
    
     public void volver() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        
    }
     
    public void aAgregarNuevoTipoInc(){
       CommonFunctions.goToPage("/faces/users/admin/config/agregarTipoIncidencia.xhtml");
    }

    public List<TipoIncidenciaPojo> getListaMatPOJOBusq() {
        return listaTipoIncPOJOBusq;
    }

    public void setListaMatPOJOBusq(List<TipoIncidenciaPojo> listaMatPOJOBusq) {
        this.listaTipoIncPOJOBusq = listaMatPOJOBusq;
    }
     
     
}
