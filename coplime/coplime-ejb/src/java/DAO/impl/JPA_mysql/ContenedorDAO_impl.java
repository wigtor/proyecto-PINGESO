/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.ContenedorDAO;
import entities.Contenedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class ContenedorDAO_impl extends genericDAO_impl<Contenedor> implements ContenedorDAO{
    
    public ContenedorDAO_impl(EntityManager em) {
        super(Contenedor.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    
}
