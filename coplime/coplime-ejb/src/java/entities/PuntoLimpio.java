/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author victor
 */
@Entity
@NamedQueries( {
    @NamedQuery(name="PuntoLimpio.findByName", query="SELECT u FROM PuntoLimpio u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "PuntoLimpio.checkByNum", query = "SELECT COUNT(U) FROM PuntoLimpio u WHERE u.id = :num"),
    @NamedQuery(name = "PuntoLimpio.checkByNombre", query = "SELECT COUNT(U) FROM PuntoLimpio u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "PuntoLimpio.checkByNombreExcepto", query = "SELECT COUNT(U) FROM PuntoLimpio u WHERE (u.nombre = :nombre) AND (u.id != :idPuntoExcept)")
})
public class PuntoLimpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //El usuario le da la llave primaria
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @ManyToOne
    private Comuna comuna;
    
    @Column(nullable = false)
    private String ubicacion;
    
    private int latitud;
    
    private int longitud;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar fechaProxRevision;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado estadoGlobal;
    
    @ManyToOne
    @JoinColumn
    private Inspector inspectorEncargado;
    
    @OneToMany(mappedBy = "puntoLimpio", cascade = CascadeType.ALL)
    private List<Contenedor> contenedores;
    
    @OneToMany(mappedBy = "puntoLimpio", cascade = CascadeType.ALL)
    private List<MantencionPuntoLimpio> mantenciones;

    @OneToMany(mappedBy = "puntoLimpio", cascade = CascadeType.ALL)
    private List<RevisionPuntoLimpio> revisiones;
    
    @OneToMany(mappedBy = "puntoLimpio", cascade = CascadeType.ALL)
    private List<SolicitudMantencion> solicitudesMantencion;
    
    @OneToMany(mappedBy = "puntoLimpio", cascade = CascadeType.ALL)
    private List<Notificacion> notificaciones;
    
    public PuntoLimpio() {
        this.contenedores = new LinkedList<>();
        this.mantenciones = new LinkedList<>();
        this.solicitudesMantencion =  new LinkedList<>();
        this.notificaciones =  new LinkedList<>();
        this.revisiones =  new LinkedList<>();
    }
    
    public PuntoLimpio(String nombre, String ubicacion, int numero) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.id = numero;
        this.contenedores = new LinkedList<>();
        this.mantenciones = new LinkedList<>();
        this.solicitudesMantencion =  new LinkedList<>();
        this.notificaciones =  new LinkedList<>();
        this.revisiones =  new LinkedList<>();
    }
    
    public Estado getEstadoGlobal() {
        return estadoGlobal;
    }

    public void setEstadoGlobal(Estado estadoGlobal) {
        this.estadoGlobal = estadoGlobal;
    }

    public List<Contenedor> getContenedores() {
        return contenedores;
    }

    public void setContenedores(List<Contenedor> contenedores) {
        this.contenedores = contenedores;
    }

    public List<MantencionPuntoLimpio> getMantenciones() {
        return mantenciones;
    }

    public void setMantenciones(List<MantencionPuntoLimpio> mantenciones) {
        this.mantenciones = mantenciones;
    }

    public List<RevisionPuntoLimpio> getRevisiones() {
        return revisiones;
    }

    public void setRevisiones(List<RevisionPuntoLimpio> revisiones) {
        this.revisiones = revisiones;
    }

    public List<SolicitudMantencion> getSolicitudesMantencion() {
        return solicitudesMantencion;
    }

    public void setSolicitudesMantencion(List<SolicitudMantencion> solicitudesMantencion) {
        this.solicitudesMantencion = solicitudesMantencion;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Inspector getInspectorEncargado() {
        return inspectorEncargado;
    }

    public void setInspectorEncargado(Inspector inspectorEncargado) {
        this.inspectorEncargado = inspectorEncargado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public Calendar getFechaProxRevision() {
        return fechaProxRevision;
    }

    public void setFechaProxRevision(Calendar fechaProxRevision) {
        this.fechaProxRevision = fechaProxRevision;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PuntoLimpio)) {
            return false;
        }
        PuntoLimpio other = (PuntoLimpio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PuntoLimpio[ id=" + id + " ]";
    }
    
}
