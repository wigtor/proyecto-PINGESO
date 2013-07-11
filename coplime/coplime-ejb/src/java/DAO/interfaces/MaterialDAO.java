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
    // Interface that all AdministradorDAOs must support

    public Material find(String tipoName);
}
