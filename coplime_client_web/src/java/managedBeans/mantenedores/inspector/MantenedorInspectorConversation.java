 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.inspector;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;


/**
 *
 * @author victor
 */
@Named(value = "mantenedorInspectorConversation")
@SessionScoped
public class MantenedorInspectorConversation implements Serializable{
       
    private Integer idUsuarioDetalles;
    
    public void limpiarDatos() {
        idUsuarioDetalles = null;
    }
    
    
    public MantenedorInspectorConversation() {
               
    }

    public Integer getIdUsuarioDetalles() {
        return idUsuarioDetalles;
    }

    public void setIdUsuarioDetalles(Integer idUsuarioDetalles) {
        this.idUsuarioDetalles = idUsuarioDetalles;
    }
    
}
