/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.PuntoLimpioPojo;
import java.io.IOException;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author victor
 */
@ManagedBean
@Named(value = "mantenedorPuntoLimpio")
@RequestScoped
public class mantenedorPuntoLimpio {
    private List<PuntoLimpioPojo> lista; //collection
    private PuntoLimpioPojo elementoSelecionado;
    
    
    public List<PuntoLimpioPojo> getLista() {
        return lista;
    }

    public void setLista(List<PuntoLimpioPojo> lista) {
        this.lista = lista;
    }

    public PuntoLimpioPojo getElementoSelecionado() {
        return elementoSelecionado;
    }

    public void setElementoSelecionado(PuntoLimpioPojo elementoSelecionado) {
        this.elementoSelecionado = elementoSelecionado;
    }
    
    
    /**
     * Creates a new instance of mantenedorPuntoLimpio
     */
    public mantenedorPuntoLimpio() {
    }
    public void agregar() {
       ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
       try {
           externalContext.redirect(externalContext.getRequestContextPath() + "/faces/admin/agregarPuntoLimpio.xhtml");
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }
}
