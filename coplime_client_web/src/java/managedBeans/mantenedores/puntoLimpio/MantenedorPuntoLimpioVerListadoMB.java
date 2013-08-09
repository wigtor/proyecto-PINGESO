/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantenedores.puntoLimpio;

import ObjectsForManagedBeans.PuntoLimpioPojo;
import entities.PuntoLimpio;
import java.io.IOException;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import otros.CommonFunctions;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@ManagedBean
@Named(value = "MantenedorPuntoLimpioVerListado")
@RequestScoped
public class MantenedorPuntoLimpioVerListado {
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
    @Inject
    private MantenedorPuntoLimpio mantPtoLimpio;
    
    private List<PuntoLimpioPojo> lista;
    
    private List<PuntoLimpioPojo> listaBusqueda;
    
    private PuntoLimpioPojo elementoSelecionado;
    
    
    @PostConstruct
    public void init() {
        this.lista = cargarPuntosLimpios();
    }
    
    private List<PuntoLimpioPojo> cargarPuntosLimpios(){
        Collection<PuntoLimpio> listaTemp = crudPuntoLimpio.getAllPuntosLimpios();
        PuntoLimpioPojo ptoTemporal;
        Calendar f;
        List<PuntoLimpioPojo> listaResult = new ArrayList();
        for(PuntoLimpio pto_iter : listaTemp) {
            ptoTemporal = new PuntoLimpioPojo();
            
            ptoTemporal.setNum(pto_iter.getId());
            ptoTemporal.setEstado(pto_iter.getEstadoGlobal().getNombreEstado());
            ptoTemporal.setNombre(pto_iter.getNombre());
            f = pto_iter.getFechaProxRevision();
            ptoTemporal.setFechaProximaRevStr(f.get(Calendar.DAY_OF_MONTH)
                    +"-"
                    +f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH));
            listaResult.add(ptoTemporal);
        }
        return listaResult;
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
    
    
    /**
     * Creates a new instance of MantenedorPuntoLimpioVerListado
     */
    public MantenedorPuntoLimpioVerListado() {
    }
    
    public void agregar() {
       ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
       try {
           externalContext.redirect(externalContext.getRequestContextPath() + "/faces/admin/agregarPuntoLimpio.xhtml");
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }
    
    public void verDetalles(Integer numPtoLimpio) {
        System.out.println("NÚMERO DE INSPECTOR: "+numPtoLimpio);
        PuntoLimpio ptoLimpioSelec = crudPuntoLimpio.getPuntoLimpioByNum(numPtoLimpio);
        
        if (ptoLimpioSelec != null) { //Verifico que exista
            this.mantPtoLimpio.setIdPuntoLimpioDetalles(numPtoLimpio);
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        }
       CommonFunctions.goToPage("/faces/users/verDetallesPuntoLimpio.xhtml");
       
    }
    
    public void editar(int numPto) {
        System.out.println("NÚMERO DE PUNTO LIMPIO: "+numPto);
        PuntoLimpio ptoEdit = crudPuntoLimpio.getPuntoLimpioByNum(numPto);
        if (ptoEdit != null) {
            this.mantPtoLimpio.setIdPuntoLimpioDetalles(numPto);
            CommonFunctions.goToPage("/faces/admin/editarPuntoLimpio.xhtml");
        }
        else {
            //MOSTRAR ERROR
            this.mantPtoLimpio.limpiarDatos();
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        }
       
    }
    
    public void eliminar(int numPto) {
       System.out.println("NÚMERO DE PUNTO LIMPIO: "+numPto);
       crudPuntoLimpio.eliminarPuntoLimpio(numPto);
       this.mantPtoLimpio.limpiarDatos();
       CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
       
       //MOSTRAR MENSAJE DE ACCION REALIZADA SATISFACTORIAMENTE
    }
}
