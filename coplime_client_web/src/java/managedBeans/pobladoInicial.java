/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import otros.CommonFunctions;
import sessionBeans.configuracionInicialLocal;

/**
 *
 * @author victor
 */
@Named(value = "pobladoInicial")
@RequestScoped
public class pobladoInicial {
    @EJB
    private configuracionInicialLocal configuracionInicial;

    /**
     * Creates a new instance of pobladoInicial
     */
    public pobladoInicial() {
    }
    
    public void poblarTesting() {
        
        try {
            configuracionInicial.primeraEjecucionTesting();
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha poblado la base de datos en modo testing",
                    "Se ha poblado la base de datos y está todo listo para poder ejecutar COPLIME");
            CommonFunctions.goToPage("/faces/index.xhtml?faces-redirect=true");
        } 
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: La configuración iniciar ya se había realizado con anterioridad",
                    "Ha ocurrido un error ya que el sistema ya ha sido configurado anteriormente");
            CommonFunctions.goToPage("/faces/index.xhtml?faces-redirect=true");
        }
    }
    
    public void poblar() {
        try {
            configuracionInicial.primeraEjecucion();
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha creado la config inicial. username: admin, password: emeresRoot2013",
                    "Se ha poblado la base de datos y está todo listo para poder ejecutar COPLIME");
            CommonFunctions.goToPage("/faces/index.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: La configuración iniciar ya se había realizado con anterioridad",
                    "Ha ocurrido un error ya que el sistema ya ha sido configurado anteriormente");
            CommonFunctions.goToPage("/faces/index.xhtml?faces-redirect=true");
        }
        
    }
}
