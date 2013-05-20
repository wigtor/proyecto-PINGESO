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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "estado_contenedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoContenedor.findAll", query = "SELECT e FROM EstadoContenedor e"),
    @NamedQuery(name = "EstadoContenedor.findByIdEstadoContenedor", query = "SELECT e FROM EstadoContenedor e WHERE e.idEstadoContenedor = :idEstadoContenedor"),
    @NamedQuery(name = "EstadoContenedor.findByNombreEstadoContenedor", query = "SELECT e FROM EstadoContenedor e WHERE e.nombreEstadoContenedor = :nombreEstadoContenedor")})
public class EstadoContenedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_contenedor")
    private Integer idEstadoContenedor;
    @Size(max = 50)
    @Column(name = "nombre_estado_contenedor")
    private String nombreEstadoContenedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstadoContenedor")
    private Collection<Contenedor> contenedorCollection;

    public EstadoContenedor() {
    }

    public EstadoContenedor(Integer idEstadoContenedor) {
        this.idEstadoContenedor = idEstadoContenedor;
    }

    public Integer getIdEstadoContenedor() {
        return idEstadoContenedor;
    }

    public void setIdEstadoContenedor(Integer idEstadoContenedor) {
        this.idEstadoContenedor = idEstadoContenedor;
    }

    public String getNombreEstadoContenedor() {
        return nombreEstadoContenedor;
    }

    public void setNombreEstadoContenedor(String nombreEstadoContenedor) {
        this.nombreEstadoContenedor = nombreEstadoContenedor;
    }

    @XmlTransient
    public Collection<Contenedor> getContenedorCollection() {
        return contenedorCollection;
    }

    public void setContenedorCollection(Collection<Contenedor> contenedorCollection) {
        this.contenedorCollection = contenedorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoContenedor != null ? idEstadoContenedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoContenedor)) {
            return false;
        }
        EstadoContenedor other = (EstadoContenedor) object;
        if ((this.idEstadoContenedor == null && other.idEstadoContenedor != null) || (this.idEstadoContenedor != null && !this.idEstadoContenedor.equals(other.idEstadoContenedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EstadoContenedor[ idEstadoContenedor=" + idEstadoContenedor + " ]";
    }
    
}
