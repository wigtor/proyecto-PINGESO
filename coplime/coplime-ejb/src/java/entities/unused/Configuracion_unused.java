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
import javax.persistence.NamedQuery;
import javax.persistence.Column;
import javax.persistence.NamedQueries;

/**
 *
 * @author Armando
 */
//@Entity
//@NamedQueries( {
    //@NamedQuery(name="Configuracion.findByParam", query="SELECT u FROM Configuracion u WHERE u.idParam = :idParam"),
    //@NamedQuery(name="Configuracion.findByParamLike", query="SELECT u FROM Configuracion u WHERE u.idParam LIKE :cadenaBusq")
//} )
public class Configuracion_unused implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String idParam;
    
    @Column
    private String valorParam;

    public String getIdParam() {
        return idParam;
    }

    public void setIdParam(String idParam) {
        this.idParam = idParam;
    }

    public String getValorParam() {
        return valorParam;
    }

    public void setValorParam(String valorParam) {
        this.valorParam = valorParam;
    }

    public Configuracion_unused(String idParam, String valorParam) {
        this.idParam = idParam;
        this.valorParam = valorParam;
    }

    public Configuracion_unused(String idParam) {
        this.idParam = idParam;
    }

    public Configuracion_unused() {
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParam != null ? idParam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion_unused)) {
            return false;
        }
        Configuracion_unused other = (Configuracion_unused) object;
        if ((this.idParam == null && other.idParam != null) || (this.idParam != null && !this.idParam.equals(other.idParam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Configuracion_unused[ id=" + idParam + " ]";
    }
    
}
