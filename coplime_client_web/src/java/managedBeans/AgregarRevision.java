/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.ContenedorPojo;
import ObjectsForManagedBeans.PuntoLimpioPojo;
import ObjectsForManagedBeans.SelectElemPojo;
import entities.PuntoLimpio;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "AgregarRevision")
@RequestScoped
public class AgregarRevision extends commonFunctions {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private CambioEstadoPuntoLimpio_session cambioEstadoSessionBean;
    
    private Integer numPtoLimpio;
    private List<SelectElemPojo> listaPuntosLimpios;
    private String detalle;
    
    /**
     * Creates a new instance of AgregarRevision
     */
    public AgregarRevision() {
    }
    
    @PostConstruct
    public void init() {
        this.listaPuntosLimpios = cargarPuntosLimpios();
        detalle = cambioEstadoSessionBean.getDetalle();
        
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
        
        goToPage("/faces/users/cambiarEstadoPuntoLimpio.xhtml");
    }
    
    public void guardarRevision() {
         System.out.println("Se hizo click en 'guardarRevision()'");
         
         
         System.out.println("Detalle: " + detalle);
         System.out.println("Punto limpio a modificar: "+ numPtoLimpio);
         System.out.println("Estado global old del punto limpio: " + cambioEstadoSessionBean.getOld_idEstadoGlobal());
         System.out.println("Estado global nvo del punto limpio: " + cambioEstadoSessionBean.getNvo_idEstadoGlobal());
         System.out.println("Cantidad de contenedores modificados: " + cambioEstadoSessionBean.getListaContenedoresModificados().size());
         for(ContenedorPojo c : cambioEstadoSessionBean.getListaContenedoresModificados()) {
             System.out.println("Id del contenedor: "+c.getId());
             System.out.println("Id del estado del contenedor: "+c.getIdEstadoContenedor());
             System.out.println("Llenado del contenedor: "+c.getLlenadoContenedor());
         }
         HttpServletRequest request = (HttpServletRequest) 
                (FacesContext.getCurrentInstance().getExternalContext().getRequest());
         String usernameLogueado = request.getRemoteUser();
         
         //Envío al session bean los cambios para que se persistan a nivel de DB
         crudPuntoLimpio.agregarRevision(numPtoLimpio, usernameLogueado, detalle, cambioEstadoSessionBean.getNvo_idEstadoGlobal());
         for(ContenedorPojo c : cambioEstadoSessionBean.getListaContenedoresModificados()) {
             crudPuntoLimpio.cambiarEstadoContenedor(c.getId(), c.getIdEstadoContenedor(), c.getLlenadoContenedor());
         }
         
         volverToLista();
    }
    
    public void volverToLista() {
        System.out.println("Se hizo click en 'volverToLista()'");
        cambioEstadoSessionBean.limpiarCampos();
        goToPage("/faces/users/verPuntosLimpios.xhtml");
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
