/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author victor
 */
@Named(value = "userSessionBean")
@SessionScoped
public class UserSessionBean implements Serializable {
    private String originalURL;
    private boolean loggedIn;
    

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public void recordOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
    /**
     * Creates a new instance of UserSessionBean
     */
    public UserSessionBean() {
    }
}
