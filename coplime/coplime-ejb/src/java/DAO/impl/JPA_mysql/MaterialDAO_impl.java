/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.MaterialDAO;
import entities.Material;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class MaterialDAO_impl extends genericDAO_impl<Material> implements MaterialDAO{
    
    public MaterialDAO_impl(EntityManager em) {
        super(Material.class);
        this.em = em;
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    @Override
    public Material find(String nombre) {
        Query q = this.em.createNamedQuery("Material.findByName");
        q.setParameter("nombre", nombre);
        Material res;
        try {
            res = (Material)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}
