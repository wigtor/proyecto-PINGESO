/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.revisionesPuntoLimpio;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorRevisionConversation")
@ConversationScoped
public class MantenedorRevisionConversation implements Serializable {

    @Inject Conversation conversation;
    
    private Integer idRevisionDetalles;
    
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
    
    public Conversation getConversation() {
        return conversation;
    }
    
}
