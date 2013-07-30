/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Inspector;

/**
 *
 * @author victor
 */
public interface InspectorDAO extends genericDAO<Inspector>{
    public Inspector findByRut(int rut);
    public boolean deleteByRut(int rut);
    public Inspector findByUsername(String username);
}
