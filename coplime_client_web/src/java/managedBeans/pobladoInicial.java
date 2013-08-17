/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
    
    public void poblar() {
        
        configuracionInicial.primeraEjecicion();
        CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha poblado la base de datos",
                    "Se ha poblado la base de datos y está todo listo para poder ejecutar COPLIME");
            CommonFunctions.goToPage("/faces/index.xhtml?faces-redirect=true");
    }
}
