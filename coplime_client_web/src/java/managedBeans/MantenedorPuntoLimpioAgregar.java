/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Comuna;
import entities.Estado;
import entities.Inspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorPuntoLimpioAgregar")
@RequestScoped
public class MantenedorPuntoLimpioAgregar extends commonFunctions {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    private Integer num;
    private String nombre;
    private Integer comuna_seleccionada;
    private List<SelectElemPojo> listaComunas;
    private String direccion;
    private String fechaRevision;
    private Integer estado_seleccionado;
    private List<SelectElemPojo> listaEstadosPtoLimpio;
    private Integer inspectorEncargado_seleccionado;
    private List<SelectElemPojo> listaInspectores;
    
    /**
     * Creates a new instance of MantenedorPuntoLimpioAgregar
     */
    public MantenedorPuntoLimpioAgregar() {
        System.out.println("Se ha instanciado un MantenedorPuntoLimpioAgregar");
    }
    
    @PostConstruct
    public void init() {
        cargarEstadosPuntoLimpio();
        cargarInspectores();
        cargarComunas();
    }
    
    private void cargarInspectores() {
        Collection<Inspector> listaTemp = crudPuntoLimpio.getAllInspectores();
        SelectElemPojo elemTemp;
        this.listaInspectores = new ArrayList();
        for(Inspector insp_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(insp_iter.getId()));
            elemTemp.setLabel(insp_iter.getUsuario().getRut() + " - " 
                    + insp_iter.getUsuario().getNombre() + " " 
                    + insp_iter.getUsuario().getApellido1());
            this.listaInspectores.add(elemTemp);
            
        }
    }
    
    private void cargarEstadosPuntoLimpio() {
        Collection<Estado> listaTemp = crudPuntoLimpio.getAllEstadosPuntoLimpio();
        SelectElemPojo elemTemp;
        this.listaEstadosPtoLimpio = new ArrayList();
        for(Estado estado_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(estado_iter.getId()));
            elemTemp.setLabel(estado_iter.getNombreEstado());
            this.listaEstadosPtoLimpio.add(elemTemp);
        }
    }
    
    private void cargarComunas() {
        Collection<Comuna> listaTemp = crudPuntoLimpio.getAllComunas();
        SelectElemPojo elemTemp;
        this.listaComunas = new ArrayList();
        for(Comuna comuna_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(comuna_iter.getId()));
            elemTemp.setLabel(comuna_iter.getNombre());
            this.listaComunas.add(elemTemp);
        }
    }
    
    public void agregarContenedores() {
        System.out.println("Se hizo click en 'AgregarContenedores()'");
        goToPage("/faces/admin/agregarContenedor.xhtml");
    }
    
    public void volverToLista() {
        System.out.println("Se hizo click en 'volverToLista()'");
        goToPage("/faces/users/verPuntosLimpios.xhtml");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getComuna_seleccionada() {
        return comuna_seleccionada;
    }

    public void setComuna_seleccionada(Integer comuna_seleccionada) {
        this.comuna_seleccionada = comuna_seleccionada;
    }

    public List<SelectElemPojo> getListaComunas() {
        return listaComunas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(String fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Integer getEstado_seleccionado() {
        return estado_seleccionado;
    }

    public void setEstado_seleccionado(Integer estado_seleccionado) {
        this.estado_seleccionado = estado_seleccionado;
    }

    public List<SelectElemPojo> getListaEstadosPtoLimpio() {
        return listaEstadosPtoLimpio;
    }

    public void setListaEstadosPtoLimpio(List<SelectElemPojo> listaEstadosPtoLimpio) {
        this.listaEstadosPtoLimpio = listaEstadosPtoLimpio;
    }

    public Integer getInspectorEncargado_seleccionado() {
        return inspectorEncargado_seleccionado;
    }

    public void setInspectorEncargado_seleccionado(Integer inspectorEncargado_seleccionado) {
        this.inspectorEncargado_seleccionado = inspectorEncargado_seleccionado;
    }

    public List<SelectElemPojo> getListaInspectores() {
        return listaInspectores;
    }

    public void setListaInspectores(List<SelectElemPojo> listaInspectores) {
        this.listaInspectores = listaInspectores;
    }
    
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
