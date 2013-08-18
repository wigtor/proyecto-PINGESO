/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.puntoLimpio;

import ObjectsForManagedBeans.ContenedorPojo;
import ObjectsForManagedBeans.PuntoLimpioPojo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorPuntoLimpioConversation")
@SessionScoped
public class MantenedorPuntoLimpioConversation implements Serializable {
    private PuntoLimpioPojo pto_creando;
    private List<ContenedorPojo> contenedores_creando;
    private Integer idPuntoLimpioDetalles;
    
    /**
     * Creates a new instance of MantenedorPuntoLimpioConversation
     */
    public MantenedorPuntoLimpioConversation() {
        contenedores_creando = new LinkedList<>();
    }
    
    public void limpiarDatos() {
        pto_creando = null;
        contenedores_creando.clear();
        idPuntoLimpioDetalles = null;
    }
    
    public PuntoLimpioPojo getPto_creando() {
        return pto_creando;
    }

    public void setPto_creando(PuntoLimpioPojo pto_creando) {
        this.pto_creando = pto_creando;
    }

    public List<ContenedorPojo> getContenedores_creando() {
        return contenedores_creando;
    }

    public Integer getIdPuntoLimpioDetalles() {
        return idPuntoLimpioDetalles;
    }

    public void setIdPuntoLimpioDetalles(Integer idPuntoLimpioDetalles) {
        this.idPuntoLimpioDetalles = idPuntoLimpioDetalles;
    }
    
}
