/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author victor
 */
@Entity
@NamedQueries( {
    @NamedQuery(name="Usuario.findByUsername", query="SELECT u FROM Usuario u WHERE u.username = :username"),
    @NamedQuery(name="Usuario.findByRut", query="SELECT u FROM Usuario u WHERE u.rut = :rut"),
    @NamedQuery(name="Usuario.deleteInspectorByRut", query="DELETE FROM Inspector u WHERE u.usuario.rut = :rut"),
    @NamedQuery(name = "Usuario.deleteOperarioByRut", query = "DELETE FROM OperarioMantencion u WHERE u.usuario.rut = :rut"),
    @NamedQuery(name = "Usuario.deleteAdministradorByRut", query = "DELETE FROM Administrador u WHERE u.usuario.rut = :rut"),
    @NamedQuery(name = "Usuario.checkByRut", query = "SELECT COUNT(U) FROM Usuario u WHERE u.rut = :rut"),
    @NamedQuery(name = "Usuario.checkByUsername", query = "SELECT COUNT(U) FROM Usuario u WHERE u.username = :username")
})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private Integer rut;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellido1;
    
    @Column(nullable = false)
    private String apellido2;
    
    @Column(nullable = false)
    private String email;
    
    private int telefono;
    
    @JoinColumn(nullable = false)
    @ManyToOne
    private Rol rol;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }
    public void setRut(int rut) {
        this.rut = rut;
    }
    
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Usuario[ id=" + username + " ]";
    }
    
}
