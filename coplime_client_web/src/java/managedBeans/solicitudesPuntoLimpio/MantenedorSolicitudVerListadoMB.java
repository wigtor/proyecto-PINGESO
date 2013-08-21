/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.solicitudesPuntoLimpio;

import ObjectsForManagedBeans.SolicitudPojo;
import entities.SolicitudMantencion;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudSolicitudMantencionLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorSolicitudVerListadoMB")
@RequestScoped
public class MantenedorSolicitudVerListadoMB {
    @EJB
    CrudSolicitudMantencionLocal crudSolicitud;
    
    @Inject
    private MantenedorSolicitudConversation mantSolicitudes;
    
    private List<SolicitudPojo> lista;
    
    private List<SolicitudPojo> listaBusqueda;
    
    private SolicitudPojo elementoSelecionado;
    
    
    @PostConstruct
    public void init() {
        cargarSolicitudes();
    }
    
    private void cargarSolicitudes(){
        Collection<SolicitudMantencion> listaTemp = crudSolicitud.getAllSolicitudes(CommonFunctions.getUsuarioLogueado());
        if (listaTemp == null)
            return;
        SolicitudPojo revTemporal;
        Calendar f;
        String str_temp, rut, nombre, apellido1;
        List<SolicitudPojo> listaResult = new ArrayList<>();
        for(SolicitudMantencion rev_iter : listaTemp) {
            revTemporal = new SolicitudPojo();
            
            revTemporal.setNum(rev_iter.getId());
            
            f = rev_iter.getFecha();
            revTemporal.setFecha(Integer.toString(f.get(Calendar.DAY_OF_MONTH))
                    .concat("-")
                    .concat(f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)));
            str_temp = rev_iter.getDetalles();
            if (str_temp.length() > 21) {
                str_temp = str_temp.substring(0, 25).concat("...");
            }
            revTemporal.setDetalleCortado(str_temp);
            
            
            rut = Integer.toString(rev_iter.getInspectorSolicitante().getUsuario().getRut());
            nombre = rev_iter.getInspectorSolicitante().getUsuario().getNombre();
            apellido1 = rev_iter.getInspectorSolicitante().getUsuario().getApellido1();
            revTemporal.setUsuarioEmisor(rut.concat(" - ").concat(nombre).concat(" ").concat(apellido1));
            
            rut = Integer.toString(rev_iter.getOperarioAsignado().getUsuario().getRut());
            nombre = rev_iter.getOperarioAsignado().getUsuario().getNombre();
            apellido1 = rev_iter.getOperarioAsignado().getUsuario().getApellido1();
            revTemporal.setUsuarioReceptor(rut.concat(" - ").concat(nombre).concat(" ").concat(apellido1));
            listaResult.add(revTemporal);
        }
        this.lista = listaResult;
    }
    
    public void verDetalles(Integer numSolicitud) {
        SolicitudMantencion solicitudSelect = crudSolicitud.getSolicitudById(numSolicitud);
        
        if (solicitudSelect != null) { //Verifico que exista
            this.mantSolicitudes.beginConversation();
            this.mantSolicitudes.setIdSolicitudDetalles(numSolicitud);
            CommonFunctions.goToPage("/faces/users/verDetallesSolicitudMantencion.xhtml?cid=".concat(this.mantSolicitudes.getConversation().getId()));
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        }
        
    }
    
    public void volver() {
        mantSolicitudes.limpiarDatos();
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }
    
    
    public MantenedorSolicitudVerListadoMB() {
    }

    public List<SolicitudPojo> getLista() {
        return lista;
    }

    public void setLista(List<SolicitudPojo> lista) {
        this.lista = lista;
    }

    public List<SolicitudPojo> getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List<SolicitudPojo> listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public SolicitudPojo getElementoSelecionado() {
        return elementoSelecionado;
    }

    public void setElementoSelecionado(SolicitudPojo elementoSelecionado) {
        this.elementoSelecionado = elementoSelecionado;
    }
    
}
