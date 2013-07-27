/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Contenedor;
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
@Named(value = "CambioEstadoContenedor")
@RequestScoped
public class CambioEstadoContenedor extends commonFunctions {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private CambioEstadoPuntoLimpio_session cambioEstadoSessionBean;
    
    private List<SelectElemPojo> listaEstados;
    
    private Integer idPtoLimpio;
    private Integer idContenedor;
    private Integer idEstado;
    private Integer llenado;
    private String ptoLimpio;
    private String material;
    
    /**
     * Creates a new instance of CambioEstadoPuntoLimpio
     */
    public CambioEstadoContenedor() {
    }
    
    @PostConstruct
    public void init() {
        cargarEstadosPuntoLimpio();
        cargarDatosContenedor();
    }
    
    private void cargarEstadosPuntoLimpio() {
        Collection<Estado> listaTemp = crudPuntoLimpio.getAllEstadosPuntoLimpio();
        SelectElemPojo elemTemp;
        this.listaEstados = new ArrayList();
        for(Estado estado_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(estado_iter.getId()));
            elemTemp.setLabel(estado_iter.getNombreEstado());
            this.listaEstados.add(elemTemp);
        }
    }
    
    private void cargarDatosContenedor() {
        this.idPtoLimpio = cambioEstadoSessionBean.getIdEstadoToChange();
        this.idContenedor = cambioEstadoSessionBean.getIdContenedorToChange();
        Contenedor cont = crudPuntoLimpio.getContenedor(this.idContenedor);
        this.material = cont.getMaterialDeAcopio().getNombre_material();
        this.ptoLimpio = cont.getPuntoLimpio().getId() + " - " + cont.getPuntoLimpio().getNombre();
        this.idEstado = cont.getEstadoContenedor().getId();
        this.llenado = cont.getProcentajeUso();
    }
    
    public void guardarCambios() {
        
    }
    
    public void volver() {
        System.out.println("Se hizo click en 'volver()'");
        cambioEstadoSessionBean.limpiarCampos();
        goToPage("/faces/users/operario/cambiarEstadoPuntoLimpio.xhtml");
    }

    public List<SelectElemPojo> getListaEstados() {
        return listaEstados;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    public String getPtoLimpio() {
        return ptoLimpio;
    }
    
    public String getMaterial() {
        return material;
    }
    
    public Integer getLlenado() {
        return llenado;
    }

    public void setLlenado(Integer llenado) {
        this.llenado = llenado;
    }
}
