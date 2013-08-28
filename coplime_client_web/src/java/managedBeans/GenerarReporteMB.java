/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ObjectsForManagedBeans.SelectElemPojo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import sessionBeans.GeneradorReportesLocal;

/**
 *
 * @author victor
 */
@Named(value = "generarReporteMB")
@RequestScoped
public class GenerarReporteMB {
    @EJB
    private GeneradorReportesLocal generadorReportes;

    private List<String> selectedOptions;
    private List<SelectElemPojo> tiposReporte;
    private Integer tipoReporteSelected;
    private Map<String, Integer> availablesOptions;
    private Calendar fechaIni;
    private Calendar fechaFin;
    private StreamedContent file;

    /**
     * Creates a new instance of GenerarReporteMB
     */
    public GenerarReporteMB() {
    }
    
    @PostConstruct
    public void init() {
        cargarTiposReporte();
        cargarOpcionesReporte();
        
        //Doy las fechas por defecto
        Calendar fechaActual = Calendar.getInstance();
        this.fechaFin = fechaActual;
        Calendar fechaFinal = (Calendar)fechaActual.clone();
        fechaFinal.add(Calendar.MONTH, -1);
        this.fechaIni = fechaFinal;
    }
    
    private void cargarTiposReporte() {
        this.tiposReporte = new LinkedList<>();
        this.tiposReporte.add(new SelectElemPojo(Integer.toString(GeneradorReportesLocal.DATOS_PUNTOS_LIMPIOS), "Datos de puntos limpios"));
        this.tiposReporte.add(new SelectElemPojo(Integer.toString(GeneradorReportesLocal.MANTENCIONES_PUNTO_LIMPIO), "Mantenciones de puntos limpios"));
        this.tiposReporte.add(new SelectElemPojo(Integer.toString(GeneradorReportesLocal.REVISIONES_PUNTO_LIMPIO), "Revisiones de puntos limpios"));
        this.tiposReporte.add(new SelectElemPojo(Integer.toString(GeneradorReportesLocal.SOLICITUDES_PUNTO_LIMPIO), "Solicitudes de puntos limpios"));
        this.tiposReporte.add(new SelectElemPojo(Integer.toString(GeneradorReportesLocal.USUARIOS_SISTEMA), "Usuarios registrados en el sistema"));
        this.tipoReporteSelected = GeneradorReportesLocal.DATOS_PUNTOS_LIMPIOS;
        System.out.println("tipo reporte selected: "+this.tipoReporteSelected);
    }
    
    private void cargarOpcionesReporte() {
        this.availablesOptions = generadorReportes.getOpcionesReporte(this.tipoReporteSelected);
        
        selectedOptions = new LinkedList<>();
        
        //Selecciono todo por defecto
        for (Integer strTemp : availablesOptions.values()) {
            this.selectedOptions.add(strTemp.toString());
        }
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
    
    public void updateOpcionesReporte() {
        cargarOpcionesReporte();
    }
    
    public HSSFWorkbook construirExcel() {
        
        HSSFWorkbook libroReporte= new HSSFWorkbook();
        HSSFSheet hojaReporte = libroReporte.createSheet();
        HSSFCellStyle style = libroReporte.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        HSSFRow cfilaReporteCabecera = hojaReporte.createRow(0);
        HSSFCell celdaTemp = cfilaReporteCabecera.createCell(0);
        celdaTemp.setCellValue("Reporte de puntos limpios");
        celdaTemp = cfilaReporteCabecera.createCell(1);
        celdaTemp.setCellValue("Desde:");
        celdaTemp = cfilaReporteCabecera.createCell(2);
        celdaTemp.setCellValue(Integer.toString(this.fechaIni.get(Calendar.DAY_OF_MONTH)).
                concat("-").concat(Integer.toString(this.fechaIni.get(Calendar.MONTH))).
                concat("-").concat(Integer.toString(this.fechaIni.get(Calendar.YEAR))));
        celdaTemp = cfilaReporteCabecera.createCell(3);
        celdaTemp.setCellValue("Hasta:");
        celdaTemp = cfilaReporteCabecera.createCell(4);
        celdaTemp.setCellValue(Integer.toString(this.fechaFin.get(Calendar.DAY_OF_MONTH)).
                concat("-").concat(Integer.toString(this.fechaFin.get(Calendar.MONTH))).
                concat("-").concat(Integer.toString(this.fechaFin.get(Calendar.YEAR))));
        
        
        String[][] datosReporte = generadorReportes.getDatosReporte(this.tipoReporteSelected, this.selectedOptions, fechaIni, fechaFin);
        
        if (datosReporte == null) {
            return libroReporte;
        }
        
        int i; //Comienzo en la segunda fila
        int j;
        System.out.println("CACA");
        HSSFRow cFilaTemp;
        i = 1;
        for (String[] fila : datosReporte) {
            cFilaTemp = hojaReporte.createRow(i);
            j = 0;
            for (String celda : fila) {
                celdaTemp = cFilaTemp.createCell(j);
                celdaTemp.setCellValue(celda);
                j++;
            }
            i++;
        }
        if (datosReporte.length > 0) {
            for (i = 0; i < datosReporte[0].length; i++) {
                hojaReporte.autoSizeColumn(i);
            }
        }
        return libroReporte;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public Map<String, Integer> getAvailablesOptions() {
        return availablesOptions;
    }

    public void setAvailablesOptions(Map<String, Integer> availablesOptions) {
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

    public List<SelectElemPojo> getTiposReporte() {
        return tiposReporte;
    }

    public void setTiposReporte(List<SelectElemPojo> tiposReporte) {
        this.tiposReporte = tiposReporte;
    }

    public Integer getTipoReporteSelected() {
        return tipoReporteSelected;
    }

    public void setTipoReporteSelected(Integer tipoReporteSelected) {
        this.tipoReporteSelected = tipoReporteSelected;
    }
    
}
