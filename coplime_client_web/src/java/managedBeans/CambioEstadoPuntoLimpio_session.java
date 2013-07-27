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
@Named(value = "cambioEstadoPuntoLimpio_session")
@SessionScoped
public class CambioEstadoPuntoLimpio_session implements Serializable {

    private Integer idPuntoLimpioToChange;
    private Integer idContenedorToChange;
    private Integer idEstadoToChange;
    
    /**
     * Creates a new instance of CambioEstadoPuntoLimpio_session
     */
    public CambioEstadoPuntoLimpio_session() {
    }
    
    public void limpiarCampos() {
        this.idPuntoLimpioToChange = null;
    }
    
    public Integer getIdPuntoLimpioToChange() {
        return idPuntoLimpioToChange;
    }

    public void setIdPuntoLimpioToChange(Integer idPuntoLimpioToChange) {
        this.idPuntoLimpioToChange = idPuntoLimpioToChange;
    }
    
    public Integer getIdContenedorToChange() {
        return idContenedorToChange;
    }

    public void setIdContenedorToChange(Integer idContenedorToChange) {
        this.idContenedorToChange = idContenedorToChange;
    }

    public Integer getIdEstadoToChange() {
        return idEstadoToChange;
    }

    public void setIdEstadoToChange(Integer idEstadoToChange) {
        this.idEstadoToChange = idEstadoToChange;
    }
    
}
