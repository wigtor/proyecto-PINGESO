/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.comuna;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import managedBeans.AbstractConversation;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorComunaConversation")
@ConversationScoped
public class MantenedorComunaConversation extends AbstractConversation implements Serializable {

    private Integer idComuna;

    public Integer getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Integer idComuna) {
        this.idComuna = idComuna;
    }
    
    public void limpiarDatos(){
        idComuna = null;
    }
            
    public MantenedorComunaConversation() {
    }
}
