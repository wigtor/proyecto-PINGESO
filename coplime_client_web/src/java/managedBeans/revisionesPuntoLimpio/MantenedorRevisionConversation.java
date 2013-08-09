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
@Named(value = "mantenedorRevisionConversation")
@SessionScoped
public class MantenedorRevisionConversation implements Serializable {

    private Integer idRevisionDetalles;
    
    
    public void limpiarDatos() {
        idRevisionDetalles = null;
    }
    
    /**
     * Creates a new instance of MantenedorRevisionConversation
     */
    public MantenedorRevisionConversation() {
    }

    public Integer getIdRevisionDetalles() {
        return idRevisionDetalles;
    }

    public void setIdRevisionDetalles(Integer idRevisionDetalles) {
        this.idRevisionDetalles = idRevisionDetalles;
    }
    
}
