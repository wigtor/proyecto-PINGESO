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
@Table(name = "operario_mantencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperarioMantencion.findAll", query = "SELECT o FROM OperarioMantencion o"),
    @NamedQuery(name = "OperarioMantencion.findByRut", query = "SELECT o FROM OperarioMantencion o WHERE o.rut = :rut"),
    @NamedQuery(name = "OperarioMantencion.findByCodOperario", query = "SELECT o FROM OperarioMantencion o WHERE o.codOperario = :codOperario")})
public class OperarioMantencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rut")
    private Integer rut;
    @Basic(optional = false)
    @Column(name = "cod_operario")
    private int codOperario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rut")
    private Collection<SolicitudMantencion> solicitudMantencionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rut")
    private Collection<MantencionPuntoLimpio> mantencionPuntoLimpioCollection;
    @JoinColumn(name = "rut", referencedColumnName = "rut", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;

    public OperarioMantencion() {
    }

    public OperarioMantencion(Integer rut) {
        this.rut = rut;
    }

    public OperarioMantencion(Integer rut, int codOperario) {
        this.rut = rut;
        this.codOperario = codOperario;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public int getCodOperario() {
        return codOperario;
    }

    public void setCodOperario(int codOperario) {
        this.codOperario = codOperario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof OperarioMantencion)) {
            return false;
        }
        OperarioMantencion other = (OperarioMantencion) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OperarioMantencion[ rut=" + rut + " ]";
    }
    
}
