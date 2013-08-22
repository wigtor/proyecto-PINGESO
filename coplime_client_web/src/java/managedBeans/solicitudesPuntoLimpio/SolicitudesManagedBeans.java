/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.solicitudesPuntoLimpio;

import java.awt.event.ActionEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sessionBeans.CrudSolicitudMantencionLocal;

/**
 *
 * @author victor
 */
@Named(value = "solicitudesManagedBeans")
@SessionScoped
public class SolicitudesManagedBeans implements Serializable {
    @EJB
    private CrudSolicitudMantencionLocal solicitador;
    
    private int cantidadSolicitudes;
    private String usernameLogueado;
    
    
    /**
     * Creates a new instance of notificacionesManagedBeans
     */
    public SolicitudesManagedBeans() {
    }

    @PostConstruct
    public void cargarCantidadNotificaciones() {
        refresSolicitudes(null);
    }
    
    public void refresSolicitudes(ActionEvent actionEvent) {
        //System.out.println("Cargando método cargarCantidadNotificaciones");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        usernameLogueado = request.getRemoteUser();
        this.cantidadSolicitudes = solicitador.obtenerCantidadSolicitudes(usernameLogueado);
        
    }
    
    public int getCantidadSolicitudes() {
        return cantidadSolicitudes;
    }

    public void setCantidadSolicitudes(int cantidadSolicitudes) {
        this.cantidadSolicitudes = cantidadSolicitudes;
    }
}
