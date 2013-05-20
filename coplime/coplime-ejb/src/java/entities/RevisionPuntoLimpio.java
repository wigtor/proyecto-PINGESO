/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "revision_punto_limpio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RevisionPuntoLimpio.findAll", query = "SELECT r FROM RevisionPuntoLimpio r"),
    @NamedQuery(name = "RevisionPuntoLimpio.findByNumRev", query = "SELECT r FROM RevisionPuntoLimpio r WHERE r.numRev = :numRev"),
    @NamedQuery(name = "RevisionPuntoLimpio.findByFechaRev", query = "SELECT r FROM RevisionPuntoLimpio r WHERE r.fechaRev = :fechaRev"),
    @NamedQuery(name = "RevisionPuntoLimpio.findByComentariosRev", query = "SELECT r FROM RevisionPuntoLimpio r WHERE r.comentariosRev = :comentariosRev")})
public class RevisionPuntoLimpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_rev")
    private Integer numRev;
    @Column(name = "fecha_rev")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRev;
    @Size(max = 254)
    @Column(name = "comentarios_rev")
    private String comentariosRev;
    @OneToMany(mappedBy = "numRev")
    private Collection<SolicitudMantencion> solicitudMantencionCollection;
    @JoinColumn(name = "rut", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Inspector rut;
    @JoinColumn(name = "num_punto_limpio", referencedColumnName = "num_punto_limpio")
    @ManyToOne(optional = false)
    private PuntoLimpio numPuntoLimpio;

    public RevisionPuntoLimpio() {
    }

    public RevisionPuntoLimpio(Integer numRev) {
        this.numRev = numRev;
    }

    public Integer getNumRev() {
        return numRev;
    }

    public void setNumRev(Integer numRev) {
        this.numRev = numRev;
    }

    public Date getFechaRev() {
        return fechaRev;
    }

    public void setFechaRev(Date fechaRev) {
        this.fechaRev = fechaRev;
    }

    public String getComentariosRev() {
        return comentariosRev;
    }

    public void setComentariosRev(String comentariosRev) {
        this.comentariosRev = comentariosRev;
    }

    @XmlTransient
    public Collection<SolicitudMantencion> getSolicitudMantencionCollection() {
        return solicitudMantencionCollection;
    }

    public void setSolicitudMantencionCollection(Collection<SolicitudMantencion> solicitudMantencionCollection) {
        this.solicitudMantencionCollection = solicitudMantencionCollection;
    }

    public Inspector getRut() {
        return rut;
    }

    public void setRut(Inspector rut) {
        this.rut = rut;
    }

    public PuntoLimpio getNumPuntoLimpio() {
        return numPuntoLimpio;
    }

    public void setNumPuntoLimpio(PuntoLimpio numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numRev != null ? numRev.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RevisionPuntoLimpio)) {
            return false;
        }
        RevisionPuntoLimpio other = (RevisionPuntoLimpio) object;
        if ((this.numRev == null && other.numRev != null) || (this.numRev != null && !this.numRev.equals(other.numRev))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.RevisionPuntoLimpio[ numRev=" + numRev + " ]";
    }
    
}
