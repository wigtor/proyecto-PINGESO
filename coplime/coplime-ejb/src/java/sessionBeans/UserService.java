/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.UsuarioDAO;
import entities.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class UserService implements UserServiceLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Usuario buscarUsuario(String username) {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        return userDAO.find(username);
    }
    

    @Override
    public void cambiarDatosContacto(String username, int telefono, String email) throws Exception {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        Usuario user = userDAO.find(username);
        if (user != null) {
            //System.out.println("modificando datos de contacto");
            user.setEmail(email);
            user.setTelefono(telefono);
            userDAO.update(user);
        }
        else {
            throw new Exception("No existe el usuario con ese username");
        }
    }
    
    @Override
    public void cambiarPass(String username, String passActual, String nvaPass) throws Exception {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        Usuario user = userDAO.find(username);
        if (user != null) {
            String passwordMd5_real = user.getPassword();
            String passwordMd5_puesta = conviertePassToMd5(passActual);
            if (passwordMd5_puesta.equals(passwordMd5_real)) {
                user.setPassword(conviertePassToMd5(nvaPass));
                userDAO.update(user);
            }
            else {
                throw new Exception("La contraseña actual no es correcta");
            }
        }
        else {
            throw new Exception("No existe el usuario con ese username");
        }
    }

    private String conviertePassToMd5(String password) {
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
        return password;
    }
}
