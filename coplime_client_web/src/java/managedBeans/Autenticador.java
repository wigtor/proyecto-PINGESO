/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.PostActivate;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
//import com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm;

/**
 *
 * @author victor
 */
@Named(value = "Autenticador")
@SessionScoped
public class Autenticador extends commonFunctions implements Serializable {

    private String username;
    private String password;
    private String originalURL;

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
    

    @PostConstruct
    public void init() {
        originalURL = (String)FacesContext.getCurrentInstance().getExternalContext()
            .getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        System.out.println("URL Original: "+originalURL);
        if (originalURL == null) {
            originalURL = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestContextPath()+"/faces/users/verPuntosLimpios.xhtml";
        }
    }
    
    public boolean verificaSiEstaLogueado() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        if (request.getRemoteUser() != null) {
            goToPage("/faces/users/verPuntosLimpios.xhtml");
            return true;
        }
        return false;
    }
    
    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            verificaSiEstaLogueado();
            request.login(username, password);
            externalContext.redirect(originalURL);
        }
        catch (Exception e) {
            System.out.println("MENSAJE DE EXCEPCIÓN: "+e.getMessage());
            context.addMessage("labelMensaje", new FacesMessage("Nombre de usuario o contraseña incorrectos"));
        }
    }

    public void logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        goToPage("/faces/index.xhtml");
    }
    
    public void goToEnviarAvisoIncidencia() {
        goToPage("/faces/enviarAvisoIncidencia.xhtml");
    }

    // Getters/setters for username and password.
}