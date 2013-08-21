 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;


/**
 *
 * @author victor
 */
@Named(value = "mantenedorInspectorConversation")
@ConversationScoped
public class MantenedorInspectorConversation implements Serializable{
       
    @Inject Conversation conversation;
    
    private Integer idUsuarioDetalles;
    
    public void limpiarDatos() {
        idUsuarioDetalles = null;
    }
    
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
    
    public MantenedorInspectorConversation() {
               
    }

    public Integer getIdUsuarioDetalles() {
        return idUsuarioDetalles;
    }

    public void setIdUsuarioDetalles(Integer idUsuarioDetalles) {
        this.idUsuarioDetalles = idUsuarioDetalles;
    }
    
    public Conversation getConversation() {
        return conversation;
    }
    
}
