/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.solicitudesPuntoLimpio;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorSolicitudConversation")
@SessionScoped
public class MantenedorSolicitudConversation implements Serializable {

    private Integer idSolicitudDetalles;
    
    
    public void limpiarDatos() {
        idSolicitudDetalles = null;
    }
    
    /**
     * Creates a new instance of MantenedorSolicitudConversation
     */
    public MantenedorSolicitudConversation() {
    }

    public Integer getIdSolicitudDetalles() {
        return idSolicitudDetalles;
    }

    public void setIdSolicitudDetalles(Integer idSolicitudDetalles) {
        this.idSolicitudDetalles = idSolicitudDetalles;
    }
    
}
