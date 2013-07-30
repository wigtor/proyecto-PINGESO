/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.AdministradorDAO;
import entities.Administrador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class AdministradorDAO_impl extends genericDAO_impl<Administrador> implements AdministradorDAO{
    
    public AdministradorDAO_impl(EntityManager em) {
        super(Administrador.class);
        this.em = em;
    }
    
    @Override
    public Administrador findByRut(int rut) {
        Query q = this.em.createNamedQuery("Administrador.findByRut");
        q.setParameter("rut", rut);
        List<Administrador> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }
    
    //Poner otras funciones extra que sólo haga este DAO
    @Override
    public boolean deleteByRut(int rut) {
        //Obtengo el inspector
        Query q = this.em.createNamedQuery("Administrador.findByRut");
        q.setParameter("rut", rut);
        List<Administrador> res = q.getResultList();
        if (res.isEmpty()) {
            return false;
        }
        Administrador adminToDelete = res.get(0);
        
        //Borro el inspector
        getEntityManager().remove(adminToDelete);
        return true;
    }
}
