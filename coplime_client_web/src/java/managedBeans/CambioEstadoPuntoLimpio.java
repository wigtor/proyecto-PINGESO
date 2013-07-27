/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Estado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "CambioEstadoPuntoLimpio")
@RequestScoped
public class CambioEstadoPuntoLimpio extends commonFunctions {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private CambioEstadoPuntoLimpio_session cambioEstadoSessionBean;
    
    private List<SelectElemPojo> listaEstadosPtoLimpio;
    
    private Integer estadoPuntoLimpio;
    
    /**
     * Creates a new instance of CambioEstadoPuntoLimpio
     */
    public CambioEstadoPuntoLimpio() {
    }
    
    @PostConstruct
    public void init() {
        cargarEstadosPuntoLimpio();
        
        //La primera vez que se ejecuta se setea el id del estado global old
        if (this.cambioEstadoSessionBean == null)
            this.cambioEstadoSessionBean.setOld_idEstadoGlobal(estadoPuntoLimpio);
    }
    
    private void cargarEstadosPuntoLimpio() {
        Collection<Estado> listaTemp = crudPuntoLimpio.getAllEstadosPuntoLimpio();
        SelectElemPojo elemTemp;
        this.listaEstadosPtoLimpio = new ArrayList();
        for(Estado estado_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(estado_iter.getId()));
            elemTemp.setLabel(estado_iter.getNombreEstado());
            this.listaEstadosPtoLimpio.add(elemTemp);
        }
    }
    
    public void cambiarEstadoContenedor() {
        System.out.println("Se hizo click en 'cambiarEstadoContenedor()'");
        
        cambioEstadoSessionBean.setIdEstadoToChange(this.estadoPuntoLimpio);
        goToPage("/faces/users/cambiarEstadoContenedor.xhtml");
    }
    
    public void guardarCambios() {
        System.out.println("Se hizo click en 'guardarCambios()'");
        cambioEstadoSessionBean.setNvo_idEstadoGlobal(estadoPuntoLimpio);
        
        cambioEstadoSessionBean.setIdPuntoLimpioToChange(null);
        if (isUserInRole("Inspector"))
            goToPage("/faces/users/inspector/AgregarRevision.xhtml");
        if (isUserInRole("Operario"))
            goToPage("/faces/users/operario/AgregarMantención.xhtml");
    }
    
    public void volver() {
        System.out.println("Se hizo click en 'volver()'");
        cambioEstadoSessionBean.limpiarCampos();
        if (isUserInRole("Inspector"))
            goToPage("/faces/users/inspector/AgregarRevision.xhtml");
        if (isUserInRole("Operario"))
            goToPage("/faces/users/operario/AgregarMantención.xhtml");
    }

    public List<SelectElemPojo> getListaEstadosPtoLimpio() {
        return listaEstadosPtoLimpio;
    }

    public void setListaEstadosPtoLimpio(List<SelectElemPojo> listaEstadosPtoLimpio) {
        this.listaEstadosPtoLimpio = listaEstadosPtoLimpio;
    }

    public Integer getEstadoPuntoLimpio() {
        return estadoPuntoLimpio;
    }

    public void setEstadoPuntoLimpio(Integer estadoPuntoLimpio) {
        this.estadoPuntoLimpio = estadoPuntoLimpio;
    }
    
}
