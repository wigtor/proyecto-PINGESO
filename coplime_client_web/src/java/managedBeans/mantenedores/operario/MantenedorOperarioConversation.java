/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import managedBeans.AbstractConversation;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorOperarioConversation")
@ConversationScoped
public class MantenedorOperarioConversation extends AbstractConversation implements Serializable{
    
    private Integer idUsuarioDetalles;
    
    public MantenedorOperarioConversation() {
    }
    
    public Integer getIdUsuarioDetalles() {
        return idUsuarioDetalles;
    }

    public void setIdUsuarioDetalles(Integer idUsuarioDetalles) {
        this.idUsuarioDetalles = idUsuarioDetalles;
    }
   
}
