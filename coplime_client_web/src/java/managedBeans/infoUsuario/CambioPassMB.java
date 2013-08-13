/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.infoUsuario;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import otros.CommonFunctions;
import sessionBeans.UserServiceLocal;

/**
 *
 * @author victor
 */
@Named(value = "cambioPassMB")
@RequestScoped
public class CambioPassMB {
    @EJB
    private UserServiceLocal userService;
    String username;
    String passActual;
    String nvaPass;
    String nvaPassRep;

    @PostConstruct
    public void init() {
        this.username = CommonFunctions.getUsuarioLogueado();
    }

    public void guardarPass() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!nvaPass.equals(nvaPassRep)) {
            //Muestro el mensaje de error
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Las contraseñas no coinciden", ""));
        }
        else {
            try {
                userService.cambiarPass(CommonFunctions.getUsuarioLogueado(), passActual, nvaPass);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha cambiado correctamente la contraseña",
                    "Se ha actualizado su nueva contraseña en el sistema");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            
                CommonFunctions.goToPage("/faces/users/infoUsuario.xhtml?faces-redirect=true");
            }
            catch (Exception e) {
                //Muestro el mensaje de error
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
            }
        }
    }
    
    public void goToInfoUsuario() {
        CommonFunctions.goToPage("/faces/users/infoUsuario.xhtml");
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
