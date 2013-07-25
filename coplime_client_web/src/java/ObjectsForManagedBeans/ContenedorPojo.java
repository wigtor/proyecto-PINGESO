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
    Integer llenadoContenedor;
    Integer estadoContenedor;
    
    public ContenedorPojo() {
        llenadoContenedor = 0;
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
    
    public Integer getEstadoContenedor() {
        return estadoContenedor;
    }

    public void setEstadoContenedor(Integer estadoContenedor) {
        this.estadoContenedor = estadoContenedor;
    }
    
}
