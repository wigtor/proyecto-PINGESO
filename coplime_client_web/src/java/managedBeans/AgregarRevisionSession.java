/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.PuntoLimpioPojo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author victor
 */
@Named(value = "AgregarRevisionSession")
@SessionScoped
public class AgregarRevisionSession implements Serializable {

    private PuntoLimpioPojo puntoLimpioToChange;
    /**
     * Creates a new instance of AgregarRevisionSession
     */
    public AgregarRevisionSession() {
    }

    public PuntoLimpioPojo getPuntoLimpioToChange() {
        return puntoLimpioToChange;
    }

    public void setPuntoLimpioToChange(PuntoLimpioPojo puntoLimpioToChange) {
        this.puntoLimpioToChange = puntoLimpioToChange;
    }
    
    
}
