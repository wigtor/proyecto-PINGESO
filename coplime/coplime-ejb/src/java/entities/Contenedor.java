/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "contenedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contenedor.findAll", query = "SELECT c FROM Contenedor c"),
    @NamedQuery(name = "Contenedor.findByNumContenedor", query = "SELECT c FROM Contenedor c WHERE c.numContenedor = :numContenedor"),
    @NamedQuery(name = "Contenedor.findByCapacidadContenedor", query = "SELECT c FROM Contenedor c WHERE c.capacidadContenedor = :capacidadContenedor"),
    @NamedQuery(name = "Contenedor.findByUnidadMedidaContenedor", query = "SELECT c FROM Contenedor c WHERE c.unidadMedidaContenedor = :unidadMedidaContenedor"),
    @NamedQuery(name = "Contenedor.findByPorcentajeUsoContenedor", query = "SELECT c FROM Contenedor c WHERE c.porcentajeUsoContenedor = :porcentajeUsoContenedor"),
    @NamedQuery(name = "Contenedor.findByPorcentajeUsoEstimadoContenedor", query = "SELECT c FROM Contenedor c WHERE c.porcentajeUsoEstimadoContenedor = :porcentajeUsoEstimadoContenedor")})
public class Contenedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_contenedor")
    private Integer numContenedor;
    @Column(name = "capacidad_contenedor")
    private Integer capacidadContenedor;
    @Size(max = 20)
    @Column(name = "unidad_medida_contenedor")
    private String unidadMedidaContenedor;
    @Column(name = "porcentaje_uso_contenedor")
    private Integer porcentajeUsoContenedor;
    @Column(name = "porcentaje_uso_estimado_contenedor")
    private Integer porcentajeUsoEstimadoContenedor;
    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    @ManyToOne(optional = false)
    private Materiales idMaterial;
    @JoinColumn(name = "num_punto_limpio", referencedColumnName = "num_punto_limpio")
    @ManyToOne(optional = false)
    private PuntoLimpio numPuntoLimpio;
    @JoinColumn(name = "id_estado_contenedor", referencedColumnName = "id_estado_contenedor")
    @ManyToOne(optional = false)
    private EstadoContenedor idEstadoContenedor;

    public Contenedor() {
    }

    public Contenedor(Integer numContenedor) {
        this.numContenedor = numContenedor;
    }

    public Integer getNumContenedor() {
        return numContenedor;
    }

    public void setNumContenedor(Integer numContenedor) {
        this.numContenedor = numContenedor;
    }

    public Integer getCapacidadContenedor() {
        return capacidadContenedor;
    }

    public void setCapacidadContenedor(Integer capacidadContenedor) {
        this.capacidadContenedor = capacidadContenedor;
    }

    public String getUnidadMedidaContenedor() {
        return unidadMedidaContenedor;
    }

    public void setUnidadMedidaContenedor(String unidadMedidaContenedor) {
        this.unidadMedidaContenedor = unidadMedidaContenedor;
    }

    public Integer getPorcentajeUsoContenedor() {
        return porcentajeUsoContenedor;
    }

    public void setPorcentajeUsoContenedor(Integer porcentajeUsoContenedor) {
        this.porcentajeUsoContenedor = porcentajeUsoContenedor;
    }

    public Integer getPorcentajeUsoEstimadoContenedor() {
        return porcentajeUsoEstimadoContenedor;
    }

    public void setPorcentajeUsoEstimadoContenedor(Integer porcentajeUsoEstimadoContenedor) {
        this.porcentajeUsoEstimadoContenedor = porcentajeUsoEstimadoContenedor;
    }

    public Materiales getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Materiales idMaterial) {
        this.idMaterial = idMaterial;
    }

    public PuntoLimpio getNumPuntoLimpio() {
        return numPuntoLimpio;
    }

    public void setNumPuntoLimpio(PuntoLimpio numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }

    public EstadoContenedor getIdEstadoContenedor() {
        return idEstadoContenedor;
    }

    public void setIdEstadoContenedor(EstadoContenedor idEstadoContenedor) {
        this.idEstadoContenedor = idEstadoContenedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numContenedor != null ? numContenedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contenedor)) {
            return false;
        }
        Contenedor other = (Contenedor) object;
        if ((this.numContenedor == null && other.numContenedor != null) || (this.numContenedor != null && !this.numContenedor.equals(other.numContenedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Contenedor[ numContenedor=" + numContenedor + " ]";
    }
    
}
