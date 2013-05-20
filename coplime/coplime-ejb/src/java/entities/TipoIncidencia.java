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
@Table(name = "tipo_incidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoIncidencia.findAll", query = "SELECT t FROM TipoIncidencia t"),
    @NamedQuery(name = "TipoIncidencia.findByIdTipoIncidencia", query = "SELECT t FROM TipoIncidencia t WHERE t.idTipoIncidencia = :idTipoIncidencia"),
    @NamedQuery(name = "TipoIncidencia.findByNombreIncidencia", query = "SELECT t FROM TipoIncidencia t WHERE t.nombreIncidencia = :nombreIncidencia"),
    @NamedQuery(name = "TipoIncidencia.findByVisibleUsuario", query = "SELECT t FROM TipoIncidencia t WHERE t.visibleUsuario = :visibleUsuario")})
public class TipoIncidencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_incidencia")
    private Integer idTipoIncidencia;
    @Size(max = 50)
    @Column(name = "nombre_incidencia")
    private String nombreIncidencia;
    @Column(name = "visible_usuario")
    private Boolean visibleUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoIncidencia")
    private Collection<Notificacion> notificacionCollection;

    public TipoIncidencia() {
    }

    public TipoIncidencia(Integer idTipoIncidencia) {
        this.idTipoIncidencia = idTipoIncidencia;
    }

    public Integer getIdTipoIncidencia() {
        return idTipoIncidencia;
    }

    public void setIdTipoIncidencia(Integer idTipoIncidencia) {
        this.idTipoIncidencia = idTipoIncidencia;
    }

    public String getNombreIncidencia() {
        return nombreIncidencia;
    }

    public void setNombreIncidencia(String nombreIncidencia) {
        this.nombreIncidencia = nombreIncidencia;
    }

    public Boolean getVisibleUsuario() {
        return visibleUsuario;
    }

    public void setVisibleUsuario(Boolean visibleUsuario) {
        this.visibleUsuario = visibleUsuario;
    }

    @XmlTransient
    public Collection<Notificacion> getNotificacionCollection() {
        return notificacionCollection;
    }

    public void setNotificacionCollection(Collection<Notificacion> notificacionCollection) {
        this.notificacionCollection = notificacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoIncidencia != null ? idTipoIncidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoIncidencia)) {
            return false;
        }
        TipoIncidencia other = (TipoIncidencia) object;
        if ((this.idTipoIncidencia == null && other.idTipoIncidencia != null) || (this.idTipoIncidencia != null && !this.idTipoIncidencia.equals(other.idTipoIncidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TipoIncidencia[ idTipoIncidencia=" + idTipoIncidencia + " ]";
    }
    
}
