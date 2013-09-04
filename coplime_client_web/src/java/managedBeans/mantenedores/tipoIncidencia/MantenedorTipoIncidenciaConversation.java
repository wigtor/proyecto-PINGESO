/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.tipoIncidencia;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import managedBeans.AbstractConversation;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorTipoIncidenciaConversation")
@ConversationScoped
public class MantenedorTipoIncidenciaConversation extends AbstractConversation implements Serializable {

    private Integer idTipoInc;

    public Integer getIdTipoInc() {
        return idTipoInc;
    }

    public void setIdTipoInc(Integer idTipoInc) {
        this.idTipoInc = idTipoInc;
    }
    
    public void limpiarDatos(){
        idTipoInc = null;
    }
            
    public MantenedorTipoIncidenciaConversation() {
    }
}
