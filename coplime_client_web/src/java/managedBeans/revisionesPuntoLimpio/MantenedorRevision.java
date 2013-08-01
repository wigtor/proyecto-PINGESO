/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.revisionesPuntoLimpio;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorRevision")
@SessionScoped
public class MantenedorRevision implements Serializable {

    private Integer idRevisionDetalles;
    
    
    public void limpiarDatos() {
        idRevisionDetalles = null;
    }
    
    /**
     * Creates a new instance of MantenedorRevision
     */
    public MantenedorRevision() {
    }

    public Integer getIdRevisionDetalles() {
        return idRevisionDetalles;
    }

    public void setIdRevisionDetalles(Integer idRevisionDetalles) {
        this.idRevisionDetalles = idRevisionDetalles;
    }
    
}
