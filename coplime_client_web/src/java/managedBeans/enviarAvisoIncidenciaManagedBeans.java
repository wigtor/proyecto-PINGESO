/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.TipoIncidencia;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import sessionBeans.AvisosIncidenciaLocal;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author victor
 */
@Named(value = "enviarAvisoIncidenciaManagedBeans")
@RequestScoped
public class enviarAvisoIncidenciaManagedBeans extends commonFunctions{
    @EJB
    private AvisosIncidenciaLocal avisosIncidencia;
    
    private Integer numPuntoLimpio;
    private String emailContacto;
    private String detalles;
    private Integer tipoIncidenciaSeleccionada;
    private Collection<SelectElemPojo> listaTiposIncidencias;
    private UploadedFile file;

    public AvisosIncidenciaLocal getAvisosIncidencia() {
        return avisosIncidencia;
    }

    public void setAvisosIncidencia(AvisosIncidenciaLocal avisosIncidencia) {
        this.avisosIncidencia = avisosIncidencia;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    @PostConstruct
    public void init() {
        Collection<TipoIncidencia> listaTemp = avisosIncidencia.getTiposAvisos();
        this.listaTiposIncidencias = new LinkedList();
        SelectElemPojo elemTemp;
        for(TipoIncidencia temp : listaTemp) {
            elemTemp = new SelectElemPojo();
            
            elemTemp.setId(temp.getId().toString());
            elemTemp.setLabel(temp.getNombreIncidencia());
            this.listaTiposIncidencias.add(elemTemp);
        
        }
    }
    
    public void enviarAviso() {
        System.out.println("N° " + numPuntoLimpio + " email: "+ emailContacto + " detalles: " + detalles + " tipo: " + tipoIncidenciaSeleccionada);
        if(file != null) {
            byte[] datosImagen = file.getContents();
            //String nombreArchivo = file.getFileName();
            String tipoArchivo = file.getContentType();
            //System.out.println("Nombre archivo: " + nombreArchivo + " tipo: " + tipoArchivo);
            if (numPuntoLimpio != null) {
                if (tipoIncidenciaSeleccionada != null)
                avisosIncidencia.guardarAvisoIncidencia(numPuntoLimpio.intValue(), emailContacto, detalles, tipoIncidenciaSeleccionada.intValue(), datosImagen, tipoArchivo);
            }
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            String url = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestContextPath();
            url += "/faces/enviarAvisoIncidencia.xhtml#success";
            try {
                externalContext.redirect(url);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            //Mostrar algún mensaje: Su aviso ha sido realizado satisfactoriamente, gracias por ayudarnos a prestar un mejor servicio
        }
    }

    public Integer getNumPuntoLimpio() {
        return numPuntoLimpio;
    }

    public void setNumPuntoLimpio(Integer numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }
    
    public Integer getTipoIncidenciaSeleccionada() {
        return tipoIncidenciaSeleccionada;
    }
    
    public void setTipoIncidenciaSeleccionada(Integer tipoIncidenciaSeleccionada) {
        this.tipoIncidenciaSeleccionada = tipoIncidenciaSeleccionada;
    }

    public Collection<SelectElemPojo> getListaTiposIncidencias() {
        return listaTiposIncidencias;
    }

    public void setListaTiposIncidencias(Collection<SelectElemPojo> listaTiposIncidencias) {
        this.listaTiposIncidencias = listaTiposIncidencias;
    }
    
    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
    
    
    /**
     * Creates a new instance of enviarAvisoIncidenciaManagedBeans
     */
    public enviarAvisoIncidenciaManagedBeans() {
    }
    
    public void goToIndex() {
        goToPage("/faces/index.xhtml");
    }
    
}
