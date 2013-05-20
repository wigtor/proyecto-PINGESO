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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "estado_punto_limpio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoPuntoLimpio.findAll", query = "SELECT e FROM EstadoPuntoLimpio e"),
    @NamedQuery(name = "EstadoPuntoLimpio.findByIdEstadoPuntoLimpio", query = "SELECT e FROM EstadoPuntoLimpio e WHERE e.idEstadoPuntoLimpio = :idEstadoPuntoLimpio"),
    @NamedQuery(name = "EstadoPuntoLimpio.findByNombreEstadoPuntoLimpio", query = "SELECT e FROM EstadoPuntoLimpio e WHERE e.nombreEstadoPuntoLimpio = :nombreEstadoPuntoLimpio")})
public class EstadoPuntoLimpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_punto_limpio")
    private Integer idEstadoPuntoLimpio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_estado_punto_limpio")
    private String nombreEstadoPuntoLimpio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstadoPuntoLimpio")
    private Collection<PuntoLimpio> puntoLimpioCollection;

    public EstadoPuntoLimpio() {
    }

    public EstadoPuntoLimpio(Integer idEstadoPuntoLimpio) {
        this.idEstadoPuntoLimpio = idEstadoPuntoLimpio;
    }

    public EstadoPuntoLimpio(Integer idEstadoPuntoLimpio, String nombreEstadoPuntoLimpio) {
        this.idEstadoPuntoLimpio = idEstadoPuntoLimpio;
        this.nombreEstadoPuntoLimpio = nombreEstadoPuntoLimpio;
    }

    public Integer getIdEstadoPuntoLimpio() {
        return idEstadoPuntoLimpio;
    }

    public void setIdEstadoPuntoLimpio(Integer idEstadoPuntoLimpio) {
        this.idEstadoPuntoLimpio = idEstadoPuntoLimpio;
    }

    public String getNombreEstadoPuntoLimpio() {
        return nombreEstadoPuntoLimpio;
    }

    public void setNombreEstadoPuntoLimpio(String nombreEstadoPuntoLimpio) {
        this.nombreEstadoPuntoLimpio = nombreEstadoPuntoLimpio;
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
        hash += (idEstadoPuntoLimpio != null ? idEstadoPuntoLimpio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoPuntoLimpio)) {
            return false;
        }
        EstadoPuntoLimpio other = (EstadoPuntoLimpio) object;
        if ((this.idEstadoPuntoLimpio == null && other.idEstadoPuntoLimpio != null) || (this.idEstadoPuntoLimpio != null && !this.idEstadoPuntoLimpio.equals(other.idEstadoPuntoLimpio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EstadoPuntoLimpio[ idEstadoPuntoLimpio=" + idEstadoPuntoLimpio + " ]";
    }
    
}
