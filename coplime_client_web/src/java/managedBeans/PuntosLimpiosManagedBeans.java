/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

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
}
