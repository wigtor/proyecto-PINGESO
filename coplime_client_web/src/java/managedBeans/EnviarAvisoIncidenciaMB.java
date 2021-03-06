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
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import otros.CommonFunctions;
import sessionBeans.AvisosIncidenciaLocal;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "enviarAvisoIncidenciaMB")
@RequestScoped
public class EnviarAvisoIncidenciaMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    @EJB
    private AvisosIncidenciaLocal avisosIncidencia;
    
    private Integer numPuntoLimpio;
    private String nombre_presentacion_ptoLimpio;
    private String emailContacto;
    private String detalles;
    private Integer tipoIncidenciaSeleccionada;
    private Collection<SelectElemPojo> listaTiposIncidencias;
    private UploadedFile file;
    
    @PostConstruct
    public void init() {
        System.out.println("Ejecutando init enviarAvisoIncidencia");
        if (!puntoLimpioIsSelected()) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    "No ha seleccionado un punto limpio", 
                    "No ha seleccionado un punto limpio para enviar el aviso de incidencia");
            CommonFunctions.goToPage("/faces/selectPtoLimpioAviso.xhtml?faces-redirect=true");
        }
        cargarTiposIncidencia();
    }
    
    private boolean puntoLimpioIsSelected() {
        /*
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest obj = (HttpServletRequest)externalContext.getRequest();
        if (!obj.getMethod().equals("GET")) {
            return true;
        }
        */
        
        String paramNumPtoLimpio = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().
                get("id");
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
            PuntoLimpio p = crudPuntoLimpio.getPuntoLimpioByNum(this.numPuntoLimpio);
            String nombre_ptoLimpio;
            if (p != null) {
                nombre_ptoLimpio = p.getNombre();
                this.nombre_presentacion_ptoLimpio = Integer.toString(this.numPuntoLimpio).concat(" - ").concat(nombre_ptoLimpio);
                return true;
            }
            return false;
        }
    }
    
    private void cargarTiposIncidencia() {
        Collection<TipoIncidencia> listaTemp = avisosIncidencia.getTiposAvisos();
        this.listaTiposIncidencias = new LinkedList<>();
        SelectElemPojo elemTemp;
        for(TipoIncidencia temp : listaTemp) {
            elemTemp = new SelectElemPojo();
            
            elemTemp.setId(temp.getId().toString());
            elemTemp.setLabel(temp.getNombreIncidencia());
            this.listaTiposIncidencias.add(elemTemp);
        }
    }
    
    public void enviarAviso() {
        byte[] datosImagen = null;
        String tipoArchivo = null;
        if(file != null) {
            datosImagen = file.getContents();
            tipoArchivo = file.getContentType();
        }
        if (numPuntoLimpio != null) {
            if (tipoIncidenciaSeleccionada != null) {
                try {
                    avisosIncidencia.guardarAvisoIncidencia(numPuntoLimpio.intValue(),
                            emailContacto, detalles, tipoIncidenciaSeleccionada.intValue(),
                            datosImagen, tipoArchivo);
                    CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                            "Se ha enviado su aviso de incidencia",
                            "Se ha enviado su aviso de incidencia, le avisaremos cuando sea atendida su sugerencia o problema");
                }
            catch (Exception e) {
                    CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                            e.getMessage(),
                            e.getMessage());
                }
            } else {
                CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "Error",
                        "Error, no ha seleccionado un tipo de incidencia");
            }
        }
        else {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "Error, no ha seleccionado un punto limpio");
        }
        CommonFunctions.goToPage("/faces/index.xhtml?faces-redirect=true");
    }
    
    public void submitCaptcha(ActionEvent event) {  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Correcto");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void goToSeleccionarPuntoLimpio() {
        CommonFunctions.goToPage("/faces/selectPtoLimpioAviso.xhtml");
    }
    
    
    public EnviarAvisoIncidenciaMB() {
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
    
}
