/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

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
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@ManagedBean
@Named(value = "MantenedorPuntoLimpioVerListado")
@RequestScoped
public class MantenedorPuntoLimpioVerListado extends commonFunctions{
    @EJB
    private CrudPuntoLimpioLocal crudPuntoLimpio;
    
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
            
            ptoTemporal.setId(pto_iter.getId());
            ptoTemporal.setNum(pto_iter.getNum());
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
    
    public void editar(int numPto) {
        System.out.println("NÚMERO DE PUNTO LIMPIO: "+numPto);
        PuntoLimpio ptoEdit = crudPuntoLimpio.getPuntoLimpioByNum(numPto);
        if (ptoEdit != null) {
            /*
            this.mantInsp.setRut(new Integer(usuarioEdit.getRut()));
            this.mantInsp.setNombre(usuarioEdit.getNombre());
            this.mantInsp.setApellido1(usuarioEdit.getApellido1());
            this.mantInsp.setApellido2(usuarioEdit.getApellido2());
            this.mantInsp.setMail(usuarioEdit.getEmail());
            this.mantInsp.setUsername(usuarioEdit.getUsername());
            this.mantInsp.setTelefono(usuarioEdit.getTelefono());
            System.out.println(this.mantInsp.getApellido1());
            */
        }
        else {
            //MOSTRAR ERROR
        }
       goToPage("/faces/admin/editarPuntoLimpio.xhtml");
       
    }
    
    public void eliminar(int numPto) {
       System.out.println("NÚMERO DE PUNTO LIMPIO: "+numPto);
       //crudInspector.eliminarInspector(new Integer(numInspector));
       crudPuntoLimpio.eliminarPuntoLimpio(numPto);
       //init();
       goToPage("/faces/users/verPuntosLimpios.xhtml");
       
       //MOSTRAR MENSAJE DE ACCION REALIZADA SATISFACTORIAMENTE
    }
}
