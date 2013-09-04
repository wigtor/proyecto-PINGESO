/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.ComunaDAO;
import entities.Comuna;
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
    public boolean agregarComuna(String nombreComuna) throws Exception {
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        ComunaDAO comDAO = fabricaDAO.getComunaDAO();
        /*Primero se verifica la existencia de la comuna a través
        * de una búsqueda por nombre usando el nombre de la comuna
        * a agregar como argumento. Para ello, si la lista de resultados
        * que retorna la función buscarPorNombre de ComunaDAO es nula
        * entonces la comuna no existe, caso contrario la comuna ya existe
        * y en ese caso, se lanza la excepción correspondiente.
        */

        try{
            if(comDAO.buscarPorNombre(nombreComuna)!=null){
                throw new Exception("La comuna ya existe.");
            } else {
                Comuna nuevaComuna;
                nuevaComuna = new Comuna();
                nuevaComuna.setNombre(nombreComuna);
                comDAO.insert(nuevaComuna);
            }
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar la comuna \"".concat(nombreComuna).concat("\":".concat(e.toString())));
            throw new Exception ("Error al intentar agregar la nueva comuna.");
        }
        return true;
    }

    @Override
    public boolean editarComuna(Integer idComuna, String nombreAntiguoComuna, String nombreNuevoComuna) throws Exception {
        try{
            //failsafe
            if(nombreAntiguoComuna.equals(nombreNuevoComuna)){
                throw new Exception("Los nombres de comuna son iguales.");
            }
            
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            ComunaDAO comDAO = fabricaDAO.getComunaDAO();
            Comuna comunaEditar;
            comunaEditar=comDAO.find(idComuna.intValue());
            if(comunaEditar==null){
                throw new Exception ("No se puede editar la comuna, el identificador ".concat(idComuna.toString()).concat(" no existe."));
            } else {
                if(!(comunaEditar.getNombre().equals(nombreAntiguoComuna))){
                    //La comuna que se intenta editar a través del ID tiene un nombre
                    //distinto al esperado
                    throw new Exception ("No se puede editar la comuna, el nombre asociado al ID es diferente.");
                } else {
                    comunaEditar.setNombre(nombreNuevoComuna);
                    comDAO.update(comunaEditar);
                    return true;
                }
            }
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar la comuna ".concat(nombreAntiguoComuna).concat(":".concat(e.toString())));
            throw new Exception ("Error al intentar editar la comuna.");
        }
    }

    @Override
    public boolean eliminarComuna(Integer idComuna, String nombreComunaEliminar) throws Exception{
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            ComunaDAO comDAO = fabricaDAO.getComunaDAO();
            Comuna comunaEliminar;
            comunaEliminar = comDAO.find(idComuna.intValue());
            if (comunaEliminar == null){
                throw new Exception("No se puede eliminar la comuna, el identificador ".concat(idComuna.toString()).concat(" no existe."));
            } else {
                if(!(comunaEliminar.getNombre().equals(nombreComunaEliminar))){
                    throw new Exception("No se puede eliminar la comuna, el nombre asociado al ID es diferente.");
                }else{
                    comDAO.delete(comunaEliminar);
                    return true;
                }
            }
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar la comuna \"".concat(nombreComunaEliminar).concat("\":".concat(e.toString())));
            throw new Exception ("Error al intentar eliminar la comuna.");

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
            ComunaDAO comDAO = fabricaDAO.getComunaDAO();
            Comuna com;
            com = comDAO.buscarPorNombre(nombreComunaBusq);
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
    
    @Override
    public Comuna getComunaByID(Integer idComuna){
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            ComunaDAO comDAO = fabricaDAO.getComunaDAO();
            Comuna com;
            com = comDAO.find(idComuna);
            if (com != null){
                return com;
            } else {
                return null;
            }
        } catch (Exception e){
            Logger.getLogger(CrudComuna.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar buscar la comuna con ID ".concat(idComuna.toString()).concat(" :").concat(e.toString()));
            return null;
        }
    }

    
}
