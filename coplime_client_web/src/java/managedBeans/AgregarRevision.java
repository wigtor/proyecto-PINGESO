/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

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
import javax.inject.Inject;
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
    
    @Inject private AgregarRevisionSession agregarRevision_session;
    
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
        
        
        goToPage("/faces/users/cambiarEstadoPuntoLimpio.xhtml");
    }
    
    public void guardarMantencion() {
         System.out.println("Se hizo click en 'guardarMantencion()'");
    }
    
    public void volverToLista() {
        System.out.println("Se hizo click en 'volverToLista()'");
        agregarRevision_session.setPuntoLimpioToChange(null);
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
