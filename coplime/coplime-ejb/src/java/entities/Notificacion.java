/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "notificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacion.findAll", query = "SELECT n FROM Notificacion n"),
    @NamedQuery(name = "Notificacion.findByNumNotificacion", query = "SELECT n FROM Notificacion n WHERE n.numNotificacion = :numNotificacion"),
    @NamedQuery(name = "Notificacion.findByComentarioNotificacion", query = "SELECT n FROM Notificacion n WHERE n.comentarioNotificacion = :comentarioNotificacion"),
    @NamedQuery(name = "Notificacion.findByRevisadoNotificacion", query = "SELECT n FROM Notificacion n WHERE n.revisadoNotificacion = :revisadoNotificacion"),
    @NamedQuery(name = "Notificacion.findByFechaHoraNotificacion", query = "SELECT n FROM Notificacion n WHERE n.fechaHoraNotificacion = :fechaHoraNotificacion"),
    @NamedQuery(name = "Notificacion.findByResueltoNotificacion", query = "SELECT n FROM Notificacion n WHERE n.resueltoNotificacion = :resueltoNotificacion")})
public class Notificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_notificacion")
    private Integer numNotificacion;
    @Size(max = 254)
    @Column(name = "comentario_notificacion")
    private String comentarioNotificacion;
    @Column(name = "revisado_notificacion")
    private Boolean revisadoNotificacion;
    @Column(name = "fecha_hora_notificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraNotificacion;
    @Column(name = "resuelto_notificacion")
    private Boolean resueltoNotificacion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "notificacion")
    private NotificacionDeUsuario notificacionDeUsuario;
    @JoinColumn(name = "id_tipo_incidencia", referencedColumnName = "id_tipo_incidencia")
    @ManyToOne(optional = false)
    private TipoIncidencia idTipoIncidencia;
    @JoinColumn(name = "num_punto_limpio", referencedColumnName = "num_punto_limpio")
    @ManyToOne
    private PuntoLimpio numPuntoLimpio;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "notificacion")
    private NotificacionDeSistema notificacionDeSistema;

    public Notificacion() {
    }

    public Notificacion(Integer numNotificacion) {
        this.numNotificacion = numNotificacion;
    }

    public Integer getNumNotificacion() {
        return numNotificacion;
    }

    public void setNumNotificacion(Integer numNotificacion) {
        this.numNotificacion = numNotificacion;
    }

    public String getComentarioNotificacion() {
        return comentarioNotificacion;
    }

    public void setComentarioNotificacion(String comentarioNotificacion) {
        this.comentarioNotificacion = comentarioNotificacion;
    }

    public Boolean getRevisadoNotificacion() {
        return revisadoNotificacion;
    }

    public void setRevisadoNotificacion(Boolean revisadoNotificacion) {
        this.revisadoNotificacion = revisadoNotificacion;
    }

    public Date getFechaHoraNotificacion() {
        return fechaHoraNotificacion;
    }

    public void setFechaHoraNotificacion(Date fechaHoraNotificacion) {
        this.fechaHoraNotificacion = fechaHoraNotificacion;
    }

    public Boolean getResueltoNotificacion() {
        return resueltoNotificacion;
    }

    public void setResueltoNotificacion(Boolean resueltoNotificacion) {
        this.resueltoNotificacion = resueltoNotificacion;
    }

    public NotificacionDeUsuario getNotificacionDeUsuario() {
        return notificacionDeUsuario;
    }

    public void setNotificacionDeUsuario(NotificacionDeUsuario notificacionDeUsuario) {
        this.notificacionDeUsuario = notificacionDeUsuario;
    }

    public TipoIncidencia getIdTipoIncidencia() {
        return idTipoIncidencia;
    }

    public void setIdTipoIncidencia(TipoIncidencia idTipoIncidencia) {
        this.idTipoIncidencia = idTipoIncidencia;
    }

    public PuntoLimpio getNumPuntoLimpio() {
        return numPuntoLimpio;
    }

    public void setNumPuntoLimpio(PuntoLimpio numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }

    public NotificacionDeSistema getNotificacionDeSistema() {
        return notificacionDeSistema;
    }

    public void setNotificacionDeSistema(NotificacionDeSistema notificacionDeSistema) {
        this.notificacionDeSistema = notificacionDeSistema;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numNotificacion != null ? numNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacion)) {
            return false;
        }
        Notificacion other = (Notificacion) object;
        if ((this.numNotificacion == null && other.numNotificacion != null) || (this.numNotificacion != null && !this.numNotificacion.equals(other.numNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Notificacion[ numNotificacion=" + numNotificacion + " ]";
    }
    
}
