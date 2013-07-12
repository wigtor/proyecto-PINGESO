/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.TipoIncidencia;
import java.util.Collection;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import sessionBeans.AvisosIncidenciaLocal;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author victor
 */
@Named(value = "enviarAvisoIncidenciaManagedBeans")
@RequestScoped
public class enviarAvisoIncidenciaManagedBeans {
    @EJB
    private AvisosIncidenciaLocal avisosIncidencia;
    
    private Integer numPuntoLimpio;
    private String emailContacto;
    private String detalles;
    private String tipoIncidenciaSeleccionada;
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
            String nombreArchivo = file.getFileName();
            String tipoArchivo = file.getContentType();
            System.out.println("Nombre archivo: " + nombreArchivo + " tipo: " + tipoArchivo);
            //avisosIncidencia.guardarAvisoIncidencia(numPuntoLimpio, emailContacto, detalles, tipoIncidenciaSeleccionada, datosImagen, tipoArchivo);
        }
    }

    public Integer getNumPuntoLimpio() {
        return numPuntoLimpio;
    }

    public void setNumPuntoLimpio(Integer numPuntoLimpio) {
        this.numPuntoLimpio = numPuntoLimpio;
    }
    
    public String getTipoIncidenciaSeleccionada() {
        return tipoIncidenciaSeleccionada;
    }
    
    public void setTipoIncidenciaSeleccionada(String tipoIncidenciaSeleccionada) {
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
    
    
    
}
