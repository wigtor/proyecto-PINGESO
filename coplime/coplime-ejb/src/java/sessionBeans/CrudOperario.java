/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.UsuarioDAO;
import entities.OperarioMantencion;
import entities.Rol;
import entities.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collection;
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
public class CrudOperario implements CrudOperarioLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    private Usuario usertemp;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @TransactionAttribute (TransactionAttributeType.REQUIRED)
    @Override
    public void agregarOperario(String username, String password, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono){
        
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
       
        OperarioMantencion nvoInspect;
        Usuario nvoUsuario = new Usuario();
        nvoInspect = new OperarioMantencion();
        nvoUsuario.setNombre(nombre);
        nvoUsuario.setApellido1(apellido1);
        nvoUsuario.setRut(rut);
        nvoUsuario.setEmail(mail);
        nvoUsuario.setApellido2(apellido2);
        nvoUsuario.setUsername(username);
        nvoUsuario.setPassword(password);
        nvoUsuario.setTelefono(telefono);
        nvoInspect.setUsuario(nvoUsuario);
        
        
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        OperarioDAO inspectDAO = factoryDeDAOs.getOperarioDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        RolDAO rolDAO = factoryDeDAOs.getRolDAO();
        Rol nvoRol = rolDAO.find("Operario");
        if (nvoRol == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol = new Rol();
            nvoRol.setNombreRol("Operario");
            rolDAO.insert(nvoRol);
        }
        nvoUsuario.setRol(nvoRol);
        
        userDAO.insert(nvoUsuario);
        inspectDAO.insert(nvoInspect);
        System.out.println("Insertado");
        //factoryDeDAOs.close();
    }
    
    @Override
    public Collection<OperarioMantencion> getAllOperarios() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        OperarioDAO inspectDAO = factoryDeDAOs.getOperarioDAO();
        return inspectDAO.findAll();
    }
    
    @Override
    public Usuario getOperario(String userName) {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO usuarioDAO = factoryDeDAOs.getUsuarioDAO();
        return usuarioDAO.find(userName);
        
    }
    
    @Override
    public Usuario getOperarioByRut(Integer rutUser) {
        //Hago los DAO
        if (rutUser == null) {
            return null;
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO usuarioDAO = factoryDeDAOs.getUsuarioDAO();
        return usuarioDAO.findByRut(rutUser.intValue());
    }
    
    @Override
    public void editarOperario(Integer rutUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono) {
        if (rutUser == null) {
            return;
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        
        Usuario editInspector = userDAO.findByRut(rutUser.intValue());
        if (editInspector == null) {
            System.out.println("Usuario a editar no encontrado");
            return;
        }
        editInspector.setNombre(nombre);
        editInspector.setUsername(userName);
        editInspector.setApellido1(apellido1);
        editInspector.setApellido2(apellido2);
        editInspector.setEmail(mail);
        editInspector.setTelefono(telefono);
        
        
        if(resetContraseña == true){
            String password = Integer.toString(editInspector.getRut());
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
            editInspector.setPassword(password);        
        }
        
        this.usertemp = editInspector;
        this.usertemp = userDAO.update(editInspector);
    }
    
    @Override
    public boolean eliminarOperario(Integer rutUser){
        if (rutUser != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            OperarioDAO inspDAO = factoryDeDAOs.getOperarioDAO();
            return inspDAO.deleteByRut(rutUser);
        }
        return false;
    }

    public Usuario getUsertemp() {
        return usertemp;
    }

    public void setUsertemp(Usuario usertemp) {
        this.usertemp = usertemp;
    }
    
}
