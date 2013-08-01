/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.revisionesPuntoLimpio;

import ObjectsForManagedBeans.RevisionPojo;
import entities.RevisionPuntoLimpio;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudRevisionPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorRevisionVerDetalles")
@RequestScoped
public class MantenedorRevisionVerDetalles {
    @EJB
    CrudRevisionPuntoLimpioLocal crudRevision;
    
    @Inject
    private MantenedorRevision mantRevisiones;
    
    private Integer num;
    
    private String puntoLimpio;
    
    private String fecha;
    
    private String inspector;
    
    private String detalles;
    
    
    @PostConstruct
    public void init() {
        this.num = this.mantRevisiones.getIdRevisionDetalles();
        
        cargarDatosRevision();
    }
    
    private void cargarDatosRevision(){
        RevisionPuntoLimpio rev = this.crudRevision.getRevisionById(num);
        if (rev == null) {
             volver();
             return;
        }
        this.puntoLimpio = Integer.toString(rev.getPuntoLimpio().getId()).concat(" - ").concat(rev.getPuntoLimpio().getNombre());
        this.detalles = rev.getDetalles();
        Calendar f = rev.getFecha();
        this.fecha = Integer.toString(f.get(Calendar.DAY_OF_MONTH)).concat("-").concat(
                    f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)).concat(
                "-").concat(Integer.toString(f.get(Calendar.YEAR)));
        String rut = Integer.toString(rev.getInspectorRevisor().getUsuario().getRut());
        String nombre = rev.getInspectorRevisor().getUsuario().getNombre();
        String apellido1 = rev.getInspectorRevisor().getUsuario().getApellido1();
        String apellido2 = rev.getInspectorRevisor().getUsuario().getApellido2();
        this.inspector = rut.concat(" - ").concat(nombre).concat(" ").concat(apellido1);
    }
    
    public void volver() {
       CommonFunctions.goToPage("/faces/users/verRevisiones.xhtml");
    }
    
    
    /**
     * Creates a new instance of MantenedorRevisionVerListado
     */
    public MantenedorRevisionVerDetalles() {
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPuntoLimpio() {
        return puntoLimpio;
    }

    public void setPuntoLimpio(String puntoLimpio) {
        this.puntoLimpio = puntoLimpio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }
    
    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
