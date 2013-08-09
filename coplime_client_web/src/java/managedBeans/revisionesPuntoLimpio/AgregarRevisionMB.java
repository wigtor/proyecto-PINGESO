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
    private CambioEstadoPuntoLimpioConversation cambioEstadoSessionBean;
    
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
    
    public void guardarRevision() {
         System.out.println("Se hizo click en 'guardarRevision()'");
         System.out.println("Detalle: " + detalle);
         System.out.println("Punto limpio a modificar: "+ numPtoLimpio);
         System.out.println("Estado global del punto limpio: " + cambioEstadoSessionBean.getIdEstadoToChange());
         System.out.println("Cantidad de contenedores modificados: " + cambioEstadoSessionBean.getListaContenedoresModificados().size());
         for(ContenedorPojo c : cambioEstadoSessionBean.getListaContenedoresModificados()) {
             System.out.println("Id del contenedor: "+c.getId());
             System.out.println("Id del estado del contenedor: "+c.getIdEstadoContenedor());
             System.out.println("Llenado del contenedor: "+c.getLlenadoContenedor());
         }
         //BORRAR LOS PRINTLN ANTERIORES
         
         
         HttpServletRequest request = (HttpServletRequest) 
                (FacesContext.getCurrentInstance().getExternalContext().getRequest());
         String usernameLogueado = request.getRemoteUser();
         
         //Envío al session bean los cambios para que se persistan a nivel de DB
         crudRevision.agregarRevision(numPtoLimpio, usernameLogueado, detalle, cambioEstadoSessionBean.getIdEstadoToChange());
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
