/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantencionesPuntoLimpio;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorMantencionConversation")
@ConversationScoped
public class MantenedorMantencionConversation implements Serializable {

    @Inject Conversation conversation;
    
    private Integer idMantencionDetalles;
    
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
    
    public Conversation getConversation() {
        return conversation;
    }
    
}
