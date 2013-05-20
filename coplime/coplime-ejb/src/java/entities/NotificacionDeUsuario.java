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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "notificacion_de_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionDeUsuario.findAll", query = "SELECT n FROM NotificacionDeUsuario n"),
    @NamedQuery(name = "NotificacionDeUsuario.findByNumNotificacion", query = "SELECT n FROM NotificacionDeUsuario n WHERE n.numNotificacion = :numNotificacion"),
    @NamedQuery(name = "NotificacionDeUsuario.findByNumNotificacionUsuario", query = "SELECT n FROM NotificacionDeUsuario n WHERE n.numNotificacionUsuario = :numNotificacionUsuario"),
    @NamedQuery(name = "NotificacionDeUsuario.findByEmailContacto", query = "SELECT n FROM NotificacionDeUsuario n WHERE n.emailContacto = :emailContacto"),
    @NamedQuery(name = "NotificacionDeUsuario.findByImagenAdjunta", query = "SELECT n FROM NotificacionDeUsuario n WHERE n.imagenAdjunta = :imagenAdjunta")})
public class NotificacionDeUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_notificacion")
    private Integer numNotificacion;
    @Basic(optional = false)
    @Column(name = "num_notificacion_usuario")
    private int numNotificacionUsuario;
    @Size(max = 254)
    @Column(name = "email_contacto")
    private String emailContacto;
    @Size(max = 254)
    @Column(name = "imagen_adjunta")
    private String imagenAdjunta;
    @JoinColumn(name = "num_notificacion", referencedColumnName = "num_notificacion", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Notificacion notificacion;

    public NotificacionDeUsuario() {
    }

    public NotificacionDeUsuario(Integer numNotificacion) {
        this.numNotificacion = numNotificacion;
    }

    public NotificacionDeUsuario(Integer numNotificacion, int numNotificacionUsuario) {
        this.numNotificacion = numNotificacion;
        this.numNotificacionUsuario = numNotificacionUsuario;
    }

    public Integer getNumNotificacion() {
        return numNotificacion;
    }

    public void setNumNotificacion(Integer numNotificacion) {
        this.numNotificacion = numNotificacion;
    }

    public int getNumNotificacionUsuario() {
        return numNotificacionUsuario;
    }

    public void setNumNotificacionUsuario(int numNotificacionUsuario) {
        this.numNotificacionUsuario = numNotificacionUsuario;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getImagenAdjunta() {
        return imagenAdjunta;
    }

    public void setImagenAdjunta(String imagenAdjunta) {
        this.imagenAdjunta = imagenAdjunta;
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
        if (!(object instanceof NotificacionDeUsuario)) {
            return false;
        }
        NotificacionDeUsuario other = (NotificacionDeUsuario) object;
        if ((this.numNotificacion == null && other.numNotificacion != null) || (this.numNotificacion != null && !this.numNotificacion.equals(other.numNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotificacionDeUsuario[ numNotificacion=" + numNotificacion + " ]";
    }
    
}
