/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.NotificacionPojo;
import entities.Notificacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sessionBeans.NotificadorLocal;

/**
 *
 * @author victor
 */
@Named(value = "NotificacionVerListado")
@RequestScoped
public class NotificacionVerListado extends commonFunctions implements Serializable {
    @EJB
    private NotificadorLocal notificador;
    
    private Collection<NotificacionPojo> listaAllNotif;
    
    private Collection<NotificacionPojo> filteredNotif;
    
    @PostConstruct
    public void init() {
        System.out.println("Ejecutando init de NotificacionesVerListado");
        
        String username = getUsuarioLogueado();
        if (username == null) {
            goToIndex();
        }

        Collection<Notificacion> listaTemp = notificador.getAllNotificaciones(username);
        this.listaAllNotif = llenarListaNotificaciones(listaTemp);
        
        System.out.println("listo init() de NotificacionesVerListado");
    }
    
    private Collection<NotificacionPojo> llenarListaNotificaciones(Collection<Notificacion> listaTemp) {
        NotificacionPojo notifTemp;
        String str_temp;
        Collection<NotificacionPojo> listaResult = new ArrayList();
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
            listaResult.add(notifTemp);
        }
        return listaResult;
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
     * Creates a new instance of NotificacionVerListado
     */
    public NotificacionVerListado() {
    }
    
    public Collection<NotificacionPojo> getListaAllNotif() {
        return listaAllNotif;
    }
    
    public void setListaAllNotif(Collection<NotificacionPojo> listaAllNotif) {
        this.listaAllNotif = listaAllNotif;
    }

    public Collection<NotificacionPojo> getFilteredNotif() {
        System.out.println("Se llamó getFilteredNotif");
        return filteredNotif;
    }

    public void setFilteredNotif(Collection<NotificacionPojo> filteredNotif) {
        System.out.println("Se llamó setFilteredNotif");
        this.filteredNotif = filteredNotif;
    }
}
