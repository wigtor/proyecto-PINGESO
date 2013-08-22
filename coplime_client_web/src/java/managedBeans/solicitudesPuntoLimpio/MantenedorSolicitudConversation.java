/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.solicitudesPuntoLimpio;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import managedBeans.AbstractConversation;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorSolicitudConversation")
@ConversationScoped
public class MantenedorSolicitudConversation extends AbstractConversation implements Serializable {

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
