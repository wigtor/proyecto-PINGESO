/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.IOException;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
@Named(value = "infoUsuarioManagedBeans")
@RequestScoped
public class infoUsuarioManagedBeans {
    @EJB
    private UserServiceLocal userService;
    
    

    String nombres;
    String apellidos;
    String rol;
    int rut;
    String idUsuario;
    int telefono;
    String email;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        System.out.println("nombre de usuario: "+request.getRemoteUser());
        if (!userService.setUsuarioLogueado(request.getRemoteUser())) {
            CommonFunctions.goToPage("/faces/users/logout.xhtml");
        }
        nombres = userService.getNombres();
        apellidos = userService.getApellidos();
        rol = userService.getRol();
        rut = userService.getRut();
        idUsuario = userService.getIdUsuario();
        email = userService.getEmail();
        telefono = userService.getTelefono();
        /*
        System.out.println("Es administrador: "+request.isUserInRole("Administrador"));
        System.out.println("Es inspector: "+request.isUserInRole("Inspector"));
        System.out.println("Es operario: "+request.isUserInRole("Operario"));
        System.out.println("Es cualquier wea: "+request.isUserInRole("blabla"));
        */
    }

    public void guardarCambios() {
        userService.cambiarDatosContacto(telefono, email);
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }
    
    public void goToPuntosLimpios() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }
    
    public void goToCambiarContrasegna() {
        CommonFunctions.goToPage("/faces/users/cambiarContrasegna.xhtml");
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
    
}
