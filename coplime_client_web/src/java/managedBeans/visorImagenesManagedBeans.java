/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author victor
 */
@Named(value = "visorImagenesManagedBeans")
@ViewScoped
public class visorImagenesManagedBeans implements Serializable {
    private DefaultStreamedContent imagen;

    public StreamedContent getImagen() {
        return imagen;
    }

    public void setImagen(DefaultStreamedContent imagen) {
        this.imagen = imagen;
    }
    
    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public visorImagenesManagedBeans() {
    }
    
}
