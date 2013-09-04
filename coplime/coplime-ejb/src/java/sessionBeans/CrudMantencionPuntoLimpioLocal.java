/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.MantencionPuntoLimpio;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudMantencionPuntoLimpioLocal {

    /**
     * Agrega una nueva mantención de punto limpio al sistema.
     * @param numPtoLimpio El N° del punto limpio al que se le realizará la mantención
     * @param usernameLogueado El nombre de usuario de quien está realizando la mantención
     * @param detalle Una descripción acerca de qué trató la mantención realizada
     * @param nvoEstado EL nuevo estado del punto limpio luego de la mantención
     * @return 
     */
    public boolean agregarMantencion(Integer numPtoLimpio, String usernameLogueado, String detalle, Integer nvoEstado) throws Exception;
    
    /**
     * Busca en la fuente de datos una mantención de punto limpio con cierto N°.
     * @param numMantencion El N° con que se identifica una mantención de punto limpio
     * @return Un objeto "MantencionPuntoLimpio", null si no se encontró
     */
    public MantencionPuntoLimpio getMantencionById(Integer numMantencion);
    
    /**
     * Obtiene una lista con todas las mantenciones de puntos limpios registradas 
     * en el sistema que sólo pueda ver el usuario especificado como parámetro.
     * @param usernameQuienPregunta El nombre de usuario de quien está realizando la consulta
     * @return Una colección de objetos "MantencionPuntoLimpio", puede ser vacía, pero nunca null
     */
    public Collection<MantencionPuntoLimpio> getAllMantenciones(String usernameQuienPregunta);

}
