/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author victor
 */
@Entity
@NamedQueries( {
    @NamedQuery(name="TipoIncidencia.findAllVisibles", query="SELECT u FROM TipoIncidencia u WHERE u.visibleAlUsuario = true"),
    @NamedQuery(name="TipoIncidencia.findById", query="SELECT u FROM TipoIncidencia u WHERE u.id = :id"),
    @NamedQuery(name="TipoIncidencia.findByName", query="SELECT u FROM TipoIncidencia u WHERE u.nombreIncidencia = :nombre")
})
public class TipoIncidencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String nombreIncidencia;
    
    @Column(nullable = false)
    private boolean visibleAlUsuario;
    
    
    public TipoIncidencia(String nombre, boolean visibleUser) {
        this.nombreIncidencia = nombre;
        this.visibleAlUsuario = visibleUser;
    }
    
    public TipoIncidencia() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreIncidencia() {
        return nombreIncidencia;
    }

    public void setNombreIncidencia(String nombreIncidencia) {
        this.nombreIncidencia = nombreIncidencia;
    }

    public boolean isVisibleAlUsuario() {
        return visibleAlUsuario;
    }

    public void setVisibleAlUsuario(boolean visibleAlUsuario) {
        this.visibleAlUsuario = visibleAlUsuario;
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
        if (!(object instanceof TipoIncidencia)) {
            return false;
        }
        TipoIncidencia other = (TipoIncidencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TipoIncidencia[ id=" + id + " ]";
    }
    
}
