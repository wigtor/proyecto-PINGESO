/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.impl.JPA_mysql.AdministradorDAO_impl;
import DAO.impl.JPA_mysql.RolDAO_impl;
import DAO.impl.JPA_mysql.UsuarioDAO_impl;
import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.ContenedorDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.UsuarioDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author victor
 */
public class JPADAOFactory extends DAOFactory{
    private EntityManager em;

    public JPADAOFactory(EntityManager em) {
        /*
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("coplime-ejbPU");
        this.em = emf.createEntityManager();
        System.out.println("Abierta:" + this.em.isOpen());
        */
        this.em = em;
    }
    
    @Override
    public void close() {
        //this.em.flush();
        this.em.close();
    }
    
    @Override
    public AdministradorDAO getAdministradorDAO() {
        return new AdministradorDAO_impl(em);
    }

    @Override
    public PuntoLimpioDAO getPuntoLimpioDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContenedorDAO getContenedorDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAO_impl(em);
    }
    
    @Override
    public RolDAO getRolDAO() {
        return new RolDAO_impl(em);
    }
    
}
