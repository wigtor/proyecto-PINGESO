/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.ContenedorDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.UsuarioDAO;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public abstract class DAOFactory {
    
    
    public static final int JPA = 1;
    public static final int MYSQL = 2; //No implementada
    public static final int SYBASE = 3; //No implementada
    
    
    // There will be a method for each DAO that can be 
    // created. The concrete factories will have to 
    // implement these methods.
    public abstract AdministradorDAO getAdministradorDAO();
    public abstract PuntoLimpioDAO getPuntoLimpioDAO();
    public abstract ContenedorDAO getContenedorDAO();
    public abstract UsuarioDAO getUsuarioDAO();
    public abstract RolDAO getRolDAO();
    public abstract InspectorDAO getInspectorDAO();
    public abstract OperarioDAO getOperarioDAO();
    
    public abstract void close();

  public static DAOFactory getDAOFactory(int whichFactory, EntityManager em) {
  
    switch (whichFactory) {
      case JPA: 
          return new JPADAOFactory(em);
      /*
      case ORACLE    : 
          return new OracleDAOFactory();      
      case SYBASE    : 
          return new SybaseDAOFactory();
      */
      default           : 
          return null;
    }
  }
}