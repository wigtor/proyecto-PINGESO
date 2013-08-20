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
    @NamedQuery(name="Notificacion.findByUsuarioDestinatario", query="SELECT u FROM Notificacion u WHERE u.usuarioEncargado.username = :username"),
    @NamedQuery(name="Notificacion.countNoRevisadasUsuarioDestinatario", query="SELECT count(u) FROM Notificacion u WHERE (u.usuarioEncargado.username = :username) AND (u.revisado = false)")
})
public class Notificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Calendar fechaHora;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Usuario usuarioEncargado;
    
    @Column(nullable = false)
    protected String comentario;
    
    @Column(nullable = false)
    protected boolean revisado;
    
    @Column(nullable = false)
    protected boolean resuelto;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    protected PuntoLimpio puntoLimpio;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    protected TipoIncidencia tipoIncidencia;

    
    public PuntoLimpio getPuntoLimpio() {
        return puntoLimpio;
    }

    public void setPuntoLimpio(PuntoLimpio puntoLimpio) {
        this.puntoLimpio = puntoLimpio;
    }
    
    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isRevisado() {
        return revisado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }

    public Calendar getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Calendar fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isResuelto() {
        return resuelto;
    }

    public void setResuelto(boolean resuelto) {
        this.resuelto = resuelto;
    }

    public Usuario getUsuarioEncargado() {
        return usuarioEncargado;
    }

    public void setUsuarioEncargado(Usuario usuarioEncargado) {
        this.usuarioEncargado = usuarioEncargado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacion)) {
            return false;
        }
        Notificacion other = (Notificacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Notificacion[ id=" + id + " ]";
    }
    
}
