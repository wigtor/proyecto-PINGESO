/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.puntoLimpio;

import ObjectsForManagedBeans.ContenedorPojo;
import ObjectsForManagedBeans.PuntoLimpioPojo;
import ObjectsForManagedBeans.SelectElemPojo;
import entities.Comuna;
import entities.Estado;
import entities.Inspector;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudInspectorLocal;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorPuntoLimpioAgregarMB")
@RequestScoped
public class MantenedorPuntoLimpioAgregarMB {

    @EJB
    private CrudInspectorLocal crudInspector;
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    @Inject
    private MantenedorPuntoLimpioConversation mantPtoLimpio;
    private Integer num;
    private String nombre;
    private Integer comuna_seleccionada;
    private List<SelectElemPojo> listaComunas;
    private String direccion;
    private Date fechaRevision;
    private Integer estado_seleccionado;
    private List<SelectElemPojo> listaEstadosPtoLimpio;
    private Integer inspectorEncargado_seleccionado;
    private List<SelectElemPojo> listaInspectores;

    /**
     * Creates a new instance of MantenedorPuntoLimpioAgregarMB
     */
    public MantenedorPuntoLimpioAgregarMB() {
    }

    @PostConstruct
    public void init() {
        cargarEstadosPuntoLimpio();
        cargarInspectores();
        cargarComunas();
        cargarDatosPtoLimpioTemporal();
    }

    private void cargarDatosPtoLimpioTemporal() {
        if (mantPtoLimpio.getPto_creando() != null) {
            PuntoLimpioPojo p = mantPtoLimpio.getPto_creando();
            this.comuna_seleccionada = p.getIdComuma();
            this.nombre = p.getNombre();
            this.direccion = p.getDireccion();
            this.inspectorEncargado_seleccionado = p.getIdInspectorEncargado();
            this.num = p.getNum();
            this.estado_seleccionado = p.getIdEstado();
            this.fechaRevision = p.getFechaProximaRev();
        }
    }

