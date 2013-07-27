/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.ComunaDAO;
import DAO.interfaces.ContenedorDAO;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.MaterialDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.UnidadMedidaDAO;
import entities.Comuna;
import entities.Contenedor;
import entities.Estado;
import entities.Inspector;
import entities.Material;
import entities.PuntoLimpio;
import entities.UnidadMedida;
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
    public Integer agregarPuntoLimpio(String nombre, Integer numeroDadoPorCliente, Integer idComuna, String direccion,
                    Calendar fechaProxRev, Integer idEstadoIni, Integer numInspEnc){
        
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        ComunaDAO comDAO = factoryDeDAOs.getComunaDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        InspectorDAO inspDAO = factoryDeDAOs.getInspectorDAO();
        try {
            PuntoLimpio p = new PuntoLimpio(nombre, direccion, numeroDadoPorCliente);
            p.setFechaProxRevision(fechaProxRev);
            
            Comuna comunaP = comDAO.find(idComuna);
            p.setComuna(comunaP);
            
            Estado estadoP = estDAO.find(idEstadoIni);
            p.setEstadoGlobal(estadoP);
            
            Inspector inspectorEnc = inspDAO.find(numInspEnc);
            p.setInspectorEncargado(inspectorEnc);
            
            ptoDAO.insert(p);
        }
        catch (Exception e) {
            
            return null;
        }
        
        return numeroDadoPorCliente;
    }
    
    @Override
    public boolean agregarContenedor(Integer numPuntoLimpio, Integer idMaterial, Integer idEstadoIni, int llenadoIni, int capacidad, Integer idUnidadMedida) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        ContenedorDAO contDAO = factoryDeDAOs.getContenedorDAO();
        UnidadMedidaDAO uniMedDAO = factoryDeDAOs.getUnidadMedidaDAO();
        MaterialDAO matDAO = factoryDeDAOs.getMaterialDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        
        PuntoLimpio p = ptoDAO.find(numPuntoLimpio.intValue());
        if (p == null) {
            System.out.println("No se encontró el id del puntolimpio al agregar el contenedor");
            return false;
        }
        Estado estTemp = estDAO.find(idEstadoIni.intValue());
        if (estTemp == null) {
            System.out.println("No se encontró el id del estado al agregar el contenedor");
            return false;
        }
        
        Material matTemp = matDAO.find(idMaterial.intValue());
        if (matTemp == null) {
            System.out.println("No se encontró el id del material al agregar el contenedor");
            return false;
        }
        
        UnidadMedida uniTemp = uniMedDAO.find(idUnidadMedida.intValue());
        if (uniTemp == null) {
            System.out.println("No se encontró el id de la unidad de medida al agregar el contenedor");
            return false;
        }
        
        Contenedor nvoCont = new Contenedor();
        nvoCont.setPuntoLimpio(p);
        nvoCont.setCapacidad(capacidad);
        nvoCont.setPorcentajeUsoEstimado(llenadoIni);
        nvoCont.setProcentajeUso(llenadoIni);
        
        nvoCont.setEstadoContenedor(estTemp);
        nvoCont.setMaterialDeAcopio(matTemp);
        nvoCont.setUnidadMedida(uniTemp);
        
        
        p.getContenedores().add(nvoCont);
        try {
            contDAO.insert(nvoCont);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    @Override
    public Collection<PuntoLimpio> getAllPuntosLimpios() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        return ptoDAO.findAll();
    }
    
    @Override
    public Collection<Material> getAllMateriales() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        MaterialDAO matDAO = factoryDeDAOs.getMaterialDAO();
        return matDAO.findAll();
    }
    
    @Override
    public Collection<UnidadMedida> getAllUnidadesMedida() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        UnidadMedidaDAO uniMedDAO = factoryDeDAOs.getUnidadMedidaDAO();
        return uniMedDAO.findAll();
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
    public Collection<Comuna> getAllComunas() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        ComunaDAO comDAO = factoryDeDAOs.getComunaDAO();
        return comDAO.findAll();
    }
    
    @Override
    public PuntoLimpio getPuntoLimpioByNum(Integer num) {
        //Hago los DAO
        if (num == null) {
            return null;
        }
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        return ptoDAO.find(num.intValue());
    }
    
    
    @Override
    public void editarPuntoLimpio(Integer idPtoLimpio, String nombre, Integer idComuna, String direccion, Calendar fechaProxRev, Integer estadoIni, Integer numInspEnc) {
        if (idPtoLimpio == null) {
            return;
        }
    }
    
    
    @Override
    public boolean eliminarPuntoLimpioByNum(Integer num){
        if (num != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
            return ptoDAO.delete(num);
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
