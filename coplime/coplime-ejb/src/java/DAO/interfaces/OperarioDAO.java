/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.OperarioMantencion;

/**
 *
 * @author victor
 */
public interface OperarioDAO extends genericDAO<OperarioMantencion>{
    public boolean deleteByRut(int rut);
    public OperarioMantencion findByUsername(String username);
}
