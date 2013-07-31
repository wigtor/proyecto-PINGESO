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
import javax.persistence.Temporal;

/**
 *
 * @author victor
 */
@Entity
public class MantencionPuntoLimpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;
    
    private String comentarios;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private PuntoLimpio puntoLimpio;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar fecha;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private OperarioMantencion operarioMantencion;

    
    public MantencionPuntoLimpio() {
        
    }
    
    public MantencionPuntoLimpio(PuntoLimpio p, OperarioMantencion operario, String comentarios) {
        this.puntoLimpio = p;
        this.operarioMantencion = operario;
        this.comentarios = comentarios;
    }
    
    public OperarioMantencion getOperarioMantencion() {
        return operarioMantencion;
    }

    public void setOperarioMantencion(OperarioMantencion operarioMantencion) {
        this.operarioMantencion = operarioMantencion;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getId() {
        return num;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof MantencionPuntoLimpio)) {
            return false;
        }
        MantencionPuntoLimpio other = (MantencionPuntoLimpio) object;
        if ((this.num == null && other.num != null) || (this.num != null && !this.num.equals(other.num))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Mantencion[ id=" + num + " ]";
    }
    
}
