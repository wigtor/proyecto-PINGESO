/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.material;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import managedBeans.AbstractConversation;

/**
 *
 * @author Armando
 */
@Named(value = "mantenedorMaterialConversation")
@ConversationScoped
public class MantenedorMaterialConversation extends AbstractConversation implements Serializable {

    private Integer idMaterial;

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMat) {
        this.idMaterial = idMat;
    }
    
    public void limpiarDatos(){
        idMaterial = null;
    }
            
    public MantenedorMaterialConversation() {
    }
}
