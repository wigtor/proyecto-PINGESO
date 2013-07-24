/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
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
    
    @Inject
    private visorImagenesManagedBeans imb;
    
    private Integer numNotif;
    private String origen_seleccionado;
    private String tipo_seleccionado;
    private String fecha_seleccionado;
    private String detalleCompleto_seleccionado;
    private boolean revisada_seleccionado;
    private boolean resuelta_seleccionado;
    
    
    private StreamedContent imagen;
    
    public void setNotifTemp(NotificacionDeUsuario notifTemp) {
        FileInputStream lector = null;
        try {
            
            String path = notifTemp.getImagenAdjunta();
        
            System.out.println("Path de imagen a abrir: " + path);
            File archivoImagen = new File(path);
            lector = new FileInputStream(archivoImagen);
            byte[] resultado = new byte[(int)archivoImagen.length()];
            lector.read(resultado);
            lector.close();
            InputStream datosImagenStream = new ByteArrayInputStream(resultado);
            imagen = new DefaultStreamedContent(datosImagenStream, notifTemp.getTipoImagen());
            System.out.println("se abrió el defaultStreamedContent");
            
        } catch (FileNotFoundException ex) {
             System.out.println("se abrió el FileNotFoundException");
            Logger.getLogger(visorImagenesManagedBeans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("se abrió el IOException");
            Logger.getLogger(visorImagenesManagedBeans.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                lector.close();
            } catch (IOException ex) {
                Logger.getLogger(visorImagenesManagedBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Ejecutando init de NotificacionesVerDetalles");
        
        if (isGetMethod()) {
            System.out.println("Se hizo un GET");
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
                System.out.println("Se seteo la notificación en imv");
                imb.setNotifTemp(notifUsuarioTemp);
                this.imagen = imb.getImagen();
            }
        }
        else {
            System.out.println("Se hizo un POST");
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
        /*
        try {
            File archivoImagen = new File("C:\\glassfish3\\jdk7\\uploads_coplime\\img_notif_1374215183342.jpeg");
            FileInputStream lector = new FileInputStream(archivoImagen);
            byte[] resultado = new byte[(int)archivoImagen.length()];
            lector.read(resultado);
            InputStream datosImagenStream = new ByteArrayInputStream(resultado);
            this.imagen = new DefaultStreamedContent(datosImagenStream, "image/jpeg");
            System.out.println("Se abrió la imagen: +" + this.imagen);
            return this.imagen;
        }
        catch (Exception e) {
            return null;
        }
        */
        
        if (this.imagen != null) {
            System.out.println("this.image != null");
            return this.imagen;
        }
        else {
            System.out.println("this.image es NULL");
            System.out.println(this.numNotif);
            return imb.getImagen();
        }
        
    }
    
}
