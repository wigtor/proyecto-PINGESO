/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.mantencionesPuntoLimpio;

import managedBeans.revisionesPuntoLimpio.*;
import ObjectsForManagedBeans.RevisionPojo;
import entities.RevisionPuntoLimpio;
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
import sessionBeans.CrudRevisionPuntoLimpioLocal;

/**
 *
 * @author victor
 */
@Named(value = "MantenedorMantencionVerListado")
@RequestScoped
public class MantenedorMantencionVerListado {
    @EJB
    CrudRevisionPuntoLimpioLocal crudRevision;
    
    @Inject
    private MantenedorRevision mantRevisiones;
    
    private List<RevisionPojo> lista;
    
    private List<RevisionPojo> listaBusqueda;
    
    private RevisionPojo elementoSelecionado;
    
    
    @PostConstruct
    public void init() {
        cargarRevisiones();
    }
    
    private void cargarRevisiones(){
        Collection<RevisionPuntoLimpio> listaTemp = crudRevision.getAllRevisiones(CommonFunctions.getUsuarioLogueado());
        RevisionPojo revTemporal;
        Calendar f;
        String str_temp, rut, nombre, apellido1;
        List<RevisionPojo> listaResult = new ArrayList();
        for(RevisionPuntoLimpio rev_iter : listaTemp) {
            revTemporal = new RevisionPojo();
            
            revTemporal.setNum(rev_iter.getId());
            f = rev_iter.getFecha();
            revTemporal.setFecha(f.get(Calendar.DAY_OF_MONTH)
                    +"-"
                    +f.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH));
            
            str_temp = rev_iter.getDetalles();
            if (str_temp.length() > 21) {
                str_temp = str_temp.substring(0, 25)+"...";
            }
            revTemporal.setDetalleCortado(str_temp);
            
            rut = Integer.toString(rev_iter.getInspectorRevisor().getUsuario().getRut());
            nombre = rev_iter.getInspectorRevisor().getUsuario().getNombre();
            apellido1 = rev_iter.getInspectorRevisor().getUsuario().getApellido1();
            revTemporal.setInspector(rut.concat(" - ").concat(nombre).concat(apellido1));
            listaResult.add(revTemporal);
        }
        this.lista = listaResult;
    }
    
    public void verDetalles(Integer numRevision) {
        System.out.println("NÚMERO DE REVISION: "+numRevision);
        RevisionPuntoLimpio revisionSelec = crudRevision.getRevisionById(numRevision);
        
        if (revisionSelec != null) { //Verifico que exista
            this.mantRevisiones.setIdRevisionDetalles(numRevision);
        }
        else {
            //MOSTRAR ERROR
            CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
        }
        CommonFunctions.goToPage("/faces/users/verDetallesRevision.xhtml");
       
    }
    
    public void volver() {
        mantRevisiones.limpiarDatos();
        CommonFunctions.goToPage("/faces/users/verPuntosLimpios.xhtml");
    }
    
    
    /**
     * Creates a new instance of MantenedorMantencionVerListado
     */
    public MantenedorMantencionVerListado() {
    }

    public MantenedorRevision getMantRevisiones() {
        return mantRevisiones;
    }

    public void setMantRevisiones(MantenedorRevision mantRevisiones) {
        this.mantRevisiones = mantRevisiones;
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
