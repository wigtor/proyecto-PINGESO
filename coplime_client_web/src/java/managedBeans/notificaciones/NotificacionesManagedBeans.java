/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.notificaciones;

import java.awt.event.ActionEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sessionBeans.NotificadorLocal;

/**
 *
 * @author victor
 */
@Named(value = "notificacionesManagedBeans")
@SessionScoped
public class NotificacionesManagedBeans implements Serializable {
    @EJB
    private NotificadorLocal notificador;
    
    private int cantidadNotificaciones;
    private int cantidadSolicitudesMantencion;
    private String usernameLogueado;
    
    
    /**
     * Creates a new instance of NotificacionesManagedBeans
     */
    public NotificacionesManagedBeans() {
    }

    @PostConstruct
    public void cargarCantidadNotificaciones() {
        refresNotificaciones(null);
    }
    
    public void refresNotificaciones(ActionEvent actionEvent) {
        //System.out.println("Cargando método cargarCantidadNotificaciones");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        usernameLogueado = request.getRemoteUser();
        this.cantidadNotificaciones = notificador.obtenerCantidadNotificaciones(usernameLogueado);
        
    }
    
    public void refresSolicitudesMantencion(ActionEvent actionEvent) {
        //System.out.println("Cargando método cargarCantidadNotificaciones");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        usernameLogueado = request.getRemoteUser();
        this.cantidadSolicitudesMantencion = notificador.obtenerCantidadSolicitudesMantencion(usernameLogueado);
        
    }
    
    public int getCantidadNotificaciones() {
        return cantidadNotificaciones;
    }

    public void setCantidadNotificaciones(int cantidadNotificaciones) {
        this.cantidadNotificaciones = cantidadNotificaciones;
    }

    public int getCantidadSolicitudesMantencion() {
        return cantidadSolicitudesMantencion;
    }

    public void setCantidadSolicitudesMantencion(int cantidadSolicitudesMantencion) {
        this.cantidadSolicitudesMantencion = cantidadSolicitudesMantencion;
    }
    
}
