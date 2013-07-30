/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.RevisionDAO;
import entities.RevisionPuntoLimpio;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public class RevisionDAO_impl extends genericDAO_impl<RevisionPuntoLimpio> implements RevisionDAO{
    
    public RevisionDAO_impl(EntityManager em) {
        super(RevisionPuntoLimpio.class);
        this.em = em;
    }
    
}
