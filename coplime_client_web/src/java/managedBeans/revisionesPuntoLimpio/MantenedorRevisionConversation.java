/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.revisionesPuntoLimpio;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import managedBeans.AbstractConversation;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorRevisionConversation")
@ConversationScoped
public class MantenedorRevisionConversation extends AbstractConversation implements Serializable {

    private Integer idRevisionDetalles;
    
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
    
}
