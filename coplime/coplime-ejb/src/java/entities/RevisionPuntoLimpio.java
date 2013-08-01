/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author victor
 */
@Entity
@NamedQueries( {
    @NamedQuery(name="RevisionPuntoLimpio.findByInspector", query="SELECT u FROM RevisionPuntoLimpio u WHERE u.inspectorRevisor.usuario.id = :idUsuario") 
})
public class RevisionPuntoLimpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar fecha;
    
    @OneToMany(mappedBy = "revisionOriginadora")
    private List<SolicitudMantencion> solicitudesPorMantencion; //Casi siempre es sólo una, no varias

    private String detalles;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Inspector inspectorRevisor;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private PuntoLimpio puntoLimpio;

    
    public RevisionPuntoLimpio() {
        
    }
    
    public RevisionPuntoLimpio(PuntoLimpio p, Inspector ins, String detalles) {
        this.puntoLimpio = p;
        this.inspectorRevisor = ins;
        this.detalles = detalles;
    }
    
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
    
    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public List<SolicitudMantencion> getSolicitudesPorMantencion() {
        return solicitudesPorMantencion;
    }

    public void setSolicitudesPorMantencion(List<SolicitudMantencion> solicitudesPorMantencion) {
        this.solicitudesPorMantencion = solicitudesPorMantencion;
    }
    
    public Integer getId() {
        return num;
    }

    public void setId(Integer id) {
        this.num = id;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
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
