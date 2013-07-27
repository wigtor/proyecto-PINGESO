/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import entities.PuntoLimpio;
import entities.TipoIncidencia;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.UploadedFile;
import sessionBeans.AvisosIncidenciaLocal;

/**
 *
 * @author victor
 */
@Named(value = "enviarAvisoIncidenciaManagedBeans")
@RequestScoped
public class enviarAvisoIncidenciaManagedBeans extends commonFunctions {
    @EJB
    private AvisosIncidenciaLocal avisosIncidencia;
    
    private Integer numPuntoLimpio;
    private String nombre_presentacion_ptoLimpio;
    private String emailContacto;
    private String detalles;
    private Integer tipoIncidenciaSeleccionada;
    private Collection<SelectElemPojo> listaTiposIncidencias;
    private Collection<SelectElemPojo> listaPuntosLimpios;
    private UploadedFile file;
    
    @PostConstruct
    public void init() {
        
        System.out.println("Ejecutando init enviarAvisoIncidencia");
        if (!seIntentaSeleccionarPuntoLimpio()) {
            if (!puntoLimpioIsSelected()) {
                goToPage("/faces/selectPtoLimpioAviso.xhtml");
            }
            cargarTiposIncidencia();
        }
        cargarPuntosLimpios();
    }
    
    private boolean seIntentaSeleccionarPuntoLimpio() {
        HttpServletRequest wea = ((HttpServletRequest)FacesContext.getCurrentInstance().
                getExternalContext().getRequest());
        String view = wea.getPathInfo();
        if (view.contains("selectPtoLimpioAviso.xhtml")) {
            return true;
        }
        return false;
    }
    
    private boolean puntoLimpioIsSelected() {
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest obj = (HttpServletRequest)externalContext.getRequest();
        if (!obj.getMethod().equals("GET")) {
            return true;
        }
        
        String paramNumPtoLimpio = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().
                get("id");
        System.out.println("param: "+ paramNumPtoLimpio);
        if (paramNumPtoLimpio == null ){ //No se ha seleccionado aún, redirecciono
            return false;
        }
        else {
            //Validar que punto limpio existe y es un número realmente
            try {
                this.numPuntoLimpio = Integer.parseInt(paramNumPtoLimpio);
            }
            catch (NumberFormatException nfe) {
                return false;
            }
            String nombre_ptoLimpio = avisosIncidencia.getNombrePtoLimpio(this.numPuntoLimpio);
            if (nombre_ptoLimpio != null) {
                this.nombre_presentacion_ptoLimpio = this.numPuntoLimpio + " - "+nombre_ptoLimpio;
                return true;
            }
            return false;
        }
    }
    
    private void cargarTiposIncidencia() {
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
    
    private void cargarPuntosLimpios() {
        Collection<PuntoLimpio> listaTemp = avisosIncidencia.getPuntosLimpios();
        this.listaPuntosLimpios = new LinkedList();
        SelectElemPojo elemTemp;
        for(PuntoLimpio temp : listaTemp) {
            elemTemp = new SelectElemPojo();
            
            elemTemp.setId(temp.getId().toString());
            elemTemp.setLabel("N°"+temp.getId()+ " - " + temp.getUbicacion());
            this.listaPuntosLimpios.add(elemTemp);
        
        }
    }
    
    public void enviarAviso() {
        
        System.out.println("N° " + numPuntoLimpio + " email: "+ emailContacto + " detalles: " + detalles + " tipo: " + tipoIncidenciaSeleccionada);
        byte[] datosImagen = null;
        String tipoArchivo = null;
        if(file != null) {
            datosImagen = file.getContents();
            //String nombreArchivo = file.getFileName();
            tipoArchivo = file.getContentType();
        }
        //System.out.println("Nombre archivo: " + nombreArchivo + " tipo: " + tipoArchivo);
        if (numPuntoLimpio != null) {
            if (tipoIncidenciaSeleccionada != null)
            avisosIncidencia.guardarAvisoIncidencia(numPuntoLimpio.intValue(), emailContacto, detalles, tipoIncidenciaSeleccionada.intValue(), datosImagen, tipoArchivo);
        }
        goToPage("/faces/index.xhtml?success=1");
    }
    
    public void submitCaptcha(ActionEvent event) {  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Correcto");  
          
        FacesContext.getCurrentInstance().addMessage(null, msg);  
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

    public Collection<SelectElemPojo> getListaPuntosLimpios() {
        return listaPuntosLimpios;
    }

    public void setListaPuntosLimpios(Collection<SelectElemPojo> listaPuntosLimpios) {
        this.listaPuntosLimpios = listaPuntosLimpios;
    }
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
    
    public String getNombre_presentacion_ptoLimpio() {
        return nombre_presentacion_ptoLimpio;
    }
    
    public void goToEnviarAviso(int idSeleccionado) {
        System.out.println("idPtoLimpio="+idSeleccionado);
        goToPage("/faces/enviarAvisoIncidencia.xhtml"+"?id="+idSeleccionado);
    }
    
    public void goToSeleccionarPuntoLimpio() {
        goToPage("/faces/selectPtoLimpioAviso.xhtml");
    }
    /**
     * Creates a new instance of enviarAvisoIncidenciaManagedBeans
     */
    public enviarAvisoIncidenciaManagedBeans() {
    }
}
