/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.cambioEstadoPuntoLimpio;

import ObjectsForManagedBeans.ContenedorPojo;
import ObjectsForManagedBeans.PuntoLimpioPojo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<ContenedorPojo> listaContenedoresModificados;
    private String detalle;
    
    /**
     * Creates a new instance of CambioEstadoPuntoLimpio_session
     */
    public CambioEstadoPuntoLimpio_session() {
        this.listaContenedoresModificados = new ArrayList();
    }
    
    public void limpiarCampos() {
        this.idPuntoLimpioToChange = null;
        this.idContenedorToChange = null;
        this.idEstadoToChange = null;
        this.detalle = null;
        this.listaContenedoresModificados.clear();
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

    public List<ContenedorPojo> getListaContenedoresModificados() {
        return listaContenedoresModificados;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
}
