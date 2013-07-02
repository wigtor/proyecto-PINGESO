/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.AdministradorDAO;
import entities.Administrador;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public class AdministradorDAO_impl extends genericDAO_impl<Administrador> implements AdministradorDAO{
    
    public AdministradorDAO_impl(EntityManager em) {
        super(Administrador.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    
}
