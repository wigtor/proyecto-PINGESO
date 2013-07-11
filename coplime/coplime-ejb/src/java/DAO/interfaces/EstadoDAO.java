/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Estado;

/**
 *
 * @author victor
 */
public interface EstadoDAO extends genericDAO<Estado>{
    // Interface that all AdministradorDAOs must support

    public Estado find(String estadoName);
}
