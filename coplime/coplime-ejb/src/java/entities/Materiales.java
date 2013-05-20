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
@Table(name = "materiales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materiales.findAll", query = "SELECT m FROM Materiales m"),
    @NamedQuery(name = "Materiales.findByIdMaterial", query = "SELECT m FROM Materiales m WHERE m.idMaterial = :idMaterial"),
    @NamedQuery(name = "Materiales.findByNombreMaterial", query = "SELECT m FROM Materiales m WHERE m.nombreMaterial = :nombreMaterial")})
public class Materiales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_material")
    private Integer idMaterial;
    @Size(max = 100)
    @Column(name = "nombre_material")
    private String nombreMaterial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaterial")
    private Collection<Contenedor> contenedorCollection;

    public Materiales() {
    }

    public Materiales(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
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
        hash += (idMaterial != null ? idMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materiales)) {
            return false;
        }
        Materiales other = (Materiales) object;
        if ((this.idMaterial == null && other.idMaterial != null) || (this.idMaterial != null && !this.idMaterial.equals(other.idMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Materiales[ idMaterial=" + idMaterial + " ]";
    }
    
}
