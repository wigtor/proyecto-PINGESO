/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;

/**
 *
 * @author victor
 */
@Entity
public class NotificacionDeUsuario extends Notificacion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String emailContacto;
    
    private String imagenAdjunta;
    
    private String tipoImagen;

    
    public NotificacionDeUsuario() {
    }
    
    public NotificacionDeUsuario(String comentarios, PuntoLimpio ptoRelacionado, TipoIncidencia tipoIncidencia) {
        this.puntoLimpio = ptoRelacionado;
        this.comentario = comentarios;
        this.fechaHora = Calendar.getInstance();
        this.revisado = false;
        this.resuelto = false;
        this.tipoIncidencia = tipoIncidencia;
    }
    
    public String getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getImagenAdjunta() {
        return imagenAdjunta;
    }

    public void setImagenAdjunta(String imagenAdjunta) {
        this.imagenAdjunta = imagenAdjunta;
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
        if (!(object instanceof NotificacionDeUsuario)) {
            return false;
        }
        NotificacionDeUsuario other = (NotificacionDeUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotificacionDeUsuario[ id=" + id + " ]";
    }
    
}
