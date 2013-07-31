/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.puntoLimpio;

import ObjectsForManagedBeans.ContenedorPojo;
import ObjectsForManagedBeans.SelectElemPojo;
import entities.Estado;
import entities.Material;
import entities.UnidadMedida;
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
@Named(value = "MantenedorPuntoLimpioAgregarCont")
@RequestScoped
public class MantenedorPuntoLimpioAgregarCont {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private MantenedorPuntoLimpio mantPtoLimpio;
    
    private Integer numPtoLimpio;
    private Integer material;
    private int capacidad;
    private Integer idUnidadMedida;
    private List<SelectElemPojo> listaUnidadesMedida;
    private List<SelectElemPojo> listaMateriales;
    private Integer llenadoContenedor;
    private List<SelectElemPojo> listaEstadosContenedores;
    private Integer estadoContenedor;
    
    /**
     * Creates a new instance of MantenedorPuntoLimpioAgregar
     */
    public MantenedorPuntoLimpioAgregarCont() {
        System.out.println("Se ha instanciado un MantenedorPuntoLimpioAgregar");
        this.llenadoContenedor = 0;
    }
    
    @PostConstruct
    public void init() {
        cargarEstadosPuntoLimpio();
        cargarMateriales();
        cargarUnidadesMedida();
        if (mantPtoLimpio.getPto_creando() != null) {
            this.numPtoLimpio = mantPtoLimpio.getPto_creando().getNum();
        }
        else {
            //MOSTRAR ERROR
            
            CommonFunctions.goToPage("/faces/admin/agregarPuntoLimpio.xhtml");
        }
    }
    
    
    private void cargarEstadosPuntoLimpio() {
        Collection<Estado> listaTemp = crudPuntoLimpio.getAllEstadosPuntoLimpio();
        SelectElemPojo elemTemp;
        this.listaEstadosContenedores = new ArrayList();
        for(Estado estado_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(estado_iter.getId()));
            elemTemp.setLabel(estado_iter.getNombreEstado());
            this.listaEstadosContenedores.add(elemTemp);
        }
    }
    
    private void cargarMateriales() {
        Collection<Material> listaTemp = crudPuntoLimpio.getAllMateriales();
        SelectElemPojo elemTemp;
        this.listaMateriales = new ArrayList();
        for(Material mat_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(mat_iter.getId()));
            elemTemp.setLabel(mat_iter.getNombre_material());
            this.listaMateriales.add(elemTemp);
        }
    }
    
    private void cargarUnidadesMedida() {
        Collection<UnidadMedida> listaTemp = crudPuntoLimpio.getAllUnidadesMedida();
        SelectElemPojo elemTemp;
        this.listaUnidadesMedida = new ArrayList();
        for(UnidadMedida unidad_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(unidad_iter.getId()));
            elemTemp.setLabel(unidad_iter.getNombreUnidad());
            this.listaUnidadesMedida.add(elemTemp);
        }
    }
    
    private void limpiarCampos() {
        this.material = null;
        this.estadoContenedor = null;
        this.llenadoContenedor = null;
    }
    
    private ContenedorPojo crearContenedorTemporal() {
        ContenedorPojo res = new ContenedorPojo();
        res.setIdMaterial(material);
        res.setLlenadoContenedor(llenadoContenedor);
        res.setIdEstadoContenedor(estadoContenedor);
        res.setIdUnidadMedida(idUnidadMedida);
        res.setCapacidad(capacidad);
        Integer intTemp;
        for(SelectElemPojo matTemp : this.listaMateriales) {
            intTemp = Integer.parseInt(matTemp.getId());
            if (intTemp == material) {
                res.setNombreMaterial(matTemp.getLabel());
                break;
            }
        }
        
        return res;
    }
    
    public void guardarNvoContenedor_y_otro() {
        System.out.println("Se hizo click en 'guardarNvoContenedor_y_otro()'");
        mantPtoLimpio.getContenedores_creando().add(crearContenedorTemporal());
        
        CommonFunctions.goToPage("/faces/admin/agregarContenedor.xhtml");
    }
    
    public void guardarNvoContenedor() {
        System.out.println("Se hizo click en 'guardarNvoContenedor()'");
        mantPtoLimpio.getContenedores_creando().add(crearContenedorTemporal());
        
        CommonFunctions.goToPage("/faces/admin/agregarPuntoLimpio.xhtml");
    }
    
    public void volverToPuntoLimpio() {
        System.out.println("Se hizo click en 'volverToPuntoLimpio()'");
        CommonFunctions.goToPage("/faces/admin/agregarPuntoLimpio.xhtml");
    }
    
    public Integer getNumPtoLimpio() {
        return numPtoLimpio;
    }

    public void setNumPtoLimpio(Integer numPtoLimpio) {
        this.numPtoLimpio = numPtoLimpio;
    }

    public Integer getMaterial() {
        return material;
    }

    public void setMaterial(Integer material) {
        this.material = material;
    }

    public List<SelectElemPojo> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<SelectElemPojo> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public Integer getLlenadoContenedor() {
        return llenadoContenedor;
    }

    public void setLlenadoContenedor(Integer llenadoContenedor) {
        this.llenadoContenedor = llenadoContenedor;
    }

    public List<SelectElemPojo> getListaEstadosContenedores() {
        return listaEstadosContenedores;
    }

    public void setListaEstadosContenedores(List<SelectElemPojo> listaEstadosContenedores) {
        this.listaEstadosContenedores = listaEstadosContenedores;
    }

    public Integer getEstadoContenedor() {
        return estadoContenedor;
    }

    public void setEstadoContenedor(Integer estadoContenedor) {
        this.estadoContenedor = estadoContenedor;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(Integer idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public List<SelectElemPojo> getListaUnidadesMedida() {
        return listaUnidadesMedida;
    }

    public void setListaUnidadesMedida(List<SelectElemPojo> listaUnidadesMedida) {
        this.listaUnidadesMedida = listaUnidadesMedida;
    }
    
}
