/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.NotificacionPojo;
import entities.Notificacion;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sessionBeans.NotificadorLocal;

/**
 *
 * @author victor
 */
@Named(value = "verNotificaciones")
@RequestScoped
public class verNotificaciones extends commonFunctions{
    @EJB
    private NotificadorLocal notificador;
    
    private String origen_seleccionado;
    private String tipo_seleccionado;
    private String fecha_seleccionado;
    private String detalleCompleto_seleccionado;
    private boolean revisada_seleccionado;
    private boolean resuelta_seleccionado;
    
    private Collection<NotificacionPojo> listaAllNotif;
    
    @PostConstruct
    public void init() {
        System.out.println("Ejecutando init de verNotificaciones");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        String username = request.getRemoteUser();
        System.out.println("caca "+ username);
        if (username == null) {
            goToIndex();
        }
        
        Collection<Notificacion> listaTemp = notificador.getAllNotificaciones(username);
        NotificacionPojo notifTemp;
        String str_temp;
        this.listaAllNotif = new ArrayList();
        System.out.println("caca2");
        for(Notificacion notif_iter : listaTemp) {
            notifTemp = new NotificacionPojo();
            
            notifTemp.setNum(notif_iter.getId());
            str_temp = notif_iter.getComentario();
            if (str_temp.length() > 21) {
                str_temp = str_temp.substring(0, 25)+"...";
            }
            notifTemp.setDetallesCortado(str_temp);
            notifTemp.setFecha(notif_iter.getFechaHora().get(Calendar.DAY_OF_MONTH)
                    +"-"
                    +notif_iter.getFechaHora().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                    +"-"
                    +notif_iter.getFechaHora().get(Calendar.YEAR));
            notifTemp.setRevisado("No");
            this.listaAllNotif.add(notifTemp);
            
        }
        System.out.println("listo init()");
    }
    
    public void verDetalles(Integer num) {
        System.out.println("Se quiere ver los detalles: "+num);
        goToPage("/faces/users/detallesNotificacion.xhtml");
    }
    
    public void eliminar(Integer num) {
        
    }
    
    
    /**
     * Creates a new instance of verNotificaciones
     */
    public verNotificaciones() {
    }
    
    public Collection<NotificacionPojo> getListaAllNotif() {
        return listaAllNotif;
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
    
}
