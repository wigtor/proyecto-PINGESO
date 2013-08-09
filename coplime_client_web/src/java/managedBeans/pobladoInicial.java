/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        try {
            String pre = externalContext.getRequestContextPath();
            externalContext.redirect(pre.concat("/faces/index.xhtml"));
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
