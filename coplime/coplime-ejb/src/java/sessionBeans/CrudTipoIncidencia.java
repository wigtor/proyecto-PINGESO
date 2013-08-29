/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.TipoIncidenciaDAO;
import entities.TipoIncidencia;
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
public class CrudTipoIncidencia implements CrudTipoIncidenciaLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    @Override
    public boolean agregarTipoIncidencia(String tipoIncidencia, boolean visibleAlUsuario) {
        try{
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        TipoIncidenciaDAO matDAO = fabricaDAO.getTipoIncidenciaDAO();
        TipoIncidencia nuevo = new TipoIncidencia(tipoIncidencia, visibleAlUsuario);
        matDAO.insert(nuevo);
        } catch (Exception e){
            Logger.getLogger(CrudTipoIncidencia.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar el tipo de incidencia \"".concat(tipoIncidencia).concat("\":".concat(e.toString())));
            return false;
        }
        return true;
    }

    @Override
    public boolean editarTipoIncidencia(String tipoIncidenciaOrig, String tipoIncidenciaNuevo, boolean visibleAlUsuarioOrig, boolean visibleAlUsuarioNuevo) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            TipoIncidenciaDAO tipoDAO = fabricaDAO.getTipoIncidenciaDAO();
            TipoIncidencia tipoEditar;
            tipoEditar = tipoDAO.find(tipoIncidenciaOrig);
            if (tipoEditar != null){
                tipoEditar.setNombreIncidencia(tipoIncidenciaNuevo);
                if(visibleAlUsuarioOrig != visibleAlUsuarioNuevo){
                    tipoEditar.setVisibleAlUsuario(visibleAlUsuarioNuevo);
                }
                tipoDAO.update(tipoEditar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudTipoIncidencia.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar el tipo de incidencia \"".concat(tipoIncidenciaOrig).concat("\":".concat(e.toString())));
            return false;
        }
    }

    @Override
    public boolean eliminarTipoIncidencia(String tipoIncidencia) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            TipoIncidenciaDAO tipoDAO = fabricaDAO.getTipoIncidenciaDAO();
            TipoIncidencia tipoEliminar;
            tipoEliminar = tipoDAO.find(tipoIncidencia);
            if (tipoEliminar != null){
                tipoDAO.delete(tipoEliminar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            Logger.getLogger(CrudTipoIncidencia.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar el tipo de incidencia \"".concat(tipoIncidencia).concat("\":".concat(e.toString())));
            return false;
        }
    }

    @Override
    public TipoIncidencia getTipoIncidenciaByName(String tipoIncidenciaBuscada) {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            TipoIncidenciaDAO tipoDAO = fabricaDAO.getTipoIncidenciaDAO();
            TipoIncidencia tipo;
            tipo = tipoDAO.find(tipoIncidenciaBuscada);
            if (tipo != null){
                return tipo;
            } else {
                return null;
            }
        } catch (Exception e){
            Logger.getLogger(CrudTipoIncidencia.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar buscar el tipo de incidencia \"".concat(tipoIncidenciaBuscada).concat("\":".concat(e.toString())));
            return null;
        }
    }
    
    @Override
    public Collection getAllTiposIncidencia() {
        Collection resultado;
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            TipoIncidenciaDAO tipoDAO = fabricaDAO.getTipoIncidenciaDAO();
            resultado = tipoDAO.findAll();
            return resultado;            
        } catch (Exception e) {
            Logger.getLogger(CrudTipoIncidencia.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar recuperar la lista de tipos de incidencia: ".concat(e.toString()));
            return new LinkedList();
        }
    }
    
    

}
