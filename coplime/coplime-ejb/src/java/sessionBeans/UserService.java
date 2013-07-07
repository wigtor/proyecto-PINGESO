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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateful
public class UserService implements UserServiceLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    private Usuario usuarioLogueado;

    @Override
    public void setUsuarioLogueado(String username) {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        
        this.usuarioLogueado = userDAO.find(username);
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Usuario buscarUsuario(String username, String password) {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
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
        this.usuarioLogueado = userDAO.find(username, password);
        return this.usuarioLogueado;
        
    }
    

    @Override
    public boolean cambiarDatosContacto(int telefono, String email) {
        if (this.usuarioLogueado != null) {
            //System.out.println("modificando datos de contacto");
            //Hago los DAO
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
            this.usuarioLogueado.setEmail(email);
            this.usuarioLogueado.setTelefono(telefono);
            //System.out.println("Telefono antes de merge: "+this.usuarioLogueado.getTelefono());
            this.usuarioLogueado = userDAO.update(this.usuarioLogueado);
            //System.out.println("Telefono despues de merge: "+this.usuarioLogueado.getTelefono());
            return true;
        }
        else 
            return false;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    @Override
    public String getNombres() {
        return usuarioLogueado.getNombre();
    }
    
    @Override
    public String getApellidos() {
        return usuarioLogueado.getApellido1()+ " "+ usuarioLogueado.getApellido2();
    }
    
    @Override
    public String getRol() {
        return usuarioLogueado.getRol().getId();
    }
    
    @Override
    public String getIdUsuario() {
        return usuarioLogueado.getUsername();
    }
    
    @Override
    public String getEmail() {
        return usuarioLogueado.getEmail();
    }
    
    public int getTelefono() {
        return usuarioLogueado.getTelefono();
    }
}
