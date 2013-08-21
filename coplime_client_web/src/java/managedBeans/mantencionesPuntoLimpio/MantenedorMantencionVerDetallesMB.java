/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantencionesPuntoLimpio;

import entities.MantencionPuntoLimpio;
import java.util.Calendar;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudMantencionPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorMantencionVerDetallesMB")
@RequestScoped
public class MantenedorMantencionVerDetallesMB {
    @EJB
    CrudMantencionPuntoLimpioLocal crudMantencion;
    
    @Inject
    private MantenedorMantencionConversation mantMantenciones;
    
    private Integer num;
    
    private String puntoLimpio;
    
    private String fecha;
    
    private String operario;
    
    private String detalles;
    
    
    @PostConstruct
    public void init() {
        this.num = this.mantMantenciones.getIdMantencionDetalles();
        
        cargarDatosMantencion();
    }
    
    private void cargarDatosMantencion(){
        MantencionPuntoLimpio rev = this.crudMantencion.getMantencionById(num);
        if (rev == null) {
             volver();
             return;
        }
        this.puntoLimpio = Integer.toString(rev.getPuntoLimpio().getId()).concat(" - ").concat(rev.getPuntoLimpio().getNombre());
        this.detalles = rev.getComentarios();
        Calendar f = rev.getFecha();
        this.fecha = Integer.toString(f.get(Calendar.DAY_OF_MONTH)).concat("-").concat(
                    f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)).concat(
                "-").concat(Integer.toString(f.get(Calendar.YEAR)));
        String rut = Integer.toString(rev.getOperarioMantencion().getUsuario().getRut());
        String nombre = rev.getOperarioMantencion().getUsuario().getNombre();
        String apellido1 = rev.getOperarioMantencion().getUsuario().getApellido1();
        //String apellido2 = rev.getOperarioMantencion().getUsuario().getApellido2();
        this.operario = rut.concat(" - ").concat(nombre).concat(" ").concat(apellido1);
    }
    
    public void volver() {
        this.mantMantenciones.endConversation();
        CommonFunctions.goToPage("/faces/users/verMantenciones.xhtml");
    }
    
    
    /**
     * Creates a new instance of MantenedorMantencionVerDetallesMB
     */
    public MantenedorMantencionVerDetallesMB() {
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

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }
    
    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
