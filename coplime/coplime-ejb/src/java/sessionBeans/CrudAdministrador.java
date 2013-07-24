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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class CrudAdministrador implements CrudAdministradorLocal {
    //Se inyecta el entity manager, no se crea en los DAO, pero si se le pasa a los DAO
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    @TransactionAttribute (TransactionAttributeType.REQUIRED)
    @Override
    public void agregarAdministrador(String username, String password, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono){
        
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

        System.out.println("Password Md5: "+password);
       
        Administrador nvoAdmin;
        Usuario nvoUsuario = new Usuario();
        nvoAdmin = new Administrador();
        nvoUsuario.setNombre(nombre);
        nvoUsuario.setApellido1(apellido1);
        nvoUsuario.setEmail(mail);
        nvoUsuario.setApellido2(apellido2);
         nvoUsuario.setRut(rut);
        nvoUsuario.setUsername(username);
        nvoUsuario.setPassword(password);
        nvoUsuario.setTelefono(telefono);
        nvoAdmin.setUsuario(nvoUsuario);
        
        
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        AdministradorDAO adminDAO = factoryDeDAOs.getAdministradorDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        RolDAO rolDAO = factoryDeDAOs.getRolDAO();
        Rol nvoRol = rolDAO.find("Administrador");
        if (nvoRol == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol = new Rol();
            nvoRol.setNombreRol("Administrador");
            rolDAO.insert(nvoRol);
        }
        nvoUsuario.setRol(nvoRol);
        
        userDAO.insert(nvoUsuario);
        adminDAO.insert(nvoAdmin);
        System.out.println("Insertado");
        //factoryDeDAOs.close();
    }
    
    @Override
    public boolean eliminarAdministrador(Integer rutUser){
        if (rutUser != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            AdministradorDAO adminDAO = factoryDeDAOs.getAdministradorDAO();
            try {
                return adminDAO.deleteByRut(rutUser);
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }

}
