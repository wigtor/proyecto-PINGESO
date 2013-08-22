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
@Named(value = "cambioEstadoPuntoLimpioMB")
@RequestScoped
public class CambioEstadoPuntoLimpioMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private CambioEstadoPuntoLimpioConversation cambioEstadoConvBean;
    
    private Integer idPtoLimpio;
    
    private String nombrePtoLimpio;
    
    private List<SelectElemPojo> listaEstadosPtoLimpio;
    
    private Integer estadoPuntoLimpio;
    
    private Integer idContenedorSeleccionado;
    
    private List<SelectElemPojo> listaContenedores;
    
    /**
     * Creates a new instance of CambioEstadoPuntoLimpioMB
     */
    public CambioEstadoPuntoLimpioMB() {
    }
    
    @PostConstruct
    public void init() {
        this.idPtoLimpio = cambioEstadoConvBean.getIdPuntoLimpioToChange();
        cargarDatosPtoLimpio();
        cargarEstadosPuntoLimpio();
        cargarContenedoresPuntoLimpio();
        //this.estadoPuntoLimpio = 
        //La primera vez que se ejecuta se setea el id del estado global
        if (this.cambioEstadoConvBean.getIdEstadoToChange()!= null)
            this.estadoPuntoLimpio = this.cambioEstadoConvBean.getIdEstadoToChange();
        else 
            this.cambioEstadoConvBean.setIdEstadoToChange(this.estadoPuntoLimpio);
    }
    
    private void cargarEstadosPuntoLimpio() {
        Collection<Estado> listaTemp = crudPuntoLimpio.getAllEstadosPuntoLimpio();
        SelectElemPojo elemTemp;
        this.listaEstadosPtoLimpio = new ArrayList<>();
        for(Estado estado_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(estado_iter.getId()));
            elemTemp.setLabel(estado_iter.getNombreEstado());
            this.listaEstadosPtoLimpio.add(elemTemp);
        }
    }
    
    private void cargarContenedoresPuntoLimpio() {
        Collection<Contenedor> listaTemp = crudPuntoLimpio.getContenedoresByPuntoLimpio(this.idPtoLimpio);
        SelectElemPojo elemTemp;
        this.listaContenedores = new ArrayList<>();
        for(Contenedor cont : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(cont.getId()));
            elemTemp.setLabel(Integer.toString(cont.getId()).concat(" - ").concat(cont.getMaterialDeAcopio().getNombre_material()));
            this.listaContenedores.add(elemTemp);
        }
        
    }
    
    private void cargarDatosPtoLimpio() {
        PuntoLimpio p = crudPuntoLimpio.getPuntoLimpioByNum(this.idPtoLimpio);
        this.nombrePtoLimpio = Integer.toString(p.getId()).concat(" - ").concat(p.getNombre());
        this.estadoPuntoLimpio = p.getEstadoGlobal().getId();
    }
    
    public void cambiarEstadoContenedor() {
        cambioEstadoConvBean.setIdEstadoToChange(this.estadoPuntoLimpio);
        cambioEstadoConvBean.setIdContenedorToChange(idContenedorSeleccionado);
        CommonFunctions.goToPage("/faces/users/cambiarEstadoContenedor.xhtml?cid=".concat(this.cambioEstadoConvBean.getConversation().getId()));
    }
    
    public void guardarCambios() {
        cambioEstadoConvBean.setIdEstadoToChange(this.estadoPuntoLimpio);
        
        //cambioEstadoConvBean.setIdPuntoLimpioToChange(null);
        if (CommonFunctions.isUserInRole("Inspector"))
            CommonFunctions.goToPage("/faces/users/inspector/agregarRevisionPuntoLimpio.xhtml?cid=".concat(this.cambioEstadoConvBean.getConversation().getId()));
        if (CommonFunctions.isUserInRole("Operario"))
            CommonFunctions.goToPage("/faces/users/operario/agregarMantencionPuntoLimpio.xhtml?cid=".concat(this.cambioEstadoConvBean.getConversation().getId()));
    }
    
    public void volver() {
        cambioEstadoConvBean.limpiarCampos();
        if (CommonFunctions.isUserInRole("Inspector"))
            CommonFunctions.goToPage("/faces/users/inspector/agregarRevisionPuntoLimpio.xhtml?cid=".concat(this.cambioEstadoConvBean.getConversation().getId()));
        if (CommonFunctions.isUserInRole("Operario"))
            CommonFunctions.goToPage("/faces/users/operario/agregarMantencionPuntoLimpio.xhtml?cid=".concat(this.cambioEstadoConvBean.getConversation().getId()));
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

    public Integer getIdPtoLimpio() {
        return idPtoLimpio;
    }

    public void setIdPtoLimpio(Integer idPtoLimpio) {
        this.idPtoLimpio = idPtoLimpio;
    }

    public String getNombrePtoLimpio() {
        return nombrePtoLimpio;
    }

    public void setNombrePtoLimpio(String nombrePtoLimpio) {
        this.nombrePtoLimpio = nombrePtoLimpio;
    }
}
