/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author victor
 */
@Entity
public class RevisionPuntoLimpio implements Serializable {
    @ManyToOne
    private Inspector inspectorRevisor;
    @ManyToOne
    private PuntoLimpio puntoLimpio;

    public Inspector getInspectorRevisor() {
        return inspectorRevisor;
    }

    public void setInspectorRevisor(Inspector inspectorRevisor) {
        this.inspectorRevisor = inspectorRevisor;
    }

    public PuntoLimpio getPuntoLimpio() {
        return puntoLimpio;
    }

    public void setPuntoLimpio(PuntoLimpio puntoLimpio) {
        this.puntoLimpio = puntoLimpio;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public List<SolicitudMantencion> getSolicitudesPorMantencion() {
        return solicitudesPorMantencion;
    }

    public void setSolicitudesPorMantencion(List<SolicitudMantencion> solicitudesPorMantencion) {
        this.solicitudesPorMantencion = solicitudesPorMantencion;
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    
    @OneToMany(mappedBy = "revisionOriginadora")
    private List<SolicitudMantencion> solicitudesPorMantencion; //Casi siempre es sólo una, no varias

    public Long getId() {
        return num;
    }

    public void setId(Long id) {
        this.num = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (num != null ? num.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RevisionPuntoLimpio)) {
            return false;
        }
        RevisionPuntoLimpio other = (RevisionPuntoLimpio) object;
        if ((this.num == null && other.num != null) || (this.num != null && !this.num.equals(other.num))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.RevisionPuntoLimpio[ id=" + num + " ]";
    }
    
}
