/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import sessionBeans.UserServiceLocal;

/**
 *
 * @author victor
 */
@Named(value = "cambioPassManagedBeans")
@RequestScoped
public class cambioPassManagedBeans extends commonFunctions{
    @EJB
    private UserServiceLocal userService;
    
    String username;
    String passActual;
    String nvaPass;
    String nvaPassRep;

    @PostConstruct
    public void init() {
        /*
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        System.out.println("nombre de usuario: "+request.getRemoteUser());
        if (!userService.setUsuarioLogueado(request.getRemoteUser())) {
            goToPage("/faces/users/logout.xhtml");
        }
        username = userService.getUsername();
        */
    }

    public void guardarPass() {
        //userService.cambiarDatosContacto(telefono, email);
        goToPage("/faces/users/infoUsuario.xhtml");
    }
    
    public void goToInfoUsuario() {
       goToPage("/faces/users/infoUsuario.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassActual() {
        return passActual;
    }

    public void setPassActual(String passActual) {
        this.passActual = passActual;
    }

    public String getNvaPass() {
        return nvaPass;
    }

    public void setNvaPass(String nvaPass) {
        this.nvaPass = nvaPass;
    }

    public String getNvaPassRep() {
        return nvaPassRep;
    }

    public void setNvaPassRep(String nvaPassRep) {
        this.nvaPassRep = nvaPassRep;
    }
}
