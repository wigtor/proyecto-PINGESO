/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.NotificacionDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.TipoIncidenciaDAO;
import entities.NotificacionDeUsuario;
import entities.PuntoLimpio;
import entities.TipoIncidencia;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class AvisosIncidencia implements AvisosIncidenciaLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    @Override
    public Collection<TipoIncidencia> getTiposAvisos() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        TipoIncidenciaDAO tiDAO = factoryDeDAOs.getTipoIncidenciaDAO();
        
        Collection<TipoIncidencia> resultado;
        resultado = tiDAO.findAllVisibles();
        if (resultado == null) {
            resultado = new LinkedList();
        }
        return resultado;
    }
    
    @Override
    public void guardarAvisoIncidencia(int numPuntoLimpio, String emailContacto, String detalles, int idTipoIncidenciaSeleccionada, byte[] datosImagen, String tipoImagen) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoLimpioDAO = factoryDeDAOs.getPuntoLimpioDAO();
        TipoIncidenciaDAO tpo_incidDAO = factoryDeDAOs.getTipoIncidenciaDAO();
        
        //Busco el punto limpio sgún el N°
        PuntoLimpio ptoLimpioRelacionado = ptoLimpioDAO.find(numPuntoLimpio);
        
        //Busco el tipo de incidencia;
        TipoIncidencia tipoIncidencia = tpo_incidDAO.find(idTipoIncidenciaSeleccionada);
        
        //Creo la notificación de incidencia
        NotificacionDeUsuario notif = new NotificacionDeUsuario(detalles, ptoLimpioRelacionado, tipoIncidencia);
        notif.setEmailContacto(emailContacto);
        if (datosImagen != null) {
            notif.setTipoImagen(tipoImagen);
            //Falta almacenar la imagen y la extensión según tipo de imagen
            System.out.println("Han llegado datos de una imagen de tipo: "+tipoImagen + " cantidad de bytes: "+datosImagen.length);
            String url_imagen_adjunta = "img_notif_"+ Calendar.getInstance().getTimeInMillis()+".png"; //FALTA CREAR UNA CARPETA TEMPORAL
            notif.setImagenAdjunta(url_imagen_adjunta);
        }
        //La persisto
        NotificacionDAO notifDAO = factoryDeDAOs.getNotificacionDAO();
        notifDAO.insert(notif);
        
        
        
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
