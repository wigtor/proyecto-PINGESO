/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.unidadMedida;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import managedBeans.AbstractConversation;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorUnidadMedidaConversation")
@ConversationScoped
public class MantenedorUnidadMedidaConversation extends AbstractConversation implements Serializable {

    private Integer idUnidadMedida;

    public Integer getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(Integer idUnidad) {
        this.idUnidadMedida = idUnidad;
    }
    
    public void limpiarDatos(){
        idUnidadMedida = null;
    }
            
    public MantenedorUnidadMedidaConversation() {
    }
}
