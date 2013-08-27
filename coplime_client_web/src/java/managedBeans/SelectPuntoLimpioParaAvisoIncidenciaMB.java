/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.PuntoLimpio;
import java.util.Collection;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "selectPuntoLimpioParaAvisoIncidenciaMB")
@RequestScoped
public class SelectPuntoLimpioParaAvisoIncidenciaMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    private Integer numPuntoLimpio;
    private Collection<SelectElemPojo> listaPuntosLimpios;
    
    @PostConstruct
    public void init() {
        cargarPuntosLimpios();
    }
    
    private void cargarPuntosLimpios() {
        Collection<PuntoLimpio> listaTemp = crudPuntoLimpio.getAllPuntosLimpios();
        this.listaPuntosLimpios = new LinkedList<>();
        SelectElemPojo elemTemp;
        for(PuntoLimpio temp : listaTemp) {
            elemTemp = new SelectElemPojo();
            
            elemTemp.setId(temp.getId().toString());
            elemTemp.setLabel("N°".concat(temp.getId().toString()).concat(" - ").concat(temp.getUbicacion()));
            this.listaPuntosLimpios.add(elemTemp);
        }
    }
    
    
    public SelectPuntoLimpioParaAvisoIncidenciaMB() {
    }

    public Integer getNumPuntoLimpio() {
        return numPuntoLimpio;
    }

    public void setNumPuntoLimpio(Integer numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }
    
    public Collection<SelectElemPojo> getListaPuntosLimpios() {
        return listaPuntosLimpios;
    }

    public void setListaPuntosLimpios(Collection<SelectElemPojo> listaPuntosLimpios) {
        this.listaPuntosLimpios = listaPuntosLimpios;
    }
    
    
    public void goToEnviarAviso() {
        if (numPuntoLimpio == null) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No ha seleccionado un punto limpio",
                    "No ha seleccionado un punto limpio para enviar el aviso de incidencia");
            CommonFunctions.goToPage("/faces/selectPtoLimpioAviso.xhtml?faces-redirect=true");
        }
        else
            CommonFunctions.goToPage("/faces/enviarAvisoIncidencia.xhtml"+"?id="+numPuntoLimpio);
    }
    
    public void goToSeleccionarPuntoLimpio() {
        CommonFunctions.goToPage("/faces/selectPtoLimpioAviso.xhtml");
    }
    
}
