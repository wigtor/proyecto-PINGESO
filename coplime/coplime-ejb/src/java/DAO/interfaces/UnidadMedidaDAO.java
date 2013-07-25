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
    // Interface that all AdministradorDAOs must support

    public UnidadMedida find(String nombreUnidad);
}
