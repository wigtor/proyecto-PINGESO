/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.notificaciones;

import entities.NotificacionDeUsuario;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author victor
 */
@Named(value = "visorImagenesManagedBeans")
@SessionScoped
public class visorImagenesManagedBeans implements Serializable {
    private DefaultStreamedContent imagen;
    private NotificacionDeUsuario notifTemp;

    public StreamedContent getImagen() {
        return imagen;
    }
    
    public void setImagen(DefaultStreamedContent imagen) {
        this.imagen = imagen;
    }

    public NotificacionDeUsuario getNotifTemp() {
        return notifTemp;
    }

    public void setNotifTemp(NotificacionDeUsuario notifTemp) {
        FileInputStream lector = null;
        try {
            this.notifTemp = notifTemp;
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
    
    
    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public visorImagenesManagedBeans() {
        /*
        try {
            File archivoImagen = new File("C:\\glassfish3\\jdk7\\uploads_coplime\\img_notif_1374215183342.jpeg");
            FileInputStream lector = new FileInputStream(archivoImagen);
            byte[] resultado = new byte[(int)archivoImagen.length()];
            lector.read(resultado);
        InputStream datosImagenStream = new ByteArrayInputStream(resultado);
        imagen = new DefaultStreamedContent(datosImagenStream, "image/jpeg");
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("imagen", imagen);
        System.out.println("se abrió el defaultStreamedContent");
        }
        catch (Exception e) {
            System.out.println("Excepción al abrir la imagen de notificación: "+e.getMessage());
            return;
        }*/
        
        System.out.println("SE INSTANCIÓ UN VISOR DE IMAGENES");
    }
    
}
