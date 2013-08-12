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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class CrudUsuariosComun implements CrudUsuariosComunLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Usuario getUsuarioByRut(Integer rutUser) {
        if (rutUser == null) {
            return null;
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO usuarioDAO = factoryDeDAOs.getUsuarioDAO();
        return usuarioDAO.findByRut(rutUser.intValue());
    }
    
    @Override
    public Usuario getUsuarioByUsername(String userName) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO usuarioDAO = factoryDeDAOs.getUsuarioDAO();
        return usuarioDAO.find(userName);
        
    }
    
    @Override
    public String convertToMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes("UTF-8"));

            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            password = bigInt.toString(16);
        } catch (Exception e) {
            System.out.println("No se pudo convertir a MD5 la password");
        }
        return password;
    }
}
