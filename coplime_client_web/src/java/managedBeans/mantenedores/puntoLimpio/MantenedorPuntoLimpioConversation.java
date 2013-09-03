/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.puntoLimpio;

import ObjectsForManagedBeans.ContenedorPojo;
import ObjectsForManagedBeans.PuntoLimpioPojo;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import managedBeans.AbstractConversation;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorPuntoLimpioConversation")
@ConversationScoped
public class MantenedorPuntoLimpioConversation extends AbstractConversation implements Serializable {
    
    public static final int AGREGAR = 1;
    public static final int EDITAR = 2;
    public static final int VER = 3;
    
    private PuntoLimpioPojo pto_creando;
    private List<ContenedorPojo> contenedores_creando;
    private List<ContenedorPojo> contenedores_eliminados;
    private Integer idPuntoLimpioDetalles;
    private int state;
    private boolean primeraCarga;
    
    /**
     * Creates a new instance of MantenedorPuntoLimpioConversation
     */
    public MantenedorPuntoLimpioConversation() {
        //System.out.println("Se contruyó un MantenedorPuntoLimpioConversation");
        contenedores_creando = new LinkedList<>();
        contenedores_eliminados = new LinkedList<>();
    }
    
    public void limpiarDatos() {
        pto_creando = null;
        contenedores_creando.clear();
        idPuntoLimpioDetalles = null;
    }
    
    public void eliminarContenedor(Integer id) {
        for(ContenedorPojo temp : contenedores_creando) {
            if (temp.getId().intValue() == id.intValue()) {
                contenedores_creando.remove(temp);
                contenedores_eliminados.add(temp);
                break;
            }
        }
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

    public List<ContenedorPojo> getContenedores_eliminados() {
        return contenedores_eliminados;
    }

    public void setContenedores_eliminados(List<ContenedorPojo> contenedores_eliminados) {
        this.contenedores_eliminados = contenedores_eliminados;
    }

    public Integer getIdPuntoLimpioDetalles() {
        return idPuntoLimpioDetalles;
    }

    public void setIdPuntoLimpioDetalles(Integer idPuntoLimpioDetalles) {
        this.idPuntoLimpioDetalles = idPuntoLimpioDetalles;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isPrimeraCarga() {
        return primeraCarga;
    }

    public void setPrimeraCarga(boolean primeraCarga) {
        this.primeraCarga = primeraCarga;
    }
    
}
