/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author victor
 */
@Entity
@NamedQueries( {
    @NamedQuery(name="Rol.findByName", query="SELECT u FROM Rol u WHERE u.nombreRol = :nombre") 
})
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String nombreRol;
    
    
    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreRol != null ? nombreRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.nombreRol == null && other.nombreRol != null) || (this.nombreRol != null && !this.nombreRol.equals(other.nombreRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Rol[ id=" + nombreRol + " ]";
    }
    
}
