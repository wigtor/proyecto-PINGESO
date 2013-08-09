/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.notificaciones;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorNotificaciones")
@SessionScoped
public class MantenedorNotificaciones implements Serializable {
    private Integer idNotificacionSeleccionada;
    
    /**
     * Creates a new instance of MantenedorPuntoLimpio
     */
    public MantenedorNotificaciones() {
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Creando el bean con sessionScoped: MantenedorNotificaciones");
    }
    
    @PreDestroy
    public void destruir() {
        System.out.println("Destruyendo el bean con sessionScoped: MantenedorNotificaciones");
    }
    
    public void limpiarDatos() {
        idNotificacionSeleccionada = null;
    }

    public Integer getIdNotificacionSeleccionada() {
        return idNotificacionSeleccionada;
    }

    public void setIdNotificacionSeleccionada(Integer idNotificacionSeleccionada) {
        this.idNotificacionSeleccionada = idNotificacionSeleccionada;
    }
    
}
