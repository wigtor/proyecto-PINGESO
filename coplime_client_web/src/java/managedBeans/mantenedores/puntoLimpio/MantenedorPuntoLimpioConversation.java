/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.puntoLimpio;

import ObjectsForManagedBeans.ContenedorPojo;
import ObjectsForManagedBeans.PuntoLimpioPojo;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorPuntoLimpioConversation")
@ConversationScoped
public class MantenedorPuntoLimpioConversation implements Serializable {
    @Inject Conversation conversation;
    
    private PuntoLimpioPojo pto_creando;
    private List<ContenedorPojo> contenedores_creando;
    private Integer idPuntoLimpioDetalles;
    
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
     * Creates a new instance of MantenedorPuntoLimpioConversation
     */
    public MantenedorPuntoLimpioConversation() {
        contenedores_creando = new LinkedList<>();
    }
    
    public void limpiarDatos() {
        pto_creando = null;
        contenedores_creando.clear();
        idPuntoLimpioDetalles = null;
    }
    
    public PuntoLimpioPojo getPto_creando() {
        return pto_creando;
    }

    public void setPto_creando(PuntoLimpioPojo pto_creando) {
        this.pto_creando = pto_creando;
    }

    public List<ContenedorPojo> getContenedores_creando() {
        return contenedores_creando;
    }

    public Integer getIdPuntoLimpioDetalles() {
        return idPuntoLimpioDetalles;
    }

    public void setIdPuntoLimpioDetalles(Integer idPuntoLimpioDetalles) {
        this.idPuntoLimpioDetalles = idPuntoLimpioDetalles;
    }
    
    public Conversation getConversation() {
        return conversation;
    }
    
}
