/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.administrador;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorAdministradorConversation")
@ConversationScoped
public class MantenedorAdministradorConversation implements Serializable{
    //private static final long serialVersionUID = 2346533234L;
    
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
    
    public MantenedorAdministradorConversation() {
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
