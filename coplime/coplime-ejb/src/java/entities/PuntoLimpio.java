/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @NamedQuery(name="PuntoLimpio.findByName", query="SELECT u FROM PuntoLimpio u WHERE u.nombre = :nombre") 
})
public class PuntoLimpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String comuna;
    
    @Column(nullable = false)
    private String ubicacion;
    
    @Column(nullable = false)
    private int num;
    
    private int latitud;
    
    private int longitud;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado estadoGlobal;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private Inspector inspectorEncargado;
    
    @OneToMany(mappedBy = "puntoLimpio")
    private List<Contenedor> contenedores;
    
    @OneToMany(mappedBy = "puntoLimpio")
    private List<MantencionPuntoLimpio> mantenciones;

    @OneToMany(mappedBy = "puntoLimpio")
    private List<RevisionPuntoLimpio> revisiones;
    
    @OneToMany(mappedBy = "puntoLimpio")
    private List<SolicitudMantencion> solicitudesMantencion;
    
    @OneToMany(mappedBy = "puntoLimpio")
    private List<Notificacion> notificaciones;
    
    public PuntoLimpio() {
    }
    
    public PuntoLimpio(String nombre, String comuna, String ubicacion, int numero) {
        this.nombre = nombre;
        this.comuna = comuna;
        this.ubicacion = ubicacion;
        this.num = numero;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
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

    public Date getFechaProxRevision() {
        return fechaProxRevision;
    }

    public void setFechaProxRevision(Date fechaProxRevision) {
        this.fechaProxRevision = fechaProxRevision;
    }
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaProxRevision;

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
