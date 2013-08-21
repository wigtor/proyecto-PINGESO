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
    Integer idMaterial;
    Integer llenadoContenedor;
    Integer idEstadoContenedor;
    Integer idUnidadMedida;
    int capacidad;
    String nombreMaterial;
    String nombreEstadoContenedor;
    
    public ContenedorPojo() {
        llenadoContenedor = 0;
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
    
}
