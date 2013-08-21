/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.notificaciones;

import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorNotificacionesConversation")
@ConversationScoped
public class MantenedorNotificacionesConversation implements Serializable {
    @Inject Conversation conversation;
    
    private Integer idNotificacionSeleccionada;
    
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
    
    /**
     * Creates a new instance of MantenedorNotificacionesConversation
     */
    public MantenedorNotificacionesConversation() {
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Creando el bean con sessionScoped: MantenedorNotificaciones");
    }
    
    @PreDestroy
    public void destruir() {
        System.out.println("Destruyendo el bean con sessionScoped: MantenedorNotificaciones");
    }
    
    public void limpiarDatos() {
        idNotificacionSeleccionada = null;
    }

    public Integer getIdNotificacionSeleccionada() {
        return idNotificacionSeleccionada;
    }

    public void setIdNotificacionSeleccionada(Integer idNotificacionSeleccionada) {
        this.idNotificacionSeleccionada = idNotificacionSeleccionada;
    }
    
    public Conversation getConversation() {
        return conversation;
    }
    
}
