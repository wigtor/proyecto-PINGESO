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
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class CrudAdministrador implements CrudAdministradorLocal {
    @EJB
    private CrudUsuariosComunLocal crudUsuariosComun;
    //Se inyecta el entity manager, no se crea en los DAO, pero si se le pasa a los DAO
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    
    @Override
    public void agregarAdministrador(String username, Integer rut, String nombre, String apellido1, String apellido2, String mail, Integer telefono) throws Exception{
        if (rut == null) {
            throw new Exception("El rut no puede ser nulo");
        }
        if (crudUsuariosComun.existeUsername(username)) {
            throw new Exception("El nombre de usuario \"".concat(username).concat("\" ya existe"));
        }
        if (crudUsuariosComun.existeRut(rut)) {
            throw new Exception("El rut del usuario \"".concat(rut.toString()).concat("\" ya existe"));
        }
        
        
        String password = crudUsuariosComun.convertToMD5(Integer.toString(rut));
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
    public Collection<Administrador> getAllAdministradores() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        AdministradorDAO adminDAO = factoryDeDAOs.getAdministradorDAO();
        return adminDAO.findAll();
        
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
    
    @Override
    public Usuario getAdministradorByRut(Integer rutUser) {
        //Hago los DAO
        if (rutUser == null) {
            return null;
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UsuarioDAO usuarioDAO = factoryDeDAOs.getUsuarioDAO();
        return usuarioDAO.findByRut(rutUser.intValue());
    }
    
    @Override
    public void editarAdministrador(Integer rutUser, String userName,String nombre, String apellido1, String apellido2, String mail, boolean resetContraseña,int telefono) {
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
            String password = crudUsuariosComun.convertToMD5(Integer.toString(editInspector.getRut()));
            editInspector.setPassword(password);        
        }
        
        userDAO.update(editInspector);
    }
    
}
