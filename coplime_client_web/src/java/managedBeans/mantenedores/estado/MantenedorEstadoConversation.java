/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.estado;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import managedBeans.AbstractConversation;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorEstadoConversation")
@ConversationScoped
public class MantenedorEstadoConversation extends AbstractConversation implements Serializable {

    private Integer idEstado;

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    public void limpiarDatos(){
        idEstado = null;
    }
            
    public MantenedorEstadoConversation() {
    }
}
