/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author victor
 */
@Entity
public class Inspector implements Serializable {
    @OneToMany(mappedBy = "inspectorEncargado")
    private List<PuntoLimpio> puntosLimpios;
    
    @OneToMany(mappedBy = "inspectorSolicitante")
    private List<SolicitudMantencion> solicitudesMantencionRealizadas;

    @OneToMany(mappedBy = "inspectorRevisor")
    private List<RevisionPuntoLimpio> revisionesRealizadas;

    public List<SolicitudMantencion> getSolicitudesMantencionRealizadas() {
        return solicitudesMantencionRealizadas;
    }

    public void setSolicitudesMantencionRealizadas(List<SolicitudMantencion> solicitudesMantencionRealizadas) {
        this.solicitudesMantencionRealizadas = solicitudesMantencionRealizadas;
    }

    public List<RevisionPuntoLimpio> getRevisionesRealizadas() {
        return revisionesRealizadas;
    }

    public void setRevisionesRealizadas(List<RevisionPuntoLimpio> revisionesRealizadas) {
        this.revisionesRealizadas = revisionesRealizadas;
    }
    
    public List<PuntoLimpio> getPuntosLimpios() {
        return puntosLimpios;
    }

    public void setPuntosLimpios(List<PuntoLimpio> puntosLimpios) {
        this.puntosLimpios = puntosLimpios;
    }
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //Es un N° único para el usuario inspector

    public Integer getCod() {
        return id;
    }

    public void setCod(Integer cod) {
        this.id = cod;
    }
    
    @OneToOne(optional = false, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(nullable = false)
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
        if (!(object instanceof Inspector)) {
            return false;
        }
        Inspector other = (Inspector) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Inspector[ id=" + id + " ]";
    }
    
}
