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
    
    public Integer getEstadoContenedor() {
        return idEstadoContenedor;
    }

    public void setEstadoContenedor(Integer idEstadoContenedor) {
        this.idEstadoContenedor = idEstadoContenedor;
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
    
}
