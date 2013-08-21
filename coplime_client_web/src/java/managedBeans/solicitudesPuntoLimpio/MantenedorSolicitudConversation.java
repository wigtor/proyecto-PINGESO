/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.solicitudesPuntoLimpio;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorSolicitudConversation")
@ConversationScoped
public class MantenedorSolicitudConversation implements Serializable {

    @Inject Conversation conversation;
    
    private Integer idSolicitudDetalles;
    
    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }
    
    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    
    
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
    
    public Conversation getConversation() {
        return conversation;
    }
    
}
