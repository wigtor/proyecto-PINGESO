/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.HistoricoContenedorDAO;
import entities.HistoricoContenedor;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public class HistoricoContenedorDAO_impl extends genericDAO_impl<HistoricoContenedor> implements HistoricoContenedorDAO{
    
    public HistoricoContenedorDAO_impl(EntityManager em) {
        super(HistoricoContenedor.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    
}
