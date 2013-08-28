/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author victor
 */
@Entity
@NamedQueries( {
    @NamedQuery(name="SolicitudMantencion.findByInspector", query="SELECT u FROM SolicitudMantencion u WHERE u.inspectorSolicitante.usuario.id = :idUsuario"), 
    @NamedQuery(name="SolicitudMantencion.findByOperario", query="SELECT u FROM SolicitudMantencion u WHERE u.operarioAsignado.usuario.id = :idUsuario"),
    @NamedQuery(name="SolicitudMantencion.countNoRevisadasUsuarioDestinatario", query="SELECT count(u) FROM SolicitudMantencion u WHERE (u.operarioAsignado.usuario.username = :username) AND (u.revisado = false)"),
    @NamedQuery(name="SolicitudMantencion.findByDateRange", query="SELECT u FROM SolicitudMantencion u WHERE u.fecha >= :fechaIni AND u.fecha <= :fechaFin") 
})
public class SolicitudMantencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    @ManyToOne
    private OperarioMantencion operarioAsignado;
    
    @ManyToOne
    private RevisionPuntoLimpio revisionOriginadora;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Inspector inspectorSolicitante;
    
    @ManyToOne
    private PuntoLimpio puntoLimpio;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar fecha;
    
    private String detalles;
    
    @Column(nullable = false)
    private boolean revisado;
    
    @Column(nullable = false)
    private boolean resuelto;

    
    public SolicitudMantencion() {
        this.resuelto = false;
        this.revisado = false;
    }
    
    public SolicitudMantencion(PuntoLimpio p, Inspector ins, OperarioMantencion opAsign, String detalle) {
        this.puntoLimpio = p;
        this.inspectorSolicitante = ins;
        this.operarioAsignado = opAsign;
        this.detalles = detalle;
        this.resuelto = false;
        this.revisado = false;
    }

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
    
    public Integer getId() {
        return num;
    }

    public void setId(Integer id) {
        this.num = id;
    }
    
    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
    
    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public boolean isRevisado() {
        return revisado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }

    public boolean isResuelto() {
        return resuelto;
    }

    public void setResuelto(boolean resuelto) {
        this.resuelto = resuelto;
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
