/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.cambioEstadoPuntoLimpio;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Contenedor;
import entities.Estado;
import entities.PuntoLimpio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "CambioEstadoPuntoLimpio")
@RequestScoped
public class CambioEstadoPuntoLimpio {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private CambioEstadoPuntoLimpio_session cambioEstadoSessionBean;
    
    private Integer idPtoLimpio;
    
    private String nombrePtoLimpio;
    
    private List<SelectElemPojo> listaEstadosPtoLimpio;
    
    private Integer estadoPuntoLimpio;
    
    private Integer idContenedorSeleccionado;
    
    private List<SelectElemPojo> listaContenedores;
    
    /**
     * Creates a new instance of CambioEstadoPuntoLimpio
     */
    public CambioEstadoPuntoLimpio() {
    }
    
    @PostConstruct
    public void init() {
        this.idPtoLimpio = cambioEstadoSessionBean.getIdPuntoLimpioToChange();
        cargarDatosPtoLimpio();
        cargarEstadosPuntoLimpio();
        cargarContenedoresPuntoLimpio();
        //this.estadoPuntoLimpio = 
        //La primera vez que se ejecuta se setea el id del estado global old
        if (this.cambioEstadoSessionBean.getOld_idEstadoGlobal() == null)
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
    
    private void cargarContenedoresPuntoLimpio() {
        List<Contenedor> listaTemp = crudPuntoLimpio.getContenedoresByPuntoLimpio(this.idPtoLimpio);
        SelectElemPojo elemTemp;
        this.listaContenedores = new ArrayList();
        for(Contenedor cont : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(cont.getId()));
            elemTemp.setLabel(cont.getId() +" - "+ cont.getMaterialDeAcopio().getNombre_material());
            this.listaContenedores.add(elemTemp);
        }
        
    }
    
    private void cargarDatosPtoLimpio() {
        PuntoLimpio p = crudPuntoLimpio.getPuntoLimpioByNum(this.idPtoLimpio);
        this.nombrePtoLimpio = p.getId()+" - "+p.getNombre();
    }
    
    public void cambiarEstadoContenedor() {
        System.out.println("Se hizo click en 'cambiarEstadoContenedor()'");
        
        cambioEstadoSessionBean.setIdEstadoToChange(this.estadoPuntoLimpio);
        cambioEstadoSessionBean.setIdContenedorToChange(idContenedorSeleccionado);
        CommonFunctions.goToPage("/faces/users/cambiarEstadoContenedor.xhtml");
    }
    
    public void guardarCambios() {
        System.out.println("Se hizo click en 'guardarCambios()'");
        cambioEstadoSessionBean.setNvo_idEstadoGlobal(estadoPuntoLimpio);
        
        cambioEstadoSessionBean.setIdPuntoLimpioToChange(null);
        if (CommonFunctions.isUserInRole("Inspector"))
            CommonFunctions.goToPage("/faces/users/inspector/agregarRevisionPuntoLimpio.xhtml");
        if (CommonFunctions.isUserInRole("Operario"))
            CommonFunctions.goToPage("/faces/users/operario/agregarMantenciónPuntoLimpio.xhtml");
    }
    
    public void volver() {
        System.out.println("Se hizo click en 'volver()'");
        cambioEstadoSessionBean.limpiarCampos();
        if (CommonFunctions.isUserInRole("Inspector"))
            CommonFunctions.goToPage("/faces/users/inspector/agregarRevisionPuntoLimpio.xhtml");
        if (CommonFunctions.isUserInRole("Operario"))
            CommonFunctions.goToPage("/faces/users/operario/agregarMantenciónPuntoLimpio.xhtml");
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

    public Integer getIdContenedorSeleccionado() {
        return idContenedorSeleccionado;
    }

    public void setIdContenedorSeleccionado(Integer idContenedorSeleccionado) {
        this.idContenedorSeleccionado = idContenedorSeleccionado;
    }

    public List<SelectElemPojo> getListaContenedores() {
        return listaContenedores;
    }

    public void setListaContenedores(List<SelectElemPojo> listaContenedores) {
        this.listaContenedores = listaContenedores;
    }
    
}
