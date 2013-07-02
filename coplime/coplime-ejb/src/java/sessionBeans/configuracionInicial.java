/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Administrador;
import entities.Rol;
import entities.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class configuracionInicial implements configuracionInicialLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void primeraEjecicion() {
        String password = "12345";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes("UTF-8"));

            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            password = bigInt.toString(16);
        }
        catch (Exception e) {
            System.out.println("No se pudo convertir a MD5 la password");
        }
        
        Administrador nvoAdmin;
        Usuario nvoUsuario = new Usuario();
        nvoAdmin = new Administrador();
        nvoUsuario.setNombre("Víctor");
        nvoUsuario.setApellido1("Flores");
        nvoUsuario.setEmail("victor.floress@usach.cl");
        nvoUsuario.setApellido2("Sánchez");
        nvoUsuario.setUsername("vflores");
        nvoUsuario.setPassword(password);
        nvoAdmin.setUsuario(nvoUsuario);
        
        
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        AdministradorDAO adminDAO = factoryDeDAOs.getAdministradorDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        RolDAO rolDAO = factoryDeDAOs.getRolDAO();
        Rol nvoRol, nvoRol2, nvoRol3;
        nvoRol = new Rol();
        nvoRol.setId("Administrador");
        nvoRol.setUsuario(nvoUsuario);
        nvoUsuario.setRol(nvoRol);
        rolDAO.insert(nvoRol);
        
        /*
        nvoRol2 = new Rol();
        nvoRol2.setId("Inspector");
        rolDAO.insert(nvoRol2);
        
        nvoRol3 = new Rol();
        nvoRol3.setId("Operario");
        rolDAO.insert(nvoRol3);
        */
        
        userDAO.insert(nvoUsuario);
        adminDAO.insert(nvoAdmin);
        
        
        
        
        
    }
}
