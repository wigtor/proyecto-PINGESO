/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "notificacion_de_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionDeSistema.findAll", query = "SELECT n FROM NotificacionDeSistema n"),
    @NamedQuery(name = "NotificacionDeSistema.findByNumNotificacion", query = "SELECT n FROM NotificacionDeSistema n WHERE n.numNotificacion = :numNotificacion"),
    @NamedQuery(name = "NotificacionDeSistema.findByNumNotificacionSistema", query = "SELECT n FROM NotificacionDeSistema n WHERE n.numNotificacionSistema = :numNotificacionSistema")})
public class NotificacionDeSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_notificacion")
    private Integer numNotificacion;
    @Basic(optional = false)
    @Column(name = "num_notificacion_sistema")
    private int numNotificacionSistema;
    @JoinColumn(name = "num_notificacion", referencedColumnName = "num_notificacion", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Notificacion notificacion;

    public NotificacionDeSistema() {
    }

    public NotificacionDeSistema(Integer numNotificacion) {
        this.numNotificacion = numNotificacion;
    }

    public NotificacionDeSistema(Integer numNotificacion, int numNotificacionSistema) {
        this.numNotificacion = numNotificacion;
        this.numNotificacionSistema = numNotificacionSistema;
    }

    public Integer getNumNotificacion() {
        return numNotificacion;
    }

    public void setNumNotificacion(Integer numNotificacion) {
        this.numNotificacion = numNotificacion;
    }

    public int getNumNotificacionSistema() {
        return numNotificacionSistema;
    }

    public void setNumNotificacionSistema(int numNotificacionSistema) {
        this.numNotificacionSistema = numNotificacionSistema;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
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
        if (!(object instanceof NotificacionDeSistema)) {
            return false;
        }
        NotificacionDeSistema other = (NotificacionDeSistema) object;
        if ((this.numNotificacion == null && other.numNotificacion != null) || (this.numNotificacion != null && !this.numNotificacion.equals(other.numNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotificacionDeSistema[ numNotificacion=" + numNotificacion + " ]";
    }
    
}
