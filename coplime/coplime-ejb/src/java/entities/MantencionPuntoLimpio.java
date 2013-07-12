/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author victor
 */
@Entity
public class MantencionPuntoLimpio implements Serializable {
    @ManyToOne
    private OperarioMantencion operarioMantencion;

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
    @ManyToOne
    private PuntoLimpio puntoLimpio;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    private String comentarios;

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
