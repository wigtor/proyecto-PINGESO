/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "punto_limpio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PuntoLimpio.findAll", query = "SELECT p FROM PuntoLimpio p"),
    @NamedQuery(name = "PuntoLimpio.findByNumPuntoLimpio", query = "SELECT p FROM PuntoLimpio p WHERE p.numPuntoLimpio = :numPuntoLimpio"),
    @NamedQuery(name = "PuntoLimpio.findByNombrePuntoLimpio", query = "SELECT p FROM PuntoLimpio p WHERE p.nombrePuntoLimpio = :nombrePuntoLimpio"),
    @NamedQuery(name = "PuntoLimpio.findByComunaPuntoLimpio", query = "SELECT p FROM PuntoLimpio p WHERE p.comunaPuntoLimpio = :comunaPuntoLimpio"),
    @NamedQuery(name = "PuntoLimpio.findByUbicacionPuntoLimpio", query = "SELECT p FROM PuntoLimpio p WHERE p.ubicacionPuntoLimpio = :ubicacionPuntoLimpio"),
    @NamedQuery(name = "PuntoLimpio.findByLatitudPuntoLimpio", query = "SELECT p FROM PuntoLimpio p WHERE p.latitudPuntoLimpio = :latitudPuntoLimpio"),
    @NamedQuery(name = "PuntoLimpio.findByLongitudPuntoLimpio", query = "SELECT p FROM PuntoLimpio p WHERE p.longitudPuntoLimpio = :longitudPuntoLimpio"),
    @NamedQuery(name = "PuntoLimpio.findByEstadoGlobalPuntoLimpio", query = "SELECT p FROM PuntoLimpio p WHERE p.estadoGlobalPuntoLimpio = :estadoGlobalPuntoLimpio"),
    @NamedQuery(name = "PuntoLimpio.findByFechaProximaRevisionPuntoLimpio", query = "SELECT p FROM PuntoLimpio p WHERE p.fechaProximaRevisionPuntoLimpio = :fechaProximaRevisionPuntoLimpio")})
