/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.notificaciones;

import entities.Notificacion;
import entities.NotificacionDeUsuario;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import otros.CommonFunctions;
import sessionBeans.NotificadorLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorNotificacionVerDetallesMB")
@RequestScoped
public class MantenedorNotificacionVerDetallesMB implements Serializable {
    @EJB
    private NotificadorLocal notificador;
    
    @Inject
    private MantenedorNotificacionesConversation mantNotificacion;
    
    private Integer numNotif;
    private String origen_seleccionado;
    private String tipo_seleccionado;
    private String fecha_seleccionado;
    private String detalleCompleto_seleccionado;
    private boolean revisada_seleccionado;
    private boolean resuelta_seleccionado;
    private String path_imagen;
    private String tipo_imagen;
    private DefaultStreamedContent imagen;
    
    
    
    @PostConstruct
    public void init() {
        if (mantNotificacion.getIdNotificacionSeleccionada()!= null) {
            numNotif = mantNotificacion.getIdNotificacionSeleccionada();
            cargarDatosNotificacion();
            cargarImagen();
        }
        else {
            //MOSTRAR ERROR
            
            volverToLista();
        }
    }
    
    private void cargarDatosNotificacion() {
        Notificacion notifTemp = notificador.getNotificacion(this.numNotif);
        this.detalleCompleto_seleccionado = notifTemp.getComentario();
        this.fecha_seleccionado = Integer.toString(notifTemp.getFechaHora().get(Calendar.DAY_OF_MONTH)).concat(
                    "-").concat(
                    notifTemp.getFechaHora().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)).concat(
                    "-").concat(
                    Integer.toString(notifTemp.getFechaHora().get(Calendar.YEAR))).concat(
                    " a las ").concat(
                    Integer.toString(notifTemp.getFechaHora().get(Calendar.HOUR))).concat(
                    ":").concat(
                    Integer.toString(notifTemp.getFechaHora().get(Calendar.MINUTE)));
        this.tipo_seleccionado = notifTemp.getTipoIncidencia().getNombreIncidencia();
        this.origen_seleccionado = notificador.getOrigenIncidencia(notifTemp);
        this.resuelta_seleccionado = notifTemp.isResuelto();
        this.revisada_seleccionado = true; //Por defecto se revisa al abrir esta ventana

        if (notificador.isNotificacionUsuario(notifTemp)) {
            NotificacionDeUsuario notifUsuarioTemp = (NotificacionDeUsuario)notifTemp;
            this.path_imagen = notifUsuarioTemp.getImagenAdjunta();
            this.tipo_imagen = notifUsuarioTemp.getTipoImagen();
        }
    }
    
    private void cargarImagen() {
        if (this.path_imagen == null) {
            return;
        }
        try {
            FileInputStream lector;
            File archivoImagen = new File(this.path_imagen);
            lector = new FileInputStream(archivoImagen);
            byte[] resultado = new byte[(int) archivoImagen.length()];
            lector.read(resultado);
            lector.close();
            InputStream datosImagenStream = new ByteArrayInputStream(resultado);
            imagen = new DefaultStreamedContent(datosImagenStream, this.tipo_imagen);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MantenedorNotificacionVerDetallesMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MantenedorNotificacionVerDetallesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void volverToListaAndSave() {
        mantNotificacion.endConversation();
        notificador.checkRevisada(mantNotificacion.getIdNotificacionSeleccionada(), revisada_seleccionado);
        notificador.checkResuelta(mantNotificacion.getIdNotificacionSeleccionada(), resuelta_seleccionado);
        volverToLista();
    }
    
    public void volverToLista() {
        mantNotificacion.limpiarDatos();
        mantNotificacion.endConversation();
        CommonFunctions.goToPage("/faces/users/verNotificaciones.xhtml");
    }
    
    public void eliminar(Integer num) {
        //Este método debe eliminar la notificación que se está viendo actualmente
    }
    
    
    /**
     * Creates a new instance of NotificacionesVerListado
     */
    public MantenedorNotificacionVerDetallesMB() {
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
        return this.imagen;
    }
    
    public String getTipoImagen() {
        return this.tipo_imagen;
    }
}
