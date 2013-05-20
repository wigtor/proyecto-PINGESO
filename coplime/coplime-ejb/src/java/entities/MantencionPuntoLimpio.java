/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "mantencion_punto_limpio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MantencionPuntoLimpio.findAll", query = "SELECT m FROM MantencionPuntoLimpio m"),
    @NamedQuery(name = "MantencionPuntoLimpio.findByNumMant", query = "SELECT m FROM MantencionPuntoLimpio m WHERE m.numMant = :numMant"),
    @NamedQuery(name = "MantencionPuntoLimpio.findByFechaMant", query = "SELECT m FROM MantencionPuntoLimpio m WHERE m.fechaMant = :fechaMant"),
    @NamedQuery(name = "MantencionPuntoLimpio.findByComentariosMant", query = "SELECT m FROM MantencionPuntoLimpio m WHERE m.comentariosMant = :comentariosMant")})
public class MantencionPuntoLimpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_mant")
    private Integer numMant;
    @Column(name = "fecha_mant")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMant;
    @Size(max = 254)
    @Column(name = "comentarios_mant")
    private String comentariosMant;
    @JoinColumn(name = "rut", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private OperarioMantencion rut;
    @JoinColumn(name = "num_punto_limpio", referencedColumnName = "num_punto_limpio")
    @ManyToOne(optional = false)
    private PuntoLimpio numPuntoLimpio;

    public MantencionPuntoLimpio() {
    }

    public MantencionPuntoLimpio(Integer numMant) {
        this.numMant = numMant;
    }

    public Integer getNumMant() {
        return numMant;
    }

    public void setNumMant(Integer numMant) {
        this.numMant = numMant;
    }

    public Date getFechaMant() {
        return fechaMant;
    }

    public void setFechaMant(Date fechaMant) {
        this.fechaMant = fechaMant;
    }

    public String getComentariosMant() {
        return comentariosMant;
    }

    public void setComentariosMant(String comentariosMant) {
        this.comentariosMant = comentariosMant;
    }

    public OperarioMantencion getRut() {
        return rut;
    }

    public void setRut(OperarioMantencion rut) {
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
        hash += (numMant != null ? numMant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MantencionPuntoLimpio)) {
            return false;
        }
        MantencionPuntoLimpio other = (MantencionPuntoLimpio) object;
        if ((this.numMant == null && other.numMant != null) || (this.numMant != null && !this.numMant.equals(other.numMant))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MantencionPuntoLimpio[ numMant=" + numMant + " ]";
    }
    
}
