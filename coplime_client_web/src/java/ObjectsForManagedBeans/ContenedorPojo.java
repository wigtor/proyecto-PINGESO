/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectsForManagedBeans;

/**
 *
 * @author victor
 */
public class ContenedorPojo {
    Integer id;
    boolean editado;
    boolean nuevo;
    Integer idMaterial;
    Integer llenadoContenedor;
    Integer idEstadoContenedor;
    Integer idUnidadMedida;
    int capacidad;
    String nombreMaterial;
    String nombreEstadoContenedor;
    String nombreUnidadMedida;
    
    public ContenedorPojo() {
        llenadoContenedor = 0;
        editado = false;
        nuevo = true;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Integer getLlenadoContenedor() {
        return llenadoContenedor;
    }

    public void setLlenadoContenedor(Integer llenadoContenedor) {
        this.llenadoContenedor = llenadoContenedor;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getNombreEstadoContenedor() {
        return nombreEstadoContenedor;
    }

    public void setNombreEstadoContenedor(String nombreEstadoContenedor) {
        this.nombreEstadoContenedor = nombreEstadoContenedor;
    }

    public Integer getIdEstadoContenedor() {
        return idEstadoContenedor;
    }

    public void setIdEstadoContenedor(Integer idEstadoContenedor) {
        this.idEstadoContenedor = idEstadoContenedor;
    }

    public Integer getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(Integer idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public boolean isEditado() {
        return editado;
    }

    public void setEditado(boolean editado) {
        this.editado = editado;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }
    
}
