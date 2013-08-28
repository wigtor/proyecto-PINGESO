/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.revisionesPuntoLimpio;

import ObjectsForManagedBeans.ContenedorPojo;
import ObjectsForManagedBeans.SelectElemPojo;
import entities.PuntoLimpio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import managedBeans.cambioEstadoPuntoLimpio.CambioEstadoPuntoLimpioConversation;
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;
import sessionBeans.CrudRevisionPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "agregarRevisionMB")
@RequestScoped
public class AgregarRevisionMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @EJB
    private CrudRevisionPuntoLimpioLocal crudRevision;
    
    @Inject
    private CambioEstadoPuntoLimpioConversation cambioEstadoConvBean;
    
    private Integer numPtoLimpio;
    private List<SelectElemPojo> listaPuntosLimpios;
    private String detalle;
    
    /**
     * Creates a new instance of AgregarRevisionMB
     */
    public AgregarRevisionMB() {
    }
    
    @PostConstruct
    public void init() {
        this.listaPuntosLimpios = cargarPuntosLimpios();
        if (detalle == null) {
            detalle = cambioEstadoConvBean.getDetalle();
        }
        else if (detalle.trim().isEmpty()) {
            detalle = cambioEstadoConvBean.getDetalle();
        }
        
        if (this.cambioEstadoConvBean.getIdPuntoLimpioToChange()!= null) {
            this.numPtoLimpio = this.cambioEstadoConvBean.getIdPuntoLimpioToChange();
        }
        else {
            this.cambioEstadoConvBean.setIdPuntoLimpioToChange(this.numPtoLimpio);
        }
    }
    
    private List<SelectElemPojo> cargarPuntosLimpios(){
        Collection<PuntoLimpio> listaTemp = crudPuntoLimpio.getAllPuntosLimpios();
        SelectElemPojo ptoTemporal;
        List<SelectElemPojo> listaResult = new ArrayList<>();
        for(PuntoLimpio pto_iter : listaTemp) {
            ptoTemporal = new SelectElemPojo();
            
            ptoTemporal.setId(pto_iter.getId().toString());
            ptoTemporal.setLabel(pto_iter.getNombre());
            listaResult.add(ptoTemporal);
        }
        return listaResult;
    }
    
    public void cambiarEstadoPtoLimpio() {
        //Almaceno en el managed bean session el punto limpio que se está editando
        cambioEstadoConvBean.beginConversation();
        cambioEstadoConvBean.setDetalle(detalle);
        cambioEstadoConvBean.setIdPuntoLimpioToChange(numPtoLimpio);
        
        CommonFunctions.goToPage("/faces/users/cambiarEstadoPuntoLimpio.xhtml?cid=".concat(this.cambioEstadoConvBean.getConversation().getId()));
    }
    
    public void guardarRevision() {
        try {
            String usernameLogueado = CommonFunctions.getUsuarioLogueado();

            //Envío al session bean los cambios para que se persistan a nivel de DB
            crudRevision.agregarRevision(numPtoLimpio, usernameLogueado, detalle, cambioEstadoConvBean.getIdEstadoToChange());
            for (ContenedorPojo c : cambioEstadoConvBean.getListaContenedoresModificados()) {
                crudPuntoLimpio.cambiarEstadoContenedor(c.getId(), c.getIdEstadoContenedor(), c.getLlenadoContenedor());
            }
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha agregado la revisión del punto limpio",
                    "Se ha agregado la revisión del punto limpio N°".concat(numPtoLimpio.toString()));
            volverToLista();
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            CommonFunctions.goToPage("/faces/users/inspector/agregarRevisionPuntoLimpio.xhtml?faces-redirect=true");
        }
    }

    public void volverToLista() {
        cambioEstadoConvBean.limpiarCampos();
        cambioEstadoConvBean.endConversation();
        CommonFunctions.goToPage("/faces/users/verRevisiones.xhtml?faces-redirect=true");
    }

    public Integer getNumPtoLimpio() {
        return numPtoLimpio;
    }

    public void setNumPtoLimpio(Integer numPtoLimpio) {
        this.numPtoLimpio = numPtoLimpio;
    }

    public List<SelectElemPojo> getListaPuntosLimpios() {
        return listaPuntosLimpios;
    }

    public void setListaPuntosLimpios(List<SelectElemPojo> listaPuntosLimpios) {
        this.listaPuntosLimpios = listaPuntosLimpios;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
}
