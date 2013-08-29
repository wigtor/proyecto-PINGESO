/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.ComunaDAO;
import DAO.interfaces.MaterialDAO;
import entities.Comuna;
import entities.Material;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Armando
 */
@Stateless
public class CrudComuna implements CrudComunaLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    @Override
    public boolean agregarComuna(String nombreComuna) {
        try{
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        ComunaDAO comDAO = fabricaDAO.getComunaDAO();
        Comuna nuevaComuna = new Comuna();
        nuevaComuna.setNombre(nombreComuna);
        comDAO.insert(nuevaComuna);
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar la comuna \"".concat(nombreComuna).concat("\":".concat(e.toString())));
            return false;
        }
        return true;
    }

    @Override
    public boolean editarComuna(String nombreAntiguoComuna, String nombreNuevoComuna) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            ComunaDAO comDAO = fabricaDAO.getComunaDAO();
            Comuna comunaEditar;
            comunaEditar = comDAO.find(nombreAntiguoComuna);
            if (comunaEditar != null){
                comunaEditar.setNombre(nombreAntiguoComuna);
                comDAO.update(comunaEditar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar la comuna ".concat(nombreAntiguoComuna).concat(":".concat(e.toString())));
            return false;
        }
    }

    @Override
    public boolean eliminarComuna(String nombreComunaEliminar) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            ComunaDAO comDAO = fabricaDAO.getComunaDAO();
            Comuna comunaEliminar;
            comunaEliminar = comDAO.find(nombreComunaEliminar);
            if (comunaEliminar != null){
                comDAO.delete(comunaEliminar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar la comuna \"".concat(nombreComunaEliminar).concat("\":".concat(e.toString())));
            return false;
        }
    }

    @Override
    public Collection getAllComunas() {
        Collection resultado;
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            ComunaDAO comDAO = fabricaDAO.getComunaDAO();
            resultado = comDAO.findAll();
            return resultado;
            
        } catch (Exception e) {
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar recuperar la lista de comunas: ".concat(e.toString()));
            return new LinkedList();
        }
    }

    @Override
    public Comuna getComunaByName(String nombreComunaBusq) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            ComunaDAO matDAO = fabricaDAO.getComunaDAO();
            Comuna com;
            com = matDAO.find(nombreComunaBusq);
            if (com != null){
                return com;
            } else {
                return null;
            }
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar buscar la comuna \"".concat(nombreComunaBusq).concat("\":".concat(e.toString())));
            return null;
        }
    }

    
}
