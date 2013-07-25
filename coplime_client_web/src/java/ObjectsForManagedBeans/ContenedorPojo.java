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
    Integer idMaterial;
    Integer idLlenadoContenedor;
    Integer idEstadoContenedor;
    Integer idUnidadMedida;
    int capacidad;
    String nombreMaterial;
    String nombreEstadoContenedor;
    
    public ContenedorPojo() {
        idLlenadoContenedor = 0;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Integer getLlenadoContenedor() {
        return idLlenadoContenedor;
    }

    public void setLlenadoContenedor(Integer idLlenadoContenedor) {
        this.idLlenadoContenedor = idLlenadoContenedor;
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

    public Integer getIdLlenadoContenedor() {
        return idLlenadoContenedor;
    }

    public void setIdLlenadoContenedor(Integer idLlenadoContenedor) {
        this.idLlenadoContenedor = idLlenadoContenedor;
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
