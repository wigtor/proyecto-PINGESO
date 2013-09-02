/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.puntoLimpio;

import ObjectsForManagedBeans.PuntoLimpioPojo;
import entities.PuntoLimpio;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@ManagedBean
@Named(value = "mantenedorPuntoLimpioVerListadoMB")
@RequestScoped
public class MantenedorPuntoLimpioVerListadoMB {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private MantenedorPuntoLimpioConversation mantPtoLimpio;
    
    private List<PuntoLimpioPojo> lista;
    
    private List<PuntoLimpioPojo> listaBusqueda;
    
    private PuntoLimpioPojo elementoSelecionado;
    
    
    @PostConstruct
    public void init() {
        cargarPuntosLimpios();
    }
    
    private void cargarPuntosLimpios(){
        Collection<PuntoLimpio> listaTemp = crudPuntoLimpio.getAllPuntosLimpios();
        PuntoLimpioPojo ptoTemporal;
        Calendar f;
        List<PuntoLimpioPojo> listaResult = new ArrayList<>();
        for(PuntoLimpio pto_iter : listaTemp) {
            ptoTemporal = new PuntoLimpioPojo();
            
            ptoTemporal.setNum(pto_iter.getId());
            ptoTemporal.setEstado(pto_iter.getEstadoGlobal().getNombreEstado());
            ptoTemporal.setNombre(pto_iter.getNombre());
            f = pto_iter.getFechaProxRevision();
            ptoTemporal.setFechaProximaRevStr(Integer.toString(f.get(Calendar.DAY_OF_MONTH))
                    .concat("-")
                    .concat(f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)));
            listaResult.add(ptoTemporal);
        }
        this.lista = listaResult;
    }
    
    public void agregar() {
        this.mantPtoLimpio.beginConversation();
        this.mantPtoLimpio.setState(MantenedorPuntoLimpioConversation.AGREGAR);
        CommonFunctions.goToPage("/faces/users/admin/agregarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
    }
    
    public void verDetalles(Integer numPtoLimpio) {
        PuntoLimpio ptoLimpioSelec = crudPuntoLimpio.getPuntoLimpioByNum(numPtoLimpio);
        
        if (ptoLimpioSelec != null) { //Verifico que exista
            this.mantPtoLimpio.beginConversation();
            this.mantPtoLimpio.setState(MantenedorPuntoLimpioConversation.VER);
            this.mantPtoLimpio.setIdPuntoLimpioDetalles(numPtoLimpio);
            CommonFunctions.goToPage("/faces/users/verDetallesPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        }
    }
    
    public void editar(Integer numPto) {
        PuntoLimpio ptoEdit = crudPuntoLimpio.getPuntoLimpioByNum(numPto);
        if (ptoEdit != null) {
            this.mantPtoLimpio.beginConversation();
            this.mantPtoLimpio.setState(MantenedorPuntoLimpioConversation.EDITAR);
            this.mantPtoLimpio.setPrimeraCarga(true);
            this.mantPtoLimpio.setIdPuntoLimpioDetalles(numPto);
            CommonFunctions.goToPage("/faces/users/admin/editarPuntoLimpio.xhtml?cid=".concat(this.mantPtoLimpio.getConversation().getId()));
        }
        else {
            //MOSTRAR ERROR
            this.mantPtoLimpio.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        }
    }
    
    public void eliminar(Integer numPto) {
        try {
            crudPuntoLimpio.eliminarPuntoLimpioByNum(numPto);
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha eliminado el punto limpio",
                    "Se ha eliminado correctamente el punto limpio ");
        }
        catch (Exception e) {
            CommonFunctions.viewMessage(FacesMessage.SEVERITY_ERROR,
                    e.getMessage(),
                    e.getMessage());
        }
        this.mantPtoLimpio.limpiarDatos();
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml?faces-redirect=true");
    }
    
    public MantenedorPuntoLimpioVerListadoMB() {
    }
    
    
    public List<PuntoLimpioPojo> getLista() {
        return lista;
    }

    public List<PuntoLimpioPojo> getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List<PuntoLimpioPojo> listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public PuntoLimpioPojo getElementoSelecionado() {
        return elementoSelecionado;
    }

    public void setElementoSelecionado(PuntoLimpioPojo elementoSelecionado) {
        this.elementoSelecionado = elementoSelecionado;
    }
    
}
