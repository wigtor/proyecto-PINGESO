/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.notificaciones;

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
import javax.inject.Inject;
import javax.inject.Named;
import otros.CommonFunctions;
import sessionBeans.NotificadorLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorNotificacionVerListadoMB")
@RequestScoped
public class MantenedorNotificacionVerListadoMB implements Serializable {
    @EJB
    private NotificadorLocal notificador;
    
    @Inject
    private MantenedorNotificacionesConversation mantNotificaciones;
    
    private Collection<NotificacionPojo> listaAllNotif;
    
    private Collection<NotificacionPojo> filteredNotif;
    
    @PostConstruct
    public void init() {
        System.out.println("Ejecutando init de NotificacionesVerListado");
        cargarListaNotificaciones();
    }
    
    private void cargarListaNotificaciones() {
        String username = CommonFunctions.getUsuarioLogueado();
        if (username == null) {
            CommonFunctions.goToIndex();
        }
        
        Collection<Notificacion> listaTemp = notificador.getAllNotificaciones(username);
        NotificacionPojo notifTemp;
        Calendar f;
        String str_temp;
        Collection<NotificacionPojo> listaResult = new ArrayList();
        for(Notificacion notif_iter : listaTemp) {
            notifTemp = new NotificacionPojo();

            notifTemp.setNum(notif_iter.getId());
            notifTemp.setDetallesCompleto(notif_iter.getComentario());
            str_temp = notif_iter.getComentario();
            if (str_temp.length() > 21) {
                str_temp = str_temp.substring(0, 25).concat("...");
            }
            notifTemp.setDetallesCortado(str_temp);
            f = notif_iter.getFechaHora();
            notifTemp.setFecha(Integer.toString(f.get(Calendar.DAY_OF_MONTH))
                    .concat("-")
                    .concat(f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)));
            notifTemp.setRevisado("No");
            listaResult.add(notifTemp);
        }
        this.listaAllNotif = listaResult;
    }
    
    public void verDetalles(Integer idSeleccionado) {
        mantNotificaciones.setIdNotificacionSeleccionada(idSeleccionado);
        CommonFunctions.goToPage("/faces/users/verDetallesNotificacion.xhtml");
    }
    
    public void volverToListado() {
        mantNotificaciones.limpiarDatos();
        CommonFunctions.goToPage("/faces/users/verNotificaciones.xhtml");
    }
    
    public void eliminar(Integer num) {
        
    }
    
    
    /**
     * Creates a new instance of MantenedorNotificacionVerListadoMB
     */
    public MantenedorNotificacionVerListadoMB() {
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
