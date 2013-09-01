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
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorPuntoLimpioAgregarContMB")
@RequestScoped
public class MantenedorPuntoLimpioAgregarContMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private MantenedorPuntoLimpioConversation mantPtoLimpio;
    
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
    public MantenedorPuntoLimpioAgregarContMB() {
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
            
            CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml");
        }
    }
    
    
    private void cargarEstadosPuntoLimpio() {
        Collection<Estado> listaTemp = crudPuntoLimpio.getAllEstadosPuntoLimpio();
        SelectElemPojo elemTemp;
        this.listaEstadosContenedores = new ArrayList<>();
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
        this.listaMateriales = new ArrayList<>();
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
        this.listaUnidadesMedida = new ArrayList<>();
        for(UnidadMedida unidad_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(unidad_iter.getId()));
            elemTemp.setLabel(unidad_iter.getNombreUnidad());
            this.listaUnidadesMedida.add(elemTemp);
        }
    }
    
    private ContenedorPojo crearContenedorTemporal() {
        ContenedorPojo res = new ContenedorPojo();
        //para darle cualquier valor, pero que no se repita
        res.setId(new Integer(Math.abs((int)(Calendar.getInstance().getTimeInMillis()))));
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
        
        for(SelectElemPojo unidTemp : this.listaUnidadesMedida) {
            intTemp = Integer.parseInt(unidTemp.getId());
            if (intTemp == material) {
                res.setNombreUnidadMedida(unidTemp.getLabel());
                break;
            }
        }
        
        return res;
    }
    
    public void eliminarContenedorAgregado(Integer id) {
        this.mantPtoLimpio.eliminarContenedor(id);
        CommonFunctions.goToPage("/faces/users/admin/agregarContenedor.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
    }
    
    public void guardarNvoContenedor_y_otro() {
        //System.out.println("Se hizo click en 'guardarNvoContenedor_y_otro()'");
        this.mantPtoLimpio.getContenedores_creando().add(crearContenedorTemporal());
        CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO, 
                "Se ha almacenado temporalmente la creación de un contenedor", 
                "Se ha almacenado temporalmente la creación de un contenedor para el punto limpio que está creando");
        CommonFunctions.goToPage("/faces/users/admin/agregarContenedor.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
    }
    
    public void guardarNvoContenedor() {
        //System.out.println("Se hizo click en 'guardarNvoContenedor()'");
        this.mantPtoLimpio.getContenedores_creando().add(crearContenedorTemporal());
        CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO, 
                "Se ha almacenado temporalmente la creación de un contenedor", 
                "Se ha almacenado temporalmente la creación de un contenedor para el punto limpio que está creando");
        CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
    }
    
    public void volverToPuntoLimpio() {
        //System.out.println("Se hizo click en 'volverToPuntoLimpio()'");
        CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
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
    
    public List<ContenedorPojo> getListaContenedores() {
        return this.mantPtoLimpio.getContenedores_creando();
    }
    
}
