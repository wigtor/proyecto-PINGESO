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
    public boolean agregarTipoIncidencia(String tipoIncidencia, boolean visibleAlUsuario) throws Exception {
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            TipoIncidenciaDAO matDAO = fabricaDAO.getTipoIncidenciaDAO();
            if(matDAO.find(tipoIncidencia)!=null){
                throw new Exception("El tipo de incidencia ya existe.");
            } else {
                TipoIncidencia nuevo;
                nuevo = new TipoIncidencia();
                nuevo.setNombreIncidencia(tipoIncidencia);
                nuevo.setVisibleAlUsuario(visibleAlUsuario);
                matDAO.insert(nuevo);
            }

        } catch (Exception e){
            Logger.getLogger(CrudTipoIncidencia.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar agregar el tipo de incidencia \"".concat(tipoIncidencia).concat("\":".concat(e.toString())));
            throw new Exception("Error al intentar agregar el nuevo tipo de incidencia.");
        }
        return true;
    }

    @Override
    public boolean editarTipoIncidencia(Integer idIncidencia, String tipoIncidenciaOrig, String tipoIncidenciaNuevo, boolean visibleAlUsuarioOrig, boolean visibleAlUsuarioNuevo) throws Exception {
        //failsafe
        if(tipoIncidenciaOrig.equals(tipoIncidenciaNuevo)){
            throw new Exception("Los tipos de incidencia son iguales.");
        }
        
        try{
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            TipoIncidenciaDAO tipoDAO = fabricaDAO.getTipoIncidenciaDAO();
            TipoIncidencia tipoEditar;
            tipoEditar = tipoDAO.find(idIncidencia.intValue());
            if (tipoEditar == null){
                throw new Exception("No se puede editar el tipo de incidencia, el ID ".concat(idIncidencia.toString()).concat(" no existe."));
            } else {
                if(!(tipoEditar.getNombreIncidencia().equals(tipoIncidenciaOrig))){
                    throw new Exception("No se puede editar el tipo de incidencia, el nombre asociado al ID es diferente.");
                } else {
                    tipoEditar.setNombreIncidencia(tipoIncidenciaNuevo);
                    if(visibleAlUsuarioOrig != visibleAlUsuarioNuevo){
                        tipoEditar.setVisibleAlUsuario(visibleAlUsuarioNuevo);
                    }
                    tipoDAO.update(tipoEditar);
                }
            }   
        } catch (Exception e){
            Logger.getLogger(CrudTipoIncidencia.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar editar el tipo de incidencia \"".concat(tipoIncidenciaOrig).concat("\":".concat(e.toString())));
            throw new Exception("Error al intentar editar el tipo de incidencia");
        }
        return true;
    }

    @Override
    public boolean eliminarTipoIncidencia(Integer idIncidencia, String tipoIncidencia) throws Exception {
        try{
            
            DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            TipoIncidenciaDAO tipoDAO = fabricaDAO.getTipoIncidenciaDAO();
            TipoIncidencia tipoEliminar;
            tipoEliminar = tipoDAO.find(idIncidencia.intValue());
            if (tipoEliminar == null){
                throw new Exception("No se puede eliminar el estado, el identificador ".concat(idIncidencia.toString()).concat(" no existe."));
            } else {
                if(!(tipoEliminar.getNombreIncidencia().equals(tipoIncidencia))){
                    throw new Exception("No se puede eliminar el tipo de incidencia, el nombre asociado al ID es diferente.");
                } else {
                    tipoDAO.delete(tipoEliminar);
                    return true;  
                }
            }
        } catch (Exception e){
            Logger.getLogger(CrudTipoIncidencia.class.getName()).log(Level.WARNING, "Ha ocurrido un error al intentar eliminar el tipo de incidencia \"".concat(tipoIncidencia).concat("\":".concat(e.toString())));
            throw new Exception("Error al intentar eliminar el tipo de incidencia.");
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
