/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.UnidadMedida;

/**
 *
 * @author victor
 */
public interface UnidadMedidaDAO extends genericDAO<UnidadMedida>{
    
    /**
     * Busca una unidad de medida a través de su nombre.
     * @param rolName El nombre de la unidad de medida que se está buscando
     * @return Un objeto "UnidadMedida" si pudo encontrarse, null si no se encuentra
     */
    public UnidadMedida find(String nombreUnidad);
}
