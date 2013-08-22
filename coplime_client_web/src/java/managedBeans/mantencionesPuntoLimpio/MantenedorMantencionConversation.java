/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantencionesPuntoLimpio;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import managedBeans.AbstractConversation;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorMantencionConversation")
@ConversationScoped
public class MantenedorMantencionConversation extends AbstractConversation implements Serializable {

    private Integer idMantencionDetalles;
    
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
    
}
