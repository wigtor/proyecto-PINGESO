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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
    public Collection<PuntoLimpio> getPuntosLimpios() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO tiDAO = factoryDeDAOs.getPuntoLimpioDAO();
        
        Collection<PuntoLimpio> resultado;
        resultado = tiDAO.findAll();
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
            String url_completa = guardarArchivo(tipoImagen, datosImagen);
            notif.setImagenAdjunta(url_completa);
        }
        //La persisto
        NotificacionDAO notifDAO = factoryDeDAOs.getNotificacionDAO();
        notifDAO.insert(notif);
    }
    
    /**
     * Escribe en el disco duro los datos de la imagen pasada como parámetro,
     * utiliza un directorio común a todas las subidas de archivos.
     * @param tipoImagen Contiene el tipo de imagen que será almacenada. Ej: image/jpeg
     * @param datosImagen Contiene los datos de la imagen a guardar
     * @return 
     */
    private String guardarArchivo(String tipoImagen, byte[] datosImagen) {
        //Falta almacenar la imagen y la extensión según tipo de imagen
            System.out.println("Han llegado datos de una imagen de tipo: "+tipoImagen + " cantidad de bytes: "+datosImagen.length);
            String url_imagen_adjunta = "img_notif_"+ Calendar.getInstance().getTimeInMillis(); //FALTA CREAR UNA CARPETA TEMPORAL
            String url_completa = "";
            String extensionImagen = "";
                String[] arrayTemp = tipoImagen.split("/");
                if (arrayTemp.length == 2) {
                    extensionImagen = "."+tipoImagen.split("/")[1];
                }
            int contador;
            String path = System.getenv("JAVA_HOME");
            path = path + File.separator+"uploads_coplime"+File.separator;
            File carpetaUploads = new File(path);
            if (carpetaUploads.mkdir()) {
                System.out.println("Creado el directorio para almacenar los uploads: "+path);
            }
            System.out.println("Almacenando archivo en el siguiente path: " + path);
            try {
                url_completa = path + url_imagen_adjunta + extensionImagen;
                File nf= new File(url_completa);
                contador = 1;
                while(!nf.createNewFile()) {
                    url_completa = path + url_imagen_adjunta + "-" + contador + extensionImagen;
                    nf= new File(url_completa);
                    contador++;
                }
                FileOutputStream escritor = new FileOutputStream(nf);
                escritor.write(datosImagen);
                escritor.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return url_completa;
    }

    @Override
    public String getNombrePtoLimpio(int numPtoLimpio) {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO pDAO = factoryDeDAOs.getPuntoLimpioDAO();
        PuntoLimpio pLimpio = pDAO.find(numPtoLimpio);
        if (pLimpio == null) {
            return null;
        }
        return pLimpio.getNombre();
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
