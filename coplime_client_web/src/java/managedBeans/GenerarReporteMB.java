/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import otros.CommonFunctions;

/**
 *
 * @author victor
 */
@Named(value = "generarReporteMB")
@RequestScoped
public class GenerarReporteMB {

    private List<String> selectedOptions;
    private Map<String, String> availablesOptions;
    private Calendar fechaIni;
    private Calendar fechaFin;
    private StreamedContent file;

    /**
     * Creates a new instance of GenerarReporteMB
     */
    public GenerarReporteMB() {
        this.availablesOptions = new HashMap<>();
        this.availablesOptions.put("numero", "Número");
        this.availablesOptions.put("nombre", "Nombre");
        this.availablesOptions.put("comuna", "Comuna");
        this.availablesOptions.put("ubicacion", "Ubicación");
        this.availablesOptions.put("cantidadVisitas", "Cantidad de visitas");
        this.availablesOptions.put("cantidadMantenciones", "Cantidad de mantenciones");
        this.availablesOptions.put("inspectorEncargado", "Inspector encargado");
        this.availablesOptions.put("fechaUltimaRevision", "Fecha última revisión");
        this.availablesOptions.put("fechaProximaRevision", "Fecha próxima revisión");
        selectedOptions = new LinkedList<>();
        
        //Selecciono todo por defecto
        for (String strTemp : availablesOptions.keySet()) {
            this.selectedOptions.add(strTemp);
        }
        
        //Doy las fechas por defecto
        Calendar fechaActual = Calendar.getInstance();
        this.fechaFin = fechaActual;
        Calendar fechaFinal = (Calendar)fechaActual.clone();
        fechaFinal.add(Calendar.MONTH, -1);
        this.fechaIni = fechaFinal;
    }

    public void goToPuntosLimpios() {
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }

    public void generarReporte() {
        HSSFWorkbook libroReporte = construirExcel();

        String path = System.getProperty("java.home");
        path = path.concat(File.separator).concat("reportes_coplime").concat(File.separator);
        File carpeta = new File(path);
        if (carpeta.mkdir()) {
            System.out.println("Se ha creado la carpeta de reportes");
        }
        String onlyName = "reporteGenerado";
        String timeStr = Long.toString(Calendar.getInstance().getTimeInMillis());
        String nombreArchivo = path.concat(onlyName).concat(timeStr).concat(".xls");
        FileOutputStream streamEscritura;
        try {
            streamEscritura = new FileOutputStream(nombreArchivo);
            libroReporte.write(streamEscritura);
            streamEscritura.close();
            
            InputStream stream = new FileInputStream(new File(nombreArchivo));
            file = new DefaultStreamedContent(stream, "application/xls", onlyName.concat(".xls"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenerarReporteMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenerarReporteMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public HSSFWorkbook construirExcel() {
        HSSFWorkbook libroReporte= new HSSFWorkbook();
        HSSFSheet hojaReporte = libroReporte.createSheet();
        HSSFCellStyle style = libroReporte.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        HSSFRow cfilaReporteCabecera = hojaReporte.createRow(0);
        HSSFCell celdaTemp = cfilaReporteCabecera.createCell(0);
        celdaTemp.setCellValue("Reporte de puntos limpios");
        celdaTemp = cfilaReporteCabecera.createCell(3);
        celdaTemp.setCellValue("Desde:");
        celdaTemp = cfilaReporteCabecera.createCell(4);
        celdaTemp.setCellValue(Integer.toString(this.fechaIni.get(Calendar.DAY_OF_MONTH)).
                concat("-").concat(Integer.toString(this.fechaIni.get(Calendar.MONTH))).
                concat("-").concat(Integer.toString(this.fechaIni.get(Calendar.YEAR))));
        celdaTemp = cfilaReporteCabecera.createCell(5);
        celdaTemp.setCellValue("Hasta:");
        celdaTemp = cfilaReporteCabecera.createCell(6);
        celdaTemp.setCellValue(Integer.toString(this.fechaFin.get(Calendar.DAY_OF_MONTH)).
                concat("-").concat(Integer.toString(this.fechaFin.get(Calendar.MONTH))).
                concat("-").concat(Integer.toString(this.fechaFin.get(Calendar.YEAR))));
        cfilaReporteCabecera = hojaReporte.createRow(1);
        int i = 0;
        for (String opcion : selectedOptions) {
            celdaTemp = cfilaReporteCabecera.createCell(i);
            celdaTemp.setCellValue(opcion);
            i++;
        }
        
        
        return libroReporte;
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

    public Date getFechaIni() {
        return fechaIni.getTime();
    }

    public void setFechaIni(Date fechaIni) {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaIni);
        this.fechaIni = fecha;
    }

    public Date getFechaFin() {
        return fechaFin.getTime();
    }

    public void setFechaFin(Date fechaFin) {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaFin);
        this.fechaFin = fecha;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
}
