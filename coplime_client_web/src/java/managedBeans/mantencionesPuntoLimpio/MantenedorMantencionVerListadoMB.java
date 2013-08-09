/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantencionesPuntoLimpio;

import ObjectsForManagedBeans.RevisionPojo;
import entities.MantencionPuntoLimpio;
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
import sessionBeans.CrudMantencionPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "mantenedorMantencionVerListadoMB")
@RequestScoped
public class MantenedorMantencionVerListadoMB {
    @EJB
    CrudMantencionPuntoLimpioLocal crudMantencion;
    
    @Inject
    private MantenedorMantencionConversation mantMantenciones;
    
    private List<RevisionPojo> lista;
    
    private List<RevisionPojo> listaBusqueda;
    
    private RevisionPojo elementoSelecionado;
    
    
    @PostConstruct
    public void init() {
        cargarMantenciones();
    }
    
    private void cargarMantenciones(){
        Collection<MantencionPuntoLimpio> listaTemp = crudMantencion.getAllMantenciones(CommonFunctions.getUsuarioLogueado());
        if (listaTemp == null)
            return;
        RevisionPojo revTemporal;
        Calendar f;
        String str_temp, rut, nombre, apellido1;
        List<RevisionPojo> listaResult = new ArrayList();
        for(MantencionPuntoLimpio rev_iter : listaTemp) {
            revTemporal = new RevisionPojo();
            
            revTemporal.setNum(rev_iter.getId());
            f = rev_iter.getFecha();
            revTemporal.setFecha(f.get(Calendar.DAY_OF_MONTH)
                    +"-"
                    +f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH));
            
            str_temp = rev_iter.getComentarios();
            if (str_temp.length() > 21) {
                str_temp = str_temp.substring(0, 25)+"...";
            }
            revTemporal.setDetalleCortado(str_temp);
            
            rut = Integer.toString(rev_iter.getOperarioMantencion().getUsuario().getRut());
            nombre = rev_iter.getOperarioMantencion().getUsuario().getNombre();
            apellido1 = rev_iter.getOperarioMantencion().getUsuario().getApellido1();
            revTemporal.setUsuario(rut.concat(" - ").concat(nombre).concat(apellido1));
            listaResult.add(revTemporal);
        }
        this.lista = listaResult;
    }
    
    public void verDetalles(Integer numRevision) {
        System.out.println("NÚMERO DE REVISION: "+numRevision);
        MantencionPuntoLimpio revisionSelec = crudMantencion.getMantencionById(numRevision);
        
        if (revisionSelec != null) { //Verifico que exista
            this.mantMantenciones.setIdMantencionDetalles(numRevision);
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        }
        CommonFunctions.goToPage("/faces/users/verDetallesMantencion.xhtml");
       
    }
    
    public void volver() {
        mantMantenciones.limpiarDatos();
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }
    
    
    /**
     * Creates a new instance of MantenedorMantencionVerListadoMB
     */
    public MantenedorMantencionVerListadoMB() {
    }

    public List<RevisionPojo> getLista() {
        return lista;
    }

    public void setLista(List<RevisionPojo> lista) {
        this.lista = lista;
    }

    public List<RevisionPojo> getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List<RevisionPojo> listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public RevisionPojo getElementoSelecionado() {
        return elementoSelecionado;
    }

    public void setElementoSelecionado(RevisionPojo elementoSelecionado) {
        this.elementoSelecionado = elementoSelecionado;
    }
    
}
