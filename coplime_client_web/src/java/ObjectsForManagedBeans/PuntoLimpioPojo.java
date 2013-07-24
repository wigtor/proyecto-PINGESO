/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectsForManagedBeans;

/**
 *
 * @author victor
 */
public class PuntoLimpioPojo {
    private Integer id;
    private Integer num;
    private String nombre;
    private String estado;
    private String fechaProximaRev;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaProximaRev() {
        return fechaProximaRev;
    }

    public void setFechaProximaRev(String fechaProximaRev) {
        this.fechaProximaRev = fechaProximaRev;
    }
}
