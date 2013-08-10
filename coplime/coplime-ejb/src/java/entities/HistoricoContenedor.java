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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Armando
 */
@Entity
public class HistoricoContenedor implements Serializable {
    @ManyToOne
    private Contenedor contenedor;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Calendar fechaHora;

    @Column(nullable = false)
    private Integer porcentajeLlenado;
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Calendar fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getPorcentajeLlenado() {
        return porcentajeLlenado;
    }

    public void setPorcentajeLlenado(Integer porcentajeLlenado) {
        this.porcentajeLlenado = porcentajeLlenado;
    }

    public Contenedor getContenedor() {
        return contenedor;
    }

    public void setContenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
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
        if (!(object instanceof HistoricoContenedor)) {
            return false;
        }
        HistoricoContenedor other = (HistoricoContenedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.HistoricoContenedor[ id=" + id + " ]";
    }
    
}
