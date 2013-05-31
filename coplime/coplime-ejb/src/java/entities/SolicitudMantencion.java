/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author victor
 */
@Entity
public class SolicitudMantencion implements Serializable {
    @ManyToOne
    private RevisionPuntoLimpio revisionOriginadora;
    @ManyToOne
    private Inspector inspectorSolicitante;
    
    @ManyToOne
    private PuntoLimpio puntoLimpio;

    public RevisionPuntoLimpio getRevisionOriginadora() {
        return revisionOriginadora;
    }

    public void setRevisionOriginadora(RevisionPuntoLimpio revisionOriginadora) {
        this.revisionOriginadora = revisionOriginadora;
    }

    public Inspector getInspectorSolicitante() {
        return inspectorSolicitante;
    }

    public void setInspectorSolicitante(Inspector inspectorSolicitante) {
        this.inspectorSolicitante = inspectorSolicitante;
    }

    public PuntoLimpio getPuntoLimpio() {
        return puntoLimpio;
    }

    public void setPuntoLimpio(PuntoLimpio puntoLimpio) {
        this.puntoLimpio = puntoLimpio;
    }

    public OperarioMantencion getOperarioAsignado() {
        return operarioAsignado;
    }

    public void setOperarioAsignado(OperarioMantencion operarioAsignado) {
        this.operarioAsignado = operarioAsignado;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }
    
    @ManyToOne
    private OperarioMantencion operarioAsignado;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long num;

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
        if (!(object instanceof SolicitudMantencion)) {
            return false;
        }
        SolicitudMantencion other = (SolicitudMantencion) object;
        if ((this.num == null && other.num != null) || (this.num != null && !this.num.equals(other.num))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SolicitudMantencion[ id=" + num + " ]";
    }
    
}
