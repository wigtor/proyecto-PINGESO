/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantencionesPuntoLimpio;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorMantencionConversation")
@SessionScoped
public class MantenedorMantencionConversation implements Serializable {

    private Integer idMantencionDetalles;
    
    
    public void limpiarDatos() {
        idMantencionDetalles = null;
    }
    
    /**
     * Creates a new instance of MantenedorMantencionConversation
     */
    public MantenedorMantencionConversation() {
    }

    public Integer getIdMantencionDetalles() {
        return idMantencionDetalles;
    }

    public void setIdMantencionDetalles(Integer idMantencionDetalles) {
        this.idMantencionDetalles = idMantencionDetalles;
    }
    
}
