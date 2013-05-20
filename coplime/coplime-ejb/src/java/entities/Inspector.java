/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "inspector")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inspector.findAll", query = "SELECT i FROM Inspector i"),
    @NamedQuery(name = "Inspector.findByRut", query = "SELECT i FROM Inspector i WHERE i.rut = :rut"),
    @NamedQuery(name = "Inspector.findByCodInspector", query = "SELECT i FROM Inspector i WHERE i.codInspector = :codInspector")})
public class Inspector implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rut")
    private Integer rut;
    @Basic(optional = false)
    @Column(name = "cod_inspector")
    private int codInspector;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insRut")
    private Collection<SolicitudMantencion> solicitudMantencionCollection;
    @JoinColumn(name = "rut", referencedColumnName = "rut", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rut")
    private Collection<RevisionPuntoLimpio> revisionPuntoLimpioCollection;
    @OneToMany(mappedBy = "rut")
    private Collection<PuntoLimpio> puntoLimpioCollection;

    public Inspector() {
    }

    public Inspector(Integer rut) {
        this.rut = rut;
    }

    public Inspector(Integer rut, int codInspector) {
        this.rut = rut;
        this.codInspector = codInspector;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public int getCodInspector() {
        return codInspector;
    }

    public void setCodInspector(int codInspector) {
        this.codInspector = codInspector;
    }

    @XmlTransient
    public Collection<SolicitudMantencion> getSolicitudMantencionCollection() {
        return solicitudMantencionCollection;
    }

    public void setSolicitudMantencionCollection(Collection<SolicitudMantencion> solicitudMantencionCollection) {
        this.solicitudMantencionCollection = solicitudMantencionCollection;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<RevisionPuntoLimpio> getRevisionPuntoLimpioCollection() {
        return revisionPuntoLimpioCollection;
    }

    public void setRevisionPuntoLimpioCollection(Collection<RevisionPuntoLimpio> revisionPuntoLimpioCollection) {
        this.revisionPuntoLimpioCollection = revisionPuntoLimpioCollection;
    }

    @XmlTransient
    public Collection<PuntoLimpio> getPuntoLimpioCollection() {
        return puntoLimpioCollection;
    }

    public void setPuntoLimpioCollection(Collection<PuntoLimpio> puntoLimpioCollection) {
        this.puntoLimpioCollection = puntoLimpioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inspector)) {
            return false;
        }
        Inspector other = (Inspector) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Inspector[ rut=" + rut + " ]";
    }
    
}
