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
}
