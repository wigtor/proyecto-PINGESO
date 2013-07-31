/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.MantencionDAO;
import DAO.interfaces.RevisionDAO;
import entities.MantencionPuntoLimpio;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public class MantencionDAO_impl extends genericDAO_impl<MantencionPuntoLimpio> implements MantencionDAO{
    
    public MantencionDAO_impl(EntityManager em) {
        super(MantencionPuntoLimpio.class);
        this.em = em;
    }
    
}
