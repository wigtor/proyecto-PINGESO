/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Administrador;
import entities.Inspector;
import entities.OperarioMantencion;
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
        nvoUsuario.setRut(17565743);
        nvoUsuario.setTelefono(333333333);
        nvoUsuario.setPassword(password);
        nvoAdmin.setUsuario(nvoUsuario);
        
        
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        AdministradorDAO adminDAO = factoryDeDAOs.getAdministradorDAO();
        InspectorDAO inspectDAO = factoryDeDAOs.getInspectorDAO();
        OperarioDAO OperDAO = factoryDeDAOs.getOperarioDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        RolDAO rolDAO = factoryDeDAOs.getRolDAO();
        Rol nvoRol = rolDAO.find("Administrador");
        if (nvoRol == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol = new Rol();
            nvoRol.setNombreRol("Administrador");
            rolDAO.insert(nvoRol);
        }
        nvoUsuario.setRol(nvoRol);
        rolDAO.insert(nvoRol);
        
        
        userDAO.insert(nvoUsuario);
        adminDAO.insert(nvoAdmin);
        
        
        Inspector nvoInspect;
        Usuario nvoUsuario2 = new Usuario();
        nvoInspect = new Inspector();
        nvoUsuario2.setNombre("Carlos");
        nvoUsuario2.setApellido1("Barrera");
        nvoUsuario2.setEmail("carlos.barrerap@usach.cl");
        nvoUsuario2.setApellido2("Pulgar");
        nvoUsuario2.setUsername("cabarrera");
        nvoUsuario2.setRut(17705318);
        nvoUsuario2.setPassword(password);
        nvoUsuario2.setTelefono(111111111);
        nvoInspect.setUsuario(nvoUsuario2);
        Rol nvoRol2 = rolDAO.find("Inspector");
        if (nvoRol2 == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol2 = new Rol();
            nvoRol2.setNombreRol("Inspector");
            rolDAO.insert(nvoRol2);
        }
        nvoUsuario2.setRol(nvoRol2);
        
        rolDAO.insert(nvoRol2);
        userDAO.insert(nvoUsuario2);
        inspectDAO.insert(nvoInspect);
        
        OperarioMantencion nvoOperario;
        Usuario nvoUsuario3 = new Usuario();
        nvoOperario = new OperarioMantencion();
        nvoUsuario3.setNombre("Armando");
        nvoUsuario3.setApellido1("Casas");
        nvoUsuario3.setEmail("armando.casas@usach.cl");
        nvoUsuario3.setApellido2("Rojas");
        nvoUsuario3.setUsername("arojas");
        nvoUsuario3.setRut(17705318);
        nvoUsuario3.setPassword(password);
        nvoUsuario3.setTelefono(222222222);
        nvoOperario.setUsuario(nvoUsuario3);
        Rol nvoRol3 = rolDAO.find("Operario");
        if (nvoRol3 == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol3 = new Rol();
            nvoRol3.setNombreRol("Operario");
            rolDAO.insert(nvoRol3);
        }
        nvoUsuario3.setRol(nvoRol3);
        
        rolDAO.insert(nvoRol3);
        userDAO.insert(nvoUsuario3);
        OperDAO.insert(nvoOperario);
        
        
    }
}
