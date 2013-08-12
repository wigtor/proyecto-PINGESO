/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.TipoIncidencia;
import java.util.Collection;

/**
 *
 * @author victor
 */
public interface TipoIncidenciaDAO extends genericDAO<TipoIncidencia>{
    
    /**
     * Busca un tipo de incidencia a través de su nombre
     * @param tipoName El nombre del tipo de incidencia que se está buscando
     * @return Un objeto "TipoIncidencia" si pudo encontrarse, null no se encuentra
     */
    public TipoIncidencia find(String tipoName);
    
    /**
     * Encuentra todos los tipos de incidencia que son para ser usados por los usuarios, 
     * se descartan los tipos de incidencia que son generadas por el sistema scheduler de COPLIME
     * @return Una colección de objetos "TipoIncidencia", puede ser vacia, pero nunca null
     */
    public Collection<TipoIncidencia> findAllVisibles();
}