    private void cargarInspectores() {
        Collection<Inspector> listaTemp = crudInspector.getAllInspectores();
        SelectElemPojo elemTemp;
        this.listaInspectores = new ArrayList<>();
        for (Inspector insp_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(insp_iter.getId()));
            elemTemp.setLabel(insp_iter.getUsuario().getRut() + " - "
                    + insp_iter.getUsuario().getNombre() + " "
                    + insp_iter.getUsuario().getApellido1());
            this.listaInspectores.add(elemTemp);

        }
    }

    private void cargarEstadosPuntoLimpio() {
        Collection<Estado> listaTemp = crudPuntoLimpio.getAllEstadosPuntoLimpio();
        SelectElemPojo elemTemp;
        this.listaEstadosPtoLimpio = new ArrayList<>();
        for (Estado estado_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(estado_iter.getId()));
            elemTemp.setLabel(estado_iter.getNombreEstado());
            this.listaEstadosPtoLimpio.add(elemTemp);
        }
    }

    private void cargarComunas() {
        Collection<Comuna> listaTemp = crudPuntoLimpio.getAllComunas();
        SelectElemPojo elemTemp;
        this.listaComunas = new ArrayList<>();
        for (Comuna comuna_iter : listaTemp) {
            elemTemp = new SelectElemPojo();
            elemTemp.setId(Integer.toString(comuna_iter.getId()));
            elemTemp.setLabel(comuna_iter.getNombre());
            this.listaComunas.add(elemTemp);
        }
    }

    private PuntoLimpioPojo crearPtoLimpioTemporal() {
        PuntoLimpioPojo res = new PuntoLimpioPojo();
        res.setNum(num);
        res.setNombre(nombre);
        res.setIdInspectorEncargado(inspectorEncargado_seleccionado);
        res.setIdEstado(estado_seleccionado);
        res.setIdComuma(comuna_seleccionada);
        res.setDireccion(direccion);
        res.setFechaProximaRev(fechaRevision);
        return res;
    }

    public void agregarContenedores() {
        mantPtoLimpio.setPto_creando(crearPtoLimpioTemporal());
        CommonFunctions.goToPage("/faces/users/admin/agregarContenedor.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
    }
    
    public void eliminarContenedorAgregado(Integer id) {
        this.mantPtoLimpio.eliminarContenedor(id);
        CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
    }

    public void agregarPuntoLimpio() {
        Calendar fechaComoCalendar = new GregorianCalendar();
        fechaComoCalendar.setTime(fechaRevision);
        Integer numPuntoLimpio;
        try {
            numPuntoLimpio = crudPuntoLimpio.agregarPuntoLimpio(num, nombre, comuna_seleccionada, direccion, fechaComoCalendar, estado_seleccionado, inspectorEncargado_seleccionado);
        } catch (Exception ex) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            this.mantPtoLimpio.endConversation();
            CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
            return;
        }
        if (numPuntoLimpio != null) {
            //Agrego los contenedores ahora
            Integer idMaterial, idEstadoIni, llenadoIni, capacidad, idUnidadMedida;
            List<ContenedorPojo> listaContenedoresTemp = this.mantPtoLimpio.getContenedores_creando();
            for (ContenedorPojo contTemp : listaContenedoresTemp) {
                idMaterial = contTemp.getIdMaterial();
                idEstadoIni = contTemp.getIdEstadoContenedor();
                llenadoIni = contTemp.getLlenadoContenedor();
                capacidad = contTemp.getCapacidad();
                idUnidadMedida = contTemp.getIdUnidadMedida();
                //System.out.format("%d, %d, %d, %d, %d, %d\n\n", numPuntoLimpio, idMaterial, idEstadoIni, llenadoIni, capacidad, idUnidadMedida);
                try {
                    crudPuntoLimpio.agregarContenedor(
                        numPuntoLimpio, idMaterial, idEstadoIni, llenadoIni, capacidad, idUnidadMedida);
                }
                catch (Exception e) {
                    //Avisar que se agregó correctamente el punto limpio
                    CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                            e.getMessage(),
                            e.getMessage());
                }
            }
            //Avisar que se agregó correctamente el punto limpio
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha creado un nuevo punto limpio",
                    "Se ha creado el punto limpio \"".concat(nombre).concat("\""));

        } else {
            //Avisar que ocurrió un error al agregar el punto limpio
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "Ha ocurrido un error al crear el punto limpio",
                    "No ha sido posible crear el punto limpio, intente más tarde");
        }
        this.mantPtoLimpio.endConversation();
        volverToLista();
    }

    public void volverToLista() {
        this.mantPtoLimpio.endConversation();
        //System.out.println("Se hizo click en 'volverToLista()'");
        mantPtoLimpio.setPto_creando(null);
        mantPtoLimpio.getContenedores_creando().clear();
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml?faces-redirect=true");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getComuna_seleccionada() {
        return comuna_seleccionada;
    }

    public void setComuna_seleccionada(Integer comuna_seleccionada) {
        this.comuna_seleccionada = comuna_seleccionada;
    }

    public List<SelectElemPojo> getListaComunas() {
        return listaComunas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Integer getEstado_seleccionado() {
        return estado_seleccionado;
    }

    public void setEstado_seleccionado(Integer estado_seleccionado) {
        this.estado_seleccionado = estado_seleccionado;
    }

    public List<SelectElemPojo> getListaEstadosPtoLimpio() {
        return listaEstadosPtoLimpio;
    }

    public void setListaEstadosPtoLimpio(List<SelectElemPojo> listaEstadosPtoLimpio) {
        this.listaEstadosPtoLimpio = listaEstadosPtoLimpio;
    }

    public Integer getInspectorEncargado_seleccionado() {
        return inspectorEncargado_seleccionado;
    }

    public void setInspectorEncargado_seleccionado(Integer inspectorEncargado_seleccionado) {
        this.inspectorEncargado_seleccionado = inspectorEncargado_seleccionado;
    }

    public List<SelectElemPojo> getListaInspectores() {
        return listaInspectores;
    }

    public void setListaInspectores(List<SelectElemPojo> listaInspectores) {
        this.listaInspectores = listaInspectores;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
    
    public List<ContenedorPojo> getListaContenedores() {
        return this.mantPtoLimpio.getContenedores_creando();
    }
    
}
