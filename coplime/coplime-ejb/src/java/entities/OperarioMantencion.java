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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author victor
 */
@Entity
public class OperarioMantencion implements Serializable {
    @OneToMany(mappedBy = "operarioAsignado")
    private List<SolicitudMantencion> solicitudesMantencionRealizadas;

    public List<SolicitudMantencion> getSolicitudesMantencionRealizadas() {
        return solicitudesMantencionRealizadas;
    }

    public void setSolicitudesMantencionRealizadas(List<SolicitudMantencion> solicitudesMantencionRealizadas) {
        this.solicitudesMantencionRealizadas = solicitudesMantencionRealizadas;
    }

    public List<MantencionPuntoLimpio> getMantencionesRealizadas() {
        return mantencionesRealizadas;
    }

    public void setMantencionesRealizadas(List<MantencionPuntoLimpio> mantencionesRealizadas) {
        this.mantencionesRealizadas = mantencionesRealizadas;
    }
    
    @OneToMany(mappedBy = "operarioMantencion")
    private List<MantencionPuntoLimpio> mantencionesRealizadas;
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getCod() {
        return id;
    }

    public void setCod(Integer cod) {
        this.id = cod;
    }
    
    @OneToOne
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof OperarioMantencion)) {
            return false;
        }
        OperarioMantencion other = (OperarioMantencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OperarioMantencion[ id=" + id + " ]";
    }
    
}
