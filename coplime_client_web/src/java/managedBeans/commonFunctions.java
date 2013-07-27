/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author victor
 */
public class commonFunctions {
    
    /**
     * Pasale una url incluyendo /faces/ y te redirecciona
     * @param pagina 
     */
    public void goToPage(String pagina) {
       ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
       try {
           externalContext.redirect(externalContext.getRequestContextPath() + pagina);
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }
    
    public void goToIndex() {
        goToPage("/faces/index.xhtml");
    }
    
    public String getUsuarioLogueado() {
        HttpServletRequest request = (HttpServletRequest) 
                (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        return request.getRemoteUser();
    }
    
    public boolean isUserInRole(String nombreRol) {
        HttpServletRequest request = (HttpServletRequest) 
                (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        return request.isUserInRole(nombreRol);
    }
    
    public boolean isGetMethod() {
        HttpServletRequest request = (HttpServletRequest) 
                (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        if (request.getMethod().equals("GET")) {
            return true;
        }
        return false;
    }
}
