/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Material;

/**
 *
 * @author victor
 */
public interface MaterialDAO extends genericDAO<Material>{
    
    /**
     * Busca un material a través de su nombre en lugar de su id
     * @param tipoName El nombre del material que se está buscando
     * @return Un objeto "Material" si pudo encontrarse, null si no se encuentra
     */
    public Material find(String tipoName);
}
