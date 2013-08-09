/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.operario;

import ObjectsForManagedBeans.UsuarioPojo;
import entities.OperarioMantencion;
import entities.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import otros.CommonFunctions;
import sessionBeans.CrudOperarioLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorOperarioConversation")
@SessionScoped
public class MantenedorOperarioConversation implements Serializable{
    
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
