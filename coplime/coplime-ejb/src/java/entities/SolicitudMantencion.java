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
@Table(name = "solicitud_mantencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudMantencion.findAll", query = "SELECT s FROM SolicitudMantencion s"),
    @NamedQuery(name = "SolicitudMantencion.findByNumSolMant", query = "SELECT s FROM SolicitudMantencion s WHERE s.numSolMant = :numSolMant"),
    @NamedQuery(name = "SolicitudMantencion.findByFechaSolMant", query = "SELECT s FROM SolicitudMantencion s WHERE s.fechaSolMant = :fechaSolMant"),
    @NamedQuery(name = "SolicitudMantencion.findByComentariosSolMant", query = "SELECT s FROM SolicitudMantencion s WHERE s.comentariosSolMant = :comentariosSolMant")})
public class SolicitudMantencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_sol_mant")
    private Integer numSolMant;
    @Column(name = "fecha_sol_mant")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolMant;
    @Size(max = 254)
    @Column(name = "comentarios_sol_mant")
    private String comentariosSolMant;
    @JoinColumn(name = "num_punto_limpio", referencedColumnName = "num_punto_limpio")
    @ManyToOne(optional = false)
    private PuntoLimpio numPuntoLimpio;
    @JoinColumn(name = "ins_rut", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Inspector insRut;
    @JoinColumn(name = "rut", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private OperarioMantencion rut;
    @JoinColumn(name = "num_rev", referencedColumnName = "num_rev")
    @ManyToOne
    private RevisionPuntoLimpio numRev;

    public SolicitudMantencion() {
    }

    public SolicitudMantencion(Integer numSolMant) {
        this.numSolMant = numSolMant;
    }

    public Integer getNumSolMant() {
        return numSolMant;
    }

    public void setNumSolMant(Integer numSolMant) {
        this.numSolMant = numSolMant;
    }

    public Date getFechaSolMant() {
        return fechaSolMant;
    }

    public void setFechaSolMant(Date fechaSolMant) {
        this.fechaSolMant = fechaSolMant;
    }

    public String getComentariosSolMant() {
        return comentariosSolMant;
    }

    public void setComentariosSolMant(String comentariosSolMant) {
        this.comentariosSolMant = comentariosSolMant;
    }

    public PuntoLimpio getNumPuntoLimpio() {
        return numPuntoLimpio;
    }

    public void setNumPuntoLimpio(PuntoLimpio numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }

    public Inspector getInsRut() {
        return insRut;
    }

    public void setInsRut(Inspector insRut) {
        this.insRut = insRut;
    }

    public OperarioMantencion getRut() {
        return rut;
    }

    public void setRut(OperarioMantencion rut) {
        this.rut = rut;
    }

    public RevisionPuntoLimpio getNumRev() {
        return numRev;
    }

    public void setNumRev(RevisionPuntoLimpio numRev) {
        this.numRev = numRev;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numSolMant != null ? numSolMant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudMantencion)) {
            return false;
        }
        SolicitudMantencion other = (SolicitudMantencion) object;
        if ((this.numSolMant == null && other.numSolMant != null) || (this.numSolMant != null && !this.numSolMant.equals(other.numSolMant))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SolicitudMantencion[ numSolMant=" + numSolMant + " ]";
    }
    
}
