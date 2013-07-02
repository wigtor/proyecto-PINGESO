/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Rol;

/**
 *
 * @author victor
 */
public interface RolDAO extends genericDAO<Rol>{
    // Interface that all AdministradorDAOs must support

    public Rol find(String rolName);
}
