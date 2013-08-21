/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorOperarioConversation")
@ConversationScoped
public class MantenedorOperarioConversation implements Serializable{
    
    @Inject Conversation conversation;
    
    private Integer idUsuarioDetalles;
    
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
    
    public MantenedorOperarioConversation() {
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
