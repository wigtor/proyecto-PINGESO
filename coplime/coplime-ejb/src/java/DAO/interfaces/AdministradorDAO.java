/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Administrador;

/**
 *
 * @author victor
 */
public interface AdministradorDAO extends genericDAO<Administrador>{
    // Interface that all AdministradorDAOs must support

    public boolean deleteByRut(int rut);
}
