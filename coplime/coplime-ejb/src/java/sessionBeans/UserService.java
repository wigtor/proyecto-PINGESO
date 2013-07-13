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
    public boolean setUsuarioLogueado(String username) {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        
        this.usuarioLogueado = userDAO.find(username);
        if (this.usuarioLogueado == null) {
            return false;
        }
        return true;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Usuario buscarUsuario(String username, String password) {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        password = conviertePassToMd5(password);
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
    
    @Override
    public void cambiarPass(String passActual, String nvaPass) throws Exception{
        if (this.usuarioLogueado != null) {
            //System.out.println("modificando contraseña del usuario");
            //Hago los DAO
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
            String passwordMd5_real = usuarioLogueado.getPassword();
            String passwordMd5_puesta = conviertePassToMd5(passActual);
            if (passwordMd5_puesta.equals(passwordMd5_real)) {
                usuarioLogueado.setPassword(conviertePassToMd5(nvaPass));
                userDAO.update(usuarioLogueado);
            }
            else {
                throw new Exception("La contraseña actual no es correcta");
            }
        }
        else {
            throw new Exception("No existe un usuario con sesión iniciada o es inválida");
        }
    }

    public Usuario getUsuarioLogueado() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado;
    }
    
    @Override
    public String getNombres() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado.getNombre();
    }
    
    @Override
    public String getApellidos() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado.getApellido1()+ " "+ usuarioLogueado.getApellido2();
    }
    
    @Override
    public String getRol() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado.getRol().getNombreRol();
    }
    
    @Override
    public String getIdUsuario() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado.getUsername();
    }
    
    @Override
    public String getEmail() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado.getEmail();
    }
    
    @Override
    public Integer getTelefono() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado.getTelefono();
    }
    
    @Override
    public Integer getRut() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado.getRut();
    }
    
    @Override
    public String getUsername() {
        if (usuarioLogueado == null)
            return null;
        return usuarioLogueado.getUsername();
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
