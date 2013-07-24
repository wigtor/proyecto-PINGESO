/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.PuntoLimpioDAO;
import entities.Estado;
import entities.Inspector;
import entities.PuntoLimpio;
import entities.Usuario;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateful
public class CrudPuntoLimpio implements CrudPuntoLimpioLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    private Usuario usertemp;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @TransactionAttribute (TransactionAttributeType.REQUIRED)
    @Override
    public void agregarPuntoLimpio(String nombre, String comuna, Calendar fechaProxRev, String estadoIni, int numInspEnc){
        
    }
    
    @Override
    public Collection<PuntoLimpio> getAllPuntosLimpios() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        return ptoDAO.findAll();
    }
    
    @Override
    public Collection<Inspector> getAllInspectores() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        InspectorDAO inspDAO = factoryDeDAOs.getInspectorDAO();
        return inspDAO.findAll();
    }
    
    @Override
    public Collection<Estado> getAllEstadosPuntoLimpio() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        return estDAO.findAll();
    }
    
    
    @Override
    public PuntoLimpio getPuntoLimpioByNum(Integer num) {
        //Hago los DAO
        if (num == null) {
            return null;
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        return ptoDAO.findByNum(num.intValue());
    }
    
    
    @Override
    public void editarPuntoLimpio(Integer idPtoLimpio, String nombre, String comuna, Calendar fechaProxRev, String estadoIni, int numInspEnc) {
        if (idPtoLimpio == null) {
            return;
        }
    }
    
    
    @Override
    public boolean eliminarPuntoLimpioByNum(Integer num){
        if (num != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
            return ptoDAO.deleteByNum(num);
        }
        return false;
    }
    
    @Override
    public boolean eliminarPuntoLimpio(Integer id){
        if (id != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
            return ptoDAO.delete(id);
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
