/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectsForManagedBeans;

/**
 *
 * @author victor
 */
public class SolicitudPojo {
    Integer num;
    String detalleCortado;
    String fecha;
    String usuarioEmisor;
    String usuarioReceptor;
    boolean revisada;
    boolean resuelta;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDetalleCortado() {
        return detalleCortado;
    }

    public void setDetalleCortado(String detalleCortado) {
        this.detalleCortado = detalleCortado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuarioEmisor() {
        return usuarioEmisor;
    }

    public void setUsuarioEmisor(String usuarioEmisor) {
        this.usuarioEmisor = usuarioEmisor;
    }
    
    public String getUsuarioReceptor() {
        return usuarioReceptor;
    }

    public void setUsuarioReceptor(String usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
    }

    public boolean isRevisada() {
        return revisada;
    }

    public void setRevisada(boolean revisada) {
        this.revisada = revisada;
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }
    
}
