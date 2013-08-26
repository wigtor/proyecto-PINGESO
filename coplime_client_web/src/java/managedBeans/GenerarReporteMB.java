/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import otros.CommonFunctions;

/**
 *
 * @author victor
 */
@Named(value = "generarReporteMB")
@RequestScoped
public class GenerarReporteMB {
    private List<String> selectedOptions;
    private Map<String,String> availablesOptions; 
    private Calendar fechaIni;
    private Calendar fechaFin;
    
    
    /**
     * Creates a new instance of GenerarReporteMB
     */
    public GenerarReporteMB() {
    }

    public void goToPuntosLimpios() {
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }
    
    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public Map<String, String> getAvailablesOptions() {
        return availablesOptions;
    }

    public void setAvailablesOptions(Map<String, String> availablesOptions) {
        this.availablesOptions = availablesOptions;
    }

    public Calendar getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Calendar fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Calendar getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
