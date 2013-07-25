/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectsForManagedBeans;

import java.util.Date;

/**
 *
 * @author victor
 */
public class PuntoLimpioPojo {
    private Integer id;
    private Integer num;
    private String nombre;
    private String estado;
    private Date fechaProximaRev;
    private String fechaProximaRevStr;
    
    //Usados para almacenar temporalmente lo que se ha ingresado para crear un punto limpio
    private Integer idEstado;
    private Integer idComuma;
    private String direccion;
    private Integer idInspectorEncargado;

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

    public Date getFechaProximaRev() {
        return fechaProximaRev;
    }

    public void setFechaProximaRev(Date fechaProximaRev) {
        this.fechaProximaRev = fechaProximaRev;
    }
    
    public String getFechaProximaRevStr() {
        return fechaProximaRevStr;
    }

    public void setFechaProximaRevStr(String fechaProximaRevStr) {
        this.fechaProximaRevStr = fechaProximaRevStr;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdComuma() {
        return idComuma;
    }

    public void setIdComuma(Integer idComuma) {
        this.idComuma = idComuma;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getIdInspectorEncargado() {
        return idInspectorEncargado;
    }

    public void setIdInspectorEncargado(Integer idInspectorEncargado) {
        this.idInspectorEncargado = idInspectorEncargado;
    }
    
}
