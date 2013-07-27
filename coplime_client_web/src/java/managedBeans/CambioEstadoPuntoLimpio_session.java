/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

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
    private Integer nvo_idEstadoGlobal;
    private Integer old_idEstadoGlobal;
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

    public Integer getNvo_idEstadoGlobal() {
        return nvo_idEstadoGlobal;
    }

    public void setNvo_idEstadoGlobal(Integer nvo_idEstadoGlobal) {
        this.nvo_idEstadoGlobal = nvo_idEstadoGlobal;
    }
    
    public Integer getOld_idEstadoGlobal() {
        return old_idEstadoGlobal;
    }

    public void setOld_idEstadoGlobal(Integer old_idEstadoGlobal) {
        this.old_idEstadoGlobal = old_idEstadoGlobal;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
}
