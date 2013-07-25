/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.Comuna;
import entities.Estado;
import entities.Inspector;
import entities.Material;
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
@Named(value = "MantenedorPuntoLimpioAgregarCont")
@RequestScoped
public class MantenedorPuntoLimpioAgregarCont extends commonFunctions {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    private Integer num;
    private String nombre;
    private Integer material;
    private List<SelectElemPojo> listaMateriales;
    private Integer llenadoContenedor;
    private List<SelectElemPojo> listaEstadosContenedores;
    private Integer estadoContenedor;
    
    /**
     * Creates a new instance of MantenedorPuntoLimpioAgregar
     */
    public MantenedorPuntoLimpioAgregarCont() {
        System.out.println("Se ha instanciado un MantenedorPuntoLimpioAgregar");
    }
    
    @PostConstruct
    public void init() {
        cargarEstadosPuntoLimpio();
        cargarMateriales();
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
    
    public void agregarContenedores() {
        System.out.println("Se hizo click en 'AgregarContenedores()'");
        goToPage("/faces/admin/agregarContenedor.xhtml");
    }
    
    public void volverToPuntoLimpio() {
        System.out.println("Se hizo click en 'volverToPuntoLimpio()'");
        goToPage("/faces/users/agregarPuntoLimpio.xhtml");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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
}
