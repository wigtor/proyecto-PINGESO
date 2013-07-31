/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Inspector;
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
public class CrudInspector implements CrudInspectorLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    private Usuario usertemp;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @TransactionAttribute (TransactionAttributeType.REQUIRED)
    @Override
    public void agregarInspector(String username, String password, int rut, String nombre, String apellido1, String apellido2, String mail, int telefono){
        
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
       
        Inspector nvoInspect;
        Usuario nvoUsuario = new Usuario();
        nvoInspect = new Inspector();
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
        InspectorDAO inspectDAO = factoryDeDAOs.getInspectorDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        RolDAO rolDAO = factoryDeDAOs.getRolDAO();
        Rol nvoRol = rolDAO.find("Inspector");
        if (nvoRol == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol = new Rol();
            nvoRol.setNombreRol("Inspector");
            rolDAO.insert(nvoRol);
        }
        nvoUsuario.setRol(nvoRol);
        
        userDAO.insert(nvoUsuario);
        inspectDAO.insert(nvoInspect);
        System.out.println("Insertado");
        //factoryDeDAOs.close();
    }
    
    @Override
    public Collection<Inspector> getAllInspectores() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        InspectorDAO inspectDAO = factoryDeDAOs.getInspectorDAO();
        return inspectDAO.findAll();
        
    }
    
    @Override
    public Usuario getInspector(String userName) {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO usuarioDAO = factoryDeDAOs.getUsuarioDAO();
        return usuarioDAO.find(userName);
    }
    
    @Override
    public Usuario getInspectorByRut(Integer rutUser) {
        //Hago los DAO
        if (rutUser == null) {
            return null;
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO usuarioDAO = factoryDeDAOs.getUsuarioDAO();
        return usuarioDAO.findByRut(rutUser.intValue());
    }
    
    @Override
    public void editarInspector(Integer rutUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono) {
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
    public boolean eliminarInspector(Integer rutUser){
        if (rutUser != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            InspectorDAO inspDAO = factoryDeDAOs.getInspectorDAO();
            try {
                return inspDAO.deleteByRut(rutUser);
            }
            catch (Exception e) {
                return false;
            }
            
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

