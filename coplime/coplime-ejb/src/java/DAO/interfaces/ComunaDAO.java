/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Comuna;

/**
 *
 * @author victor
 */
public interface ComunaDAO extends genericDAO<Comuna>{
    // Interface that all AdministradorDAOs must support

    public Comuna find(String nombreComuna);
}
