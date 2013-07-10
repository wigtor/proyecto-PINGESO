/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.OperarioDAO;
import entities.OperarioMantencion;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public class OperarioDAO_impl extends genericDAO_impl<OperarioMantencion> implements OperarioDAO {
    public OperarioDAO_impl(EntityManager em) {
        super(OperarioMantencion.class);
        this.em = em;
    }
}
