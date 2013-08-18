/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.solicitudesPuntoLimpio;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import otros.CommonFunctions;
import sessionBeans.CrudOperarioLocal;
import sessionBeans.CrudPuntoLimpioLocal;
import sessionBeans.CrudSolicitudMantencionLocal;

/**
 *
 * @author victor
 */
@Named(value = "agregarSolicitudMB")
@RequestScoped
public class AgregarSolicitudMB {
    @EJB
    private CrudSolicitudMantencionLocal crudSolicitud;
    
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @EJB
    private CrudOperarioLocal crudOperario;
    
    private Integer numPtoLimpio;
    private Integer numOperario;
    private List<SelectElemPojo> listaPuntosLimpios;
    private List<SelectElemPojo> listaOperarios;
    private String detalle;
    
    /**
     * Creates a new instance of AgregarSolicitudMB
     */
    public AgregarSolicitudMB() {
    }
    
    @PostConstruct
    public void init() {
        cargarPuntosLimpios();
        cargarOperarios();
    }
    
    private void cargarPuntosLimpios(){
        Collection<PuntoLimpio> listaTemp = crudPuntoLimpio.getAllPuntosLimpios();
        SelectElemPojo ptoTemporal;
        List<SelectElemPojo> listaResult = new ArrayList<>();
        for(PuntoLimpio pto_iter : listaTemp) {
            ptoTemporal = new SelectElemPojo();
            
            ptoTemporal.setId(pto_iter.getId().toString());
            ptoTemporal.setLabel(pto_iter.getNombre());
            listaResult.add(ptoTemporal);
        }
        this.listaPuntosLimpios = listaResult;
    }
    
    private void cargarOperarios(){
        Collection<OperarioMantencion> listaTemp = crudOperario.getAllOperarios();
        SelectElemPojo ptoTemporal;
        List<SelectElemPojo> listaResult = new ArrayList<>();
        for(OperarioMantencion elem_iter : listaTemp) {
            ptoTemporal = new SelectElemPojo();
            
            ptoTemporal.setId(elem_iter.getUsuario().getRut().toString());
            ptoTemporal.setLabel(elem_iter.getUsuario().getNombre().concat(" ")
                    .concat(elem_iter.getUsuario().getApellido1()));
            listaResult.add(ptoTemporal);
        }
        this.listaOperarios = listaResult;
    }
    
    public void guardarSolicitud() {
         System.out.println("Se hizo click en 'guardarSolicitud()'");
         
         String usernameLogueado = CommonFunctions.getUsuarioLogueado();
         
         //Envío al session bean los cambios para que se persistan a nivel de DB
         crudSolicitud.agregarSolicitudMantencion(numPtoLimpio, usernameLogueado, numOperario, detalle);
         
         volverToLista();
    }
    
    public void volverToLista() {
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

    public Integer getNumOperario() {
        return numOperario;
    }

    public void setNumOperario(Integer numOperario) {
        this.numOperario = numOperario;
    }

    public List<SelectElemPojo> getListaOperarios() {
        return listaOperarios;
    }

    public void setListaOperarios(List<SelectElemPojo> listaOperarios) {
        this.listaOperarios = listaOperarios;
    }
    
}
