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
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorPuntoLimpioEditarContMB")
@RequestScoped
public class MantenedorPuntoLimpioEditarContMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private MantenedorPuntoLimpioConversation mantPtoLimpio;
    
    private Integer numPtoLimpio;
    private Integer idContenedor;
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
    public MantenedorPuntoLimpioEditarContMB() {
        this.llenadoContenedor = 0;
    }
    
    @PostConstruct
    public void init() {
        cargarEstadosPuntoLimpio();
        cargarMateriales();
        cargarUnidadesMedida();
        if (mantPtoLimpio.getIdContenedorEditando() != null) {
            this.numPtoLimpio = mantPtoLimpio.getIdPuntoLimpioDetalles();
            this.idContenedor = mantPtoLimpio.getIdContenedorEditando();
            cargarDatosContenedor();
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        }
    }
    
    private void cargarDatosContenedor() {
        ContenedorPojo contBuscado = null;
        for(ContenedorPojo cp : this.mantPtoLimpio.getContenedores_creando()) {
            if (cp.getId().intValue() == this.mantPtoLimpio.getIdContenedorEditando().intValue()) {
                contBuscado = cp;
                break;
            }
        }
        if (contBuscado != null) {
            this.capacidad = contBuscado.getCapacidad();
            this.estadoContenedor = contBuscado.getIdEstadoContenedor();
            this.material = contBuscado.getIdMaterial();
            this.idUnidadMedida = contBuscado.getIdUnidadMedida();
            
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
    
    private ContenedorPojo editarContenedorTemporal(ContenedorPojo res) {
        //para darle cualquier valor, pero que no se repita
        //res.setId(new Integer(Math.abs((int)(Calendar.getInstance().getTimeInMillis()))));
        res.setEditado(true);
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
        if (this.mantPtoLimpio.getState() == MantenedorPuntoLimpioConversation.AGREGAR) {
            CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
        }
        if (this.mantPtoLimpio.getState() == MantenedorPuntoLimpioConversation.EDITAR) {
            CommonFunctions.goToPage("/faces/users/admin/editarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
        }
    }
    
    
    public void guardarDatosContenedor() {
        //System.out.println("Se hizo click en 'guardarNvoContenedor()'");
        for(ContenedorPojo cp : this.mantPtoLimpio.getContenedores_creando()) {
            if (cp.getId().intValue() == this.mantPtoLimpio.getIdContenedorEditando().intValue()) {
                editarContenedorTemporal(cp);
                break;
            }
        }
        CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO, 
                "Se han guardado temporalmente los cambios del contenedor", 
                "Se han guardado temporalmente los cambios del contenedor para el punto limpio que está editando");
        
        if (this.mantPtoLimpio.getState() == MantenedorPuntoLimpioConversation.AGREGAR) {
            CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
        }
        if (this.mantPtoLimpio.getState() == MantenedorPuntoLimpioConversation.EDITAR) {
            CommonFunctions.goToPage("/faces/users/admin/editarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
        }
    }
    
    public void volverToPuntoLimpio() {
        if (this.mantPtoLimpio.getState() == MantenedorPuntoLimpioConversation.AGREGAR) {
            CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
        }
        if (this.mantPtoLimpio.getState() == MantenedorPuntoLimpioConversation.EDITAR) {
            CommonFunctions.goToPage("/faces/users/admin/editarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
        }
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

    public Integer getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(Integer idContenedor) {
        this.idContenedor = idContenedor;
    }
    
}
