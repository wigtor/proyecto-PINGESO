/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.cambioEstadoPuntoLimpio;

import ObjectsForManagedBeans.ContenedorPojo;
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
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "CambioEstadoContenedor")
@RequestScoped
public class CambioEstadoContenedor {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private CambioEstadoPuntoLimpio_session cambioEstadoSessionBean;
    
    private List<SelectElemPojo> listaEstados;
    
    private Integer idPtoLimpio;
    private Integer idContenedor;
    private Integer idEstado;
    private Integer llenado;
    private String nombrePtoLimpio;
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
        if (this.idContenedor == null) {
            //Error
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
            return;
        }
        Contenedor cont = crudPuntoLimpio.getContenedor(this.idContenedor);
        if (cont == null) {
            return;
        }
        this.material = cont.getMaterialDeAcopio().getNombre_material();
        this.nombrePtoLimpio = cont.getPuntoLimpio().getId() + " - " + cont.getPuntoLimpio().getNombre();
        int indice = buscarSiContenedorFueModificado(idContenedor);
        if (indice >= 0) {
            ContenedorPojo c = cambioEstadoSessionBean.getListaContenedoresModificados().get(indice);
            this.idEstado = c.getIdEstadoContenedor();
            this.llenado = c.getLlenadoContenedor();
        }
        else {
            this.idEstado = cont.getEstadoContenedor().getId();
            this.llenado = cont.getProcentajeUso();
        }
    }
    
    private int buscarSiContenedorFueModificado(Integer idContenedor) {
        int index = 0;
        for(ContenedorPojo c : cambioEstadoSessionBean.getListaContenedoresModificados()) {
            if (c.getId().intValue() == idContenedor.intValue()) {
                return index;
            }
            index++;
        }
        return -1;
    }
    
    public void guardarCambios() {
        ContenedorPojo contModificado = new ContenedorPojo();
        contModificado.setId(this.idContenedor);
        contModificado.setLlenadoContenedor(llenado);
        contModificado.setIdEstadoContenedor(idEstado);
        this.cambioEstadoSessionBean.getListaContenedoresModificados().add(contModificado);
        this.cambioEstadoSessionBean.setIdContenedorToChange(null); //Sólo el contenedor lo mando a null
        
        CommonFunctions.goToPage("/faces/users/cambiarEstadoPuntoLimpio.xhtml");
    }
    
    public void volver() {
        System.out.println("Se hizo click en 'volver()'");
        cambioEstadoSessionBean.setIdContenedorToChange(null);
        CommonFunctions.goToPage("/faces/users/cambiarEstadoPuntoLimpio.xhtml");
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
    
    public String getNombrePtoLimpio() {
        return nombrePtoLimpio;
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