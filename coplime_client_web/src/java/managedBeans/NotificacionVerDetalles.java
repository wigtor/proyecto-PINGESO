/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Notificacion;
import entities.NotificacionDeUsuario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.StreamedContent;
import sessionBeans.NotificadorLocal;

/**
 *
 * @author victor
 */
@Named(value = "NotificacionVerDetalles")
@RequestScoped
public class NotificacionVerDetalles extends commonFunctions implements Serializable {
    @EJB
    private NotificadorLocal notificador;
    
    @Inject visorImagenesManagedBeans imb;
    
    private Integer numNotif;
    private String origen_seleccionado;
    private String tipo_seleccionado;
    private String fecha_seleccionado;
    private String detalleCompleto_seleccionado;
    private boolean revisada_seleccionado;
    private boolean resuelta_seleccionado;
    private StreamedContent imagen;
    
    @PostConstruct
    public void init() {
        System.out.println("Ejecutando init de NotificacionesVerDetalles");
        
        if (isGetMethod()) {
            if (!notificacionIsSelected()) {
                goToPage("/faces/users/verNotificaciones.xhtml");
                return;
            }
            Notificacion notifTemp = notificador.getNotificacion(this.numNotif);
            this.detalleCompleto_seleccionado = notifTemp.getComentario();
            this.fecha_seleccionado = notifTemp.getFechaHora().get(Calendar.DAY_OF_MONTH)
                        +"-"
                        +notifTemp.getFechaHora().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                        +"-"
                        +notifTemp.getFechaHora().get(Calendar.YEAR)
                        +" a las "+notifTemp.getFechaHora().get(Calendar.HOUR)
                        +":"+notifTemp.getFechaHora().get(Calendar.MINUTE);
            this.tipo_seleccionado = notifTemp.getTipoIncidencia().getNombreIncidencia();
            this.origen_seleccionado = notificador.getOrigenIncidencia(notifTemp);

            if (notificador.isNotificacionUsuario(notifTemp)) {
                NotificacionDeUsuario notifUsuarioTemp = (NotificacionDeUsuario)notifTemp;
                imb.setNotifTemp(notifUsuarioTemp);
            }
        }
        System.out.println("listo init() de NotificacionesVerDetalles");
    }
    
    private boolean notificacionIsSelected() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String paramNumNotif = externalContext.getRequestParameterMap().get("id");
        if (paramNumNotif == null){ //No se ha seleccionado aún, redirecciono
            return false;
        }
        
        //Validar que existe y es un número realmente
        try {
            this.numNotif = Integer.parseInt(paramNumNotif);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        
        Notificacion notifTemp = notificador.getNotificacion(this.numNotif);
        if (notifTemp == null) {
            return false;
        }
        return true;
    }
    
    public void verDetalles(Integer idSeleccionado) {
        System.out.println("Se quiere ver los detalles: "+idSeleccionado);
        goToPage("/faces/users/detallesNotificacion.xhtml"+"?id="+idSeleccionado);
    }
    
    public void volverToListado() {
        goToPage("/faces/users/verNotificaciones.xhtml");
    }
    
    public void eliminar(Integer num) {
        
    }
    
    
    /**
     * Creates a new instance of NotificacionesVerListado
     */
    public NotificacionVerDetalles() {
    }

    public String getOrigen_seleccionado() {
        return origen_seleccionado;
    }

    public void setOrigen_seleccionado(String origen_seleccionado) {
        this.origen_seleccionado = origen_seleccionado;
    }

    public String getTipo_seleccionado() {
        return tipo_seleccionado;
    }

    public void setTipo_seleccionado(String tipo_seleccionado) {
        this.tipo_seleccionado = tipo_seleccionado;
    }

    public String getFecha_seleccionado() {
        return fecha_seleccionado;
    }

    public void setFecha_seleccionado(String fecha_seleccionado) {
        this.fecha_seleccionado = fecha_seleccionado;
    }

    public String getDetalleCompleto_seleccionado() {
        return detalleCompleto_seleccionado;
    }

    public void setDetalleCompleto_seleccionado(String detalleCompleto_seleccionado) {
        this.detalleCompleto_seleccionado = detalleCompleto_seleccionado;
    }

    public boolean isRevisada_seleccionado() {
        return revisada_seleccionado;
    }

    public void setRevisada_seleccionado(boolean revisada_seleccionado) {
        this.revisada_seleccionado = revisada_seleccionado;
    }

    public boolean isResuelta_seleccionado() {
        return resuelta_seleccionado;
    }

    public void setResuelta_seleccionado(boolean resuelta_seleccionado) {
        this.resuelta_seleccionado = resuelta_seleccionado;
    }

    public Integer getNumNotif() {
        return numNotif;
    }

    public void setNumNotif(Integer numNotif) {
        this.numNotif = numNotif;
    }

    public StreamedContent getImagen() {
        //this.imagen = (StreamedContent)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("imagen");
        return imagen;
    }
    
}
