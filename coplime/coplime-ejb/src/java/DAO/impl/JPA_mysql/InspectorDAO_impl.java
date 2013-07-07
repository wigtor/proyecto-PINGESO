/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.InspectorDAO;
import entities.Inspector;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public class InspectorDAO_impl extends genericDAO_impl<Inspector> implements InspectorDAO {
    public InspectorDAO_impl(EntityManager em) {
        super(Inspector.class);
        this.em = em;
    }
}
