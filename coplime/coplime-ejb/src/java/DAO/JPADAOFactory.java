/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.impl.JPA_mysql.AdministradorDAO_impl;
import DAO.impl.JPA_mysql.ComunaDAO_impl;
import DAO.impl.JPA_mysql.ContenedorDAO_impl;
import DAO.impl.JPA_mysql.InspectorDAO_impl;
import DAO.impl.JPA_mysql.OperarioDAO_impl;
import DAO.impl.JPA_mysql.RolDAO_impl;
import DAO.impl.JPA_mysql.UsuarioDAO_impl;
import DAO.impl.JPA_mysql.TipoIncidenciaDAO_impl;
import DAO.impl.JPA_mysql.EstadoDAO_impl;
import DAO.impl.JPA_mysql.MaterialDAO_impl;
import DAO.impl.JPA_mysql.NotificacionDAO_impl;
import DAO.impl.JPA_mysql.PuntoLimpioDAO_impl;
import DAO.impl.JPA_mysql.UnidadMedidaDAO_impl;
import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.ComunaDAO;
import DAO.interfaces.ContenedorDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.UsuarioDAO;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.MaterialDAO;
import DAO.interfaces.NotificacionDAO;
import DAO.interfaces.TipoIncidenciaDAO;
import DAO.interfaces.UnidadMedidaDAO;
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
        return new PuntoLimpioDAO_impl(em);
    }

    @Override
    public ContenedorDAO getContenedorDAO() {
        return new ContenedorDAO_impl(em);
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAO_impl(em);
    }
    
    @Override
    public RolDAO getRolDAO() {
        return new RolDAO_impl(em);
    }

    @Override
    public InspectorDAO getInspectorDAO() {
        return new InspectorDAO_impl(em);
    }
    
    @Override
    public OperarioDAO getOperarioDAO() {
        return new OperarioDAO_impl(em);
    }

    @Override
    public EstadoDAO getEstadoDAO() {
        return new EstadoDAO_impl(em);
    }

    @Override
    public TipoIncidenciaDAO getTipoIncidenciaDAO() {
        return new TipoIncidenciaDAO_impl(em);
    }

    @Override
    public MaterialDAO getMaterialDAO() {
        return new MaterialDAO_impl(em);
    }
    
    @Override
    public NotificacionDAO getNotificacionDAO() {
        return new NotificacionDAO_impl(em);
    }
    
    @Override
    public ComunaDAO getComunaDAO() {
        return new ComunaDAO_impl(em);
    }
    
    @Override
    public UnidadMedidaDAO getUnidadMedidaDAO() {
        return new UnidadMedidaDAO_impl(em);
    }
}
