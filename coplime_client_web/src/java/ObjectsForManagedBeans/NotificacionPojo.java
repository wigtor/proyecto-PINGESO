/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectsForManagedBeans;

/**
 *
 * @author victor
 */
public class NotificacionPojo {
    private int num;
    private String detallesCortado;
    private String detallesCompleto;
    private String fecha;
    private boolean revisado;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDetallesCortado() {
        return detallesCortado;
    }

    public void setDetallesCortado(String detallesCortado) {
        this.detallesCortado = detallesCortado;
    }
    
    public String getDetallesCompleto() {
        return detallesCompleto;
    }

    public void setDetallesCompleto(String detallesCompleto) {
        this.detallesCompleto = detallesCompleto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean getRevisado() {
        return revisado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }
    
}
