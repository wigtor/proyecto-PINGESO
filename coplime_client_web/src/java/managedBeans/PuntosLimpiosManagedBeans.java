/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.IOException;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author victor
 */
@ManagedBean
@Named(value = "puntosLimpiosManagedBeans")
@RequestScoped
public class PuntosLimpiosManagedBeans {

    /**
     * Creates a new instance of PuntosLimpiosManagedBeans
     */
    public PuntosLimpiosManagedBeans() {
    }
    public void agregar() {
       ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
       try {
           externalContext.redirect(externalContext.getRequestContextPath() + "/faces/admin/agregarPuntoLimpio.xhtml");
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }
}
