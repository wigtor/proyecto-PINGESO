/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.infoUsuario;

import entities.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import otros.CommonFunctions;
import sessionBeans.UserServiceLocal;

/**
 *
 * @author victor
 */
@Named(value = "infoUsuarioMB")
@RequestScoped
public class InfoUsuarioMB {
    @EJB
    private UserServiceLocal userService;
    
    
    String nombres;
    String apellidos;
    String rol;
    int rut;
    String idUsuario;
    String username;
    int telefono;
    String email;

    @PostConstruct
    public void init() {
        Usuario user = userService.buscarUsuario(CommonFunctions.getUsuarioLogueado());
        if (user == null) {
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
            return;
        }
        nombres = user.getNombre();
        apellidos = user.getApellido1().concat(" ").concat(user.getApellido2());
        rol = user.getRol().getNombreRol();
        rut = user.getRut();
        username = user.getUsername();
        //idUsuario = Integer.toString(user.getId());
        email = user.getEmail();
        telefono = user.getTelefono();
    }

    public void guardarCambios() {
        try {
            userService.cambiarDatosContacto(CommonFunctions.getUsuarioLogueado(), telefono, email);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han actualizados sus datos",
                    "Se ha actualizado su teléfono y mail de contacto");
            
        } catch (Exception ex) {
            Logger.getLogger(InfoUsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml?faces-redirect=true");
    }
    
    public void goToPuntosLimpios() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }
    
    public void goToCambiarContrasegna() {
        System.out.println("Se va a cambiar la contraseña");
        CommonFunctions.goToPage("/faces/users/cambiarContrasegna.xhtml");
    }

    
    public InfoUsuarioMB() {
    }
    
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getRol() {
        return rol;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }
    
    public int getRut() {
        return rut;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
