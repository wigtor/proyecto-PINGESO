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
public class Autenticador implements Serializable{

    private String username;
    
    private String outputMsj = "Listo";

    public String getOutputMsj() {
        return outputMsj;
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
    private String password;
    private String originalURL;

    @PostConstruct
    public void init() {
        originalURL = (String)FacesContext.getCurrentInstance().getExternalContext()
            .getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        System.out.println("URL Original: "+originalURL);
        if (originalURL == null) {
            originalURL = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestContextPath()+"/faces/users/verPuntosLimpios.xhtml";
        }
        /*
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/faces/users/verPuntosLimpios.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
        */
    }
    
    
    public boolean verificaSiEstaLogueado() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        if (request.getRemoteUser() != null) {
            try {
                originalURL = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestContextPath()+"/faces/users/verPuntosLimpios.xhtml";
                externalContext.redirect(originalURL);
            }
            catch(IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            return true;
        }
        return false;
    }

    /*
    @EJB
    private UserServiceLocal userService;
    */
    
    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        System.out.println("getUserPrincipal(): "+request.getUserPrincipal());
        System.out.println("getAuthType(): "+request.getAuthType());
        System.out.println("getRemoteUser(): "+request.getRemoteUser());
        //JDBCRealm wea = new JDBCRealm();
        
        try {
            verificaSiEstaLogueado();
            request.login(username, password);
            /*
            Usuario user = userService.buscarUsuario(username, password);
            System.out.println("Al hacer login se ha obtenido: "+user);
            if (user == null) {
                outputMsj = "Login incorrecto";
                
            }
            externalContext.getSessionMap().put("user", user);
            */
            externalContext.redirect(originalURL);
        }
        catch (Exception e) {
            System.out.println("MENSAJE DE EXCEPCIÓN: "+e.getMessage());
            //e.printStackTrace();
            // Handle unknown username/password in request.login().
            context.addMessage(null, new FacesMessage("Unknown login"));
        }
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/index.xhtml");
    }
    
    public void goToEnviarAvisoIncidencia() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        System.out.println("cacacaca");
        try {
            String url = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestContextPath()+"/faces/enviarAvisoIncidencia.xhtml";
            externalContext.redirect(url);
        }
        catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    // Getters/setters for username and password.
}