public class PuntoLimpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_punto_limpio")
    private Integer numPuntoLimpio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "nombre_punto_limpio")
    private String nombrePuntoLimpio;
    @Size(max = 254)
    @Column(name = "comuna_punto_limpio")
    private String comunaPuntoLimpio;
    @Size(max = 254)
    @Column(name = "ubicacion_punto_limpio")
    private String ubicacionPuntoLimpio;
    @Column(name = "latitud_punto_limpio")
    private Integer latitudPuntoLimpio;
    @Column(name = "longitud_punto_limpio")
    private Integer longitudPuntoLimpio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "estado_global_punto_limpio")
    private String estadoGlobalPuntoLimpio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_proxima_revision_punto_limpio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProximaRevisionPuntoLimpio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numPuntoLimpio")
    private Collection<SolicitudMantencion> solicitudMantencionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numPuntoLimpio")
    private Collection<MantencionPuntoLimpio> mantencionPuntoLimpioCollection;
    @OneToMany(mappedBy = "numPuntoLimpio")
    private Collection<Notificacion> notificacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numPuntoLimpio")
    private Collection<Contenedor> contenedorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numPuntoLimpio")
    private Collection<RevisionPuntoLimpio> revisionPuntoLimpioCollection;
    @JoinColumn(name = "rut", referencedColumnName = "rut")
    @ManyToOne
    private Inspector rut;
    @JoinColumn(name = "id_estado_punto_limpio", referencedColumnName = "id_estado_punto_limpio")
    @ManyToOne(optional = false)
    private EstadoPuntoLimpio idEstadoPuntoLimpio;

    public PuntoLimpio() {
    }

    public PuntoLimpio(Integer numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }

    public PuntoLimpio(Integer numPuntoLimpio, String nombrePuntoLimpio, String estadoGlobalPuntoLimpio, Date fechaProximaRevisionPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
        this.nombrePuntoLimpio = nombrePuntoLimpio;
        this.estadoGlobalPuntoLimpio = estadoGlobalPuntoLimpio;
        this.fechaProximaRevisionPuntoLimpio = fechaProximaRevisionPuntoLimpio;
    }

    public Integer getNumPuntoLimpio() {
        return numPuntoLimpio;
    }

    public void setNumPuntoLimpio(Integer numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }

    public String getNombrePuntoLimpio() {
        return nombrePuntoLimpio;
    }

    public void setNombrePuntoLimpio(String nombrePuntoLimpio) {
        this.nombrePuntoLimpio = nombrePuntoLimpio;
    }

    public String getComunaPuntoLimpio() {
        return comunaPuntoLimpio;
    }

    public void setComunaPuntoLimpio(String comunaPuntoLimpio) {
        this.comunaPuntoLimpio = comunaPuntoLimpio;
    }

    public String getUbicacionPuntoLimpio() {
        return ubicacionPuntoLimpio;
    }

    public void setUbicacionPuntoLimpio(String ubicacionPuntoLimpio) {
        this.ubicacionPuntoLimpio = ubicacionPuntoLimpio;
    }

    public Integer getLatitudPuntoLimpio() {
        return latitudPuntoLimpio;
    }

    public void setLatitudPuntoLimpio(Integer latitudPuntoLimpio) {
        this.latitudPuntoLimpio = latitudPuntoLimpio;
    }

    public Integer getLongitudPuntoLimpio() {
        return longitudPuntoLimpio;
    }

    public void setLongitudPuntoLimpio(Integer longitudPuntoLimpio) {
        this.longitudPuntoLimpio = longitudPuntoLimpio;
    }

    public String getEstadoGlobalPuntoLimpio() {
        return estadoGlobalPuntoLimpio;
    }

    public void setEstadoGlobalPuntoLimpio(String estadoGlobalPuntoLimpio) {
        this.estadoGlobalPuntoLimpio = estadoGlobalPuntoLimpio;
    }

    public Date getFechaProximaRevisionPuntoLimpio() {
        return fechaProximaRevisionPuntoLimpio;
    }

    public void setFechaProximaRevisionPuntoLimpio(Date fechaProximaRevisionPuntoLimpio) {
        this.fechaProximaRevisionPuntoLimpio = fechaProximaRevisionPuntoLimpio;
    }

    @XmlTransient
    public Collection<SolicitudMantencion> getSolicitudMantencionCollection() {
        return solicitudMantencionCollection;
    }

    public void setSolicitudMantencionCollection(Collection<SolicitudMantencion> solicitudMantencionCollection) {
        this.solicitudMantencionCollection = solicitudMantencionCollection;
    }

    @XmlTransient
    public Collection<MantencionPuntoLimpio> getMantencionPuntoLimpioCollection() {
        return mantencionPuntoLimpioCollection;
    }

    public void setMantencionPuntoLimpioCollection(Collection<MantencionPuntoLimpio> mantencionPuntoLimpioCollection) {
        this.mantencionPuntoLimpioCollection = mantencionPuntoLimpioCollection;
    }

    @XmlTransient
    public Collection<Notificacion> getNotificacionCollection() {
        return notificacionCollection;
    }

    public void setNotificacionCollection(Collection<Notificacion> notificacionCollection) {
        this.notificacionCollection = notificacionCollection;
    }

    @XmlTransient
    public Collection<Contenedor> getContenedorCollection() {
        return contenedorCollection;
    }

    public void setContenedorCollection(Collection<Contenedor> contenedorCollection) {
        this.contenedorCollection = contenedorCollection;
    }

    @XmlTransient
    public Collection<RevisionPuntoLimpio> getRevisionPuntoLimpioCollection() {
        return revisionPuntoLimpioCollection;
    }

    public void setRevisionPuntoLimpioCollection(Collection<RevisionPuntoLimpio> revisionPuntoLimpioCollection) {
        this.revisionPuntoLimpioCollection = revisionPuntoLimpioCollection;
    }

    public Inspector getRut() {
        return rut;
    }

    public void setRut(Inspector rut) {
        this.rut = rut;
    }

    public EstadoPuntoLimpio getIdEstadoPuntoLimpio() {
        return idEstadoPuntoLimpio;
    }

    public void setIdEstadoPuntoLimpio(EstadoPuntoLimpio idEstadoPuntoLimpio) {
        this.idEstadoPuntoLimpio = idEstadoPuntoLimpio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numPuntoLimpio != null ? numPuntoLimpio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PuntoLimpio)) {
            return false;
        }
        PuntoLimpio other = (PuntoLimpio) object;
        if ((this.numPuntoLimpio == null && other.numPuntoLimpio != null) || (this.numPuntoLimpio != null && !this.numPuntoLimpio.equals(other.numPuntoLimpio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PuntoLimpio[ numPuntoLimpio=" + numPuntoLimpio + " ]";
    }
    
}
