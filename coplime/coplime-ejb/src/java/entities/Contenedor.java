/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
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

/**
 *
 * @author victor
 */
@Entity
@NamedQueries( {
    @NamedQuery(name="Contenedor.findByPuntoLimpio", query="SELECT u FROM Contenedor u WHERE u.puntoLimpio.id = :idPtoLimpio")
})
public class Contenedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Material materialDeAcopio;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado estadoContenedor;
    
    @Column(nullable = false)
    private int capacidad;
    
    
    @ManyToOne
    private UnidadMedida unidadMedida;
    
    @Column(nullable = false)
    private int procentajeUso; //Indica lo que últimamente fue revisado y confirmado
    
    @Column(nullable = false)
    private int porcentajeUsoEstimado; //Indica lo que el sistema estima
    
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private PuntoLimpio puntoLimpio;
    
    
    @OneToMany(mappedBy = "contenedor")
    private List<HistoricoContenedor> historialContenedor;

    public Contenedor() {
        this.historialContenedor = new LinkedList();
    }

    
    
    public List<HistoricoContenedor> getHistorialContenedor() {
        return historialContenedor;
    }

    public void setHistorialContenedor(List<HistoricoContenedor> historialContenedor) {
        this.historialContenedor = historialContenedor;
    }

    
    
    public PuntoLimpio getPuntoLimpio() {
        return puntoLimpio;
    }

    public void setPuntoLimpio(PuntoLimpio puntoLimpio) {
        this.puntoLimpio = puntoLimpio;
    }

    public Material getMaterialDeAcopio() {
        return materialDeAcopio;
    }

    public void setMaterialDeAcopio(Material materialDeAcopio) {
        this.materialDeAcopio = materialDeAcopio;
    }

    public Estado getEstadoContenedor() {
        return estadoContenedor;
    }

    public void setEstadoContenedor(Estado estadoContenedor) {
        this.estadoContenedor = estadoContenedor;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getProcentajeUso() {
        return procentajeUso;
    }

    public void setProcentajeUso(int procentajeUso) {
        this.procentajeUso = procentajeUso;
    }

    public int getPorcentajeUsoEstimado() {
        return porcentajeUsoEstimado;
    }

    public void setPorcentajeUsoEstimado(int porcentajeUsoEstimado) {
        this.porcentajeUsoEstimado = porcentajeUsoEstimado;
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
        if (!(object instanceof Contenedor)) {
            return false;
        }
        Contenedor other = (Contenedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Contenedor[ id=" + id + " ]";
    }
    
}
