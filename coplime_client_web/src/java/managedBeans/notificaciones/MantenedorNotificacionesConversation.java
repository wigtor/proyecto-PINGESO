/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.notificaciones;

import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import managedBeans.AbstractConversation;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorNotificacionesConversation")
@SessionScoped
public class MantenedorNotificacionesConversation implements Serializable {
    
    private Integer idNotificacionSeleccionada;
    
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
    
}
