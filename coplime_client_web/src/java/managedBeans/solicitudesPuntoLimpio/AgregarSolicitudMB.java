/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantencionesPuntoLimpio;

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
import javax.inject.Inject;
import managedBeans.cambioEstadoPuntoLimpio.CambioEstadoPuntoLimpioConversation;
import otros.CommonFunctions;
import sessionBeans.CrudMantencionPuntoLimpioLocal;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "agregarSolicitudMB")
@RequestScoped
public class AgregarSolicitudMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @EJB
    private CrudMantencionPuntoLimpioLocal crudMantencion;
    
    @Inject
    private CambioEstadoPuntoLimpioConversation cambioEstadoSessionBean;
    
    private Integer numPtoLimpio;
    private List<SelectElemPojo> listaPuntosLimpios;
    private String detalle;
    
    /**
     * Creates a new instance of AgregarSolicitudMB
     */
    public AgregarSolicitudMB() {
    }
    
    @PostConstruct
    public void init() {
        this.listaPuntosLimpios = cargarPuntosLimpios();
        if (detalle == null) {
            detalle = cambioEstadoSessionBean.getDetalle();
        }
        else if (detalle.trim().isEmpty()) {
            detalle = cambioEstadoSessionBean.getDetalle();
        }
        
        if (this.cambioEstadoSessionBean.getIdPuntoLimpioToChange()!= null) {
            this.numPtoLimpio = this.cambioEstadoSessionBean.getIdPuntoLimpioToChange();
        }
        else {
            this.cambioEstadoSessionBean.setIdPuntoLimpioToChange(this.numPtoLimpio);
        }
    }
    
    private List<SelectElemPojo> cargarPuntosLimpios(){
        Collection<PuntoLimpio> listaTemp = crudPuntoLimpio.getAllPuntosLimpios();
        SelectElemPojo ptoTemporal;
        List<SelectElemPojo> listaResult = new ArrayList();
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
        cambioEstadoSessionBean.setDetalle(detalle);
        cambioEstadoSessionBean.setIdPuntoLimpioToChange(numPtoLimpio);
        
        CommonFunctions.goToPage("/faces/users/cambiarEstadoPuntoLimpio.xhtml");
    }
    
    public void guardarMantencion() {
         System.out.println("Se hizo click en 'guardarRevision()'");
         
         String usernameLogueado = CommonFunctions.getUsuarioLogueado();
         
         //Envío al session bean los cambios para que se persistan a nivel de DB
         crudMantencion.agregarMantencion(numPtoLimpio, usernameLogueado, detalle, cambioEstadoSessionBean.getIdEstadoToChange());
         for(ContenedorPojo c : cambioEstadoSessionBean.getListaContenedoresModificados()) {
             crudPuntoLimpio.cambiarEstadoContenedor(c.getId(), c.getIdEstadoContenedor(), c.getLlenadoContenedor());
         }
         
         volverToLista();
    }
    
    public void volverToLista() {
        cambioEstadoSessionBean.limpiarCampos();
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
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
