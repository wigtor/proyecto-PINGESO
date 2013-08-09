/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.administrador;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Carlos Barrera
 */
@Named(value = "mantenedorAdministradorConversation")
@SessionScoped
public class MantenedorAdministradorConversation implements Serializable{

    private Integer idUsuarioDetalles;
    
    public void limpiarDatos() {
        idUsuarioDetalles = null;
    }
    
    public MantenedorAdministradorConversation() {
    }

    public Integer getIdUsuarioDetalles() {
        return idUsuarioDetalles;
    }

    public void setIdUsuarioDetalles(Integer idUsuarioDetalles) {
        this.idUsuarioDetalles = idUsuarioDetalles;
    }
    
}
