/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.puntoLimpio;

import entities.Contenedor;
import entities.PuntoLimpio;
import entities.RevisionPuntoLimpio;
import entities.Usuario;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorPuntoLimpioVerDetallesMB")
@RequestScoped
public class MantenedorPuntoLimpioVerDetallesMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private MantenedorPuntoLimpioConversation mantPtoLimpio;
    
    private Integer num;
    private String nombre;
    private String comuna;
    private String direccion;
    private String fechaUltimaRevision;
    private String fechaProximaRevision;
    private String estado;
    private String inspectorEncargado;
    private Integer numMantencionesRealizadas;
    private Integer numRevisionesRealizadas;
    private Integer numContenedores;
    
    
    /**
     * Creates a new instance of MantenedorPuntoLimpioAgregar
     */
    public MantenedorPuntoLimpioVerDetallesMB() {
        System.out.println("Se ha instanciado un MantenedorPuntoLimpioVerDetalles");
    }
    
    @PostConstruct
    public void init() {
        if (mantPtoLimpio.getIdPuntoLimpioDetalles() != null) {
            Integer numPtoLimpio = mantPtoLimpio.getIdPuntoLimpioDetalles();
            cargarDatosPuntoLimpio(numPtoLimpio);
        }
        else {
            //MOSTRAR ERROR
            
            volverToLista();
        }
    }
    
    public void goToEditar() {
        //this.mantPtoLimpio.beginConversation();
        Integer numPtoLimpio = this.mantPtoLimpio.getIdPuntoLimpioDetalles();
        System.out.println("NÚMERO DE PUNTO LIMPIO: "+numPtoLimpio);
        PuntoLimpio ptoEdit = crudPuntoLimpio.getPuntoLimpioByNum(numPtoLimpio);
        
        if (ptoEdit != null) {
            CommonFunctions.goToPage("/faces/users/admin/editarPuntoLimpio.xhtml");
            
        }
        else {
            //MOSTRAR ERROR
            
            volverToLista();
        }
    }
    
    private void cargarDatosPuntoLimpio(Integer numPtoLimpio) {
        PuntoLimpio ptoLimpioSelec = crudPuntoLimpio.getPuntoLimpioByNum(numPtoLimpio);
        if (ptoLimpioSelec == null) {
            volverToLista();
            return;
        }
        this.num = ptoLimpioSelec.getId();
        this.nombre = ptoLimpioSelec.getNombre();
        this.comuna = ptoLimpioSelec.getComuna().getNombre();
        this.direccion = ptoLimpioSelec.getUbicacion();
        this.estado = ptoLimpioSelec.getEstadoGlobal().getNombreEstado();
        Usuario userEncargado = ptoLimpioSelec.getInspectorEncargado().getUsuario();
        this.inspectorEncargado = Integer.toString(userEncargado.getRut()).concat(" - ")
                .concat(userEncargado.getNombre()).concat(" ").concat(userEncargado.getApellido1());
        this.numMantencionesRealizadas = ptoLimpioSelec.getMantenciones().size();
        this.numRevisionesRealizadas = ptoLimpioSelec.getRevisiones().size();
        Calendar f = ptoLimpioSelec.getFechaProxRevision();
        this.fechaProximaRevision = Integer.toString(f.get(Calendar.DAY_OF_MONTH)).concat("-").concat(
                    f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH));
        List<RevisionPuntoLimpio> listaRevisiones = ptoLimpioSelec.getRevisiones();
        this.fechaUltimaRevision = "Nunca revisado";
        if (listaRevisiones != null) {
            if (!listaRevisiones.isEmpty()) {
                Calendar f2 = listaRevisiones.get(listaRevisiones.size()-1).getFecha();
                this.fechaUltimaRevision = f2.get(Calendar.DAY_OF_MONTH)
                    +"-"
                    +f2.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
            }
        }
        
        List<Contenedor> listaContenedores = ptoLimpioSelec.getContenedores();
        this.numContenedores = listaContenedores.size();
        
        //FALTA CARGAR LA LISTA DE REVISIONES Y LISTA DE CONTENEDORES
        
        
    }
    
    public void volverToLista() {
        mantPtoLimpio.endConversation();
        mantPtoLimpio.limpiarDatos();
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaUltimaRevision() {
        return fechaUltimaRevision;
    }

    public void setFechaUltimaRevision(String fechaUltimaRevision) {
        this.fechaUltimaRevision = fechaUltimaRevision;
    }

    public String getFechaProximaRevision() {
        return fechaProximaRevision;
    }

    public void setFechaProximaRevision(String fechaProximaRevision) {
        this.fechaProximaRevision = fechaProximaRevision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getInspectorEncargado() {
        return inspectorEncargado;
    }

    public void setInspectorEncargado(String inspectorEncargado) {
        this.inspectorEncargado = inspectorEncargado;
    }

    public Integer getNumMantencionesRealizadas() {
        return numMantencionesRealizadas;
    }

    public void setNumMantencionesRealizadas(Integer numMantencionesRealizadas) {
        this.numMantencionesRealizadas = numMantencionesRealizadas;
    }

    public Integer getNumRevisionesRealizadas() {
        return numRevisionesRealizadas;
    }

    public void setNumRevisionesRealizadas(Integer numRevisionesRealizadas) {
        this.numRevisionesRealizadas = numRevisionesRealizadas;
    }

    public Integer getNumContenedores() {
        return numContenedores;
    }

    public void setNumContenedores(Integer numContenedores) {
        this.numContenedores = numContenedores;
    }
    
}
