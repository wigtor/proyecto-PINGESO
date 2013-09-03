/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.ComunaDAO;
import DAO.interfaces.ContenedorDAO;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.HistoricoContenedorDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.MaterialDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.UnidadMedidaDAO;
import entities.Comuna;
import entities.Contenedor;
import entities.Estado;
import entities.HistoricoContenedor;
import entities.Inspector;
import entities.Material;
import entities.PuntoLimpio;
import entities.UnidadMedida;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class CrudPuntoLimpio implements CrudPuntoLimpioLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Integer agregarPuntoLimpio(Integer numeroDadoPorCliente, String nombre, Integer idComuna, String direccion,
                    Calendar fechaProxRev, Integer idEstadoIni, Integer numInspEnc) throws Exception {
        
        if(isValidDate(fechaProxRev)) {
            throw new Exception("La fecha para la primera revisión no puede ser pasada");
        }
        
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        ComunaDAO comDAO = factoryDeDAOs.getComunaDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        InspectorDAO inspDAO = factoryDeDAOs.getInspectorDAO();
        
        if (existeNumPuntoLimpio(numeroDadoPorCliente)) {
            throw new Exception("El número del punto limpio \"".concat(numeroDadoPorCliente.toString()).concat("\" ya existe"));
        }
        if (existeNombrePuntoLimpio(nombre)) {
            throw new Exception("El nombre del punto limpio \"".concat(nombre).concat("\" ya existe"));
        }
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
    
    private boolean isValidDate(Calendar fecha) {
        Calendar actual = Calendar.getInstance();
        if (actual.before(fecha)) { //actual < fecha
            return false;
        }
        return true;
    }
    
    @Override
    public void agregarContenedor(Integer numPuntoLimpio, Integer idMaterial, Integer idEstadoIni, int llenadoIni, int capacidad, Integer idUnidadMedida) throws Exception{
        
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        ContenedorDAO contDAO = factoryDeDAOs.getContenedorDAO();
        UnidadMedidaDAO uniMedDAO = factoryDeDAOs.getUnidadMedidaDAO();
        MaterialDAO matDAO = factoryDeDAOs.getMaterialDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        HistoricoContenedorDAO histDAO = factoryDeDAOs.getHistoricoContenedorDAO();
        
        PuntoLimpio p = ptoDAO.find(numPuntoLimpio.intValue());
        if (p == null) {
            //System.out.println("No se encontró el id del puntolimpio al agregar el contenedor");
            throw new Exception("El punto limpio no es válido");
        }
        Estado estTemp = estDAO.find(idEstadoIni.intValue());
        if (estTemp == null) {
            //System.out.println("No se encontró el id del estado al agregar el contenedor");
            throw new Exception("El estado seleccionado no es válido");
        }
        
        Material matTemp = matDAO.find(idMaterial.intValue());
        if (matTemp == null) {
            //System.out.println("No se encontró el id del material al agregar el contenedor");
            throw new Exception("El material seleccionado no es válido");
        }
        
        UnidadMedida uniTemp = uniMedDAO.find(idUnidadMedida.intValue());
        if (uniTemp == null) {
            //System.out.println("No se encontró el id de la unidad de medida al agregar el contenedor");
            throw new Exception("La unidad de medida del material no es válida");
        }
        
        Contenedor nvoCont = new Contenedor();
        nvoCont.setPuntoLimpio(p);
        nvoCont.setCapacidad(capacidad);
        nvoCont.setPorcentajeUsoEstimado(llenadoIni);
        nvoCont.setProcentajeUso(llenadoIni);
        
        nvoCont.setEstadoContenedor(estTemp);
        nvoCont.setMaterialDeAcopio(matTemp);
        nvoCont.setUnidadMedida(uniTemp);
        
        HistoricoContenedor primerHistorico = new HistoricoContenedor();
        primerHistorico.setPorcentajeLlenado(llenadoIni);
        primerHistorico.setFechaHora(Calendar.getInstance());
        primerHistorico.setContenedor(nvoCont);
        nvoCont.getHistorialContenedor().add(primerHistorico);
        
        
        p.getContenedores().add(nvoCont);
        try {
            histDAO.insert(primerHistorico);
            contDAO.insert(nvoCont);
            ptoDAO.update(p);
        }
        catch (Exception e) {
            throw new Exception("No ha sido posible agregar el contenedor");
        }
        
    }
    
    
    @Override
    public void editarPuntoLimpio(Integer idPtoLimpio, String nombre, Integer idComuna, String direccion, Calendar fechaProxRev, Integer idEstadoIni, Integer numInspEnc) throws Exception{
        if (idPtoLimpio == null) {
            throw new Exception("El N° del punto limpio no es válido");
        }
        
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        ComunaDAO comDAO = factoryDeDAOs.getComunaDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        InspectorDAO inspDAO = factoryDeDAOs.getInspectorDAO();
        
        
        try {
            PuntoLimpio p = ptoDAO.find(idPtoLimpio.intValue());
            
            if (!p.getFechaProxRevision().equals(fechaProxRev)) {
                if (isValidDate(fechaProxRev)) {
                    throw new Exception("La fecha para la primera revisión no puede ser pasada");
                }
            }

            if (existeNombrePuntoLimpioExcepto(nombre, p)) {
                throw new Exception("El nombre del punto limpio \"".concat(nombre).concat("\" ya existe"));
            }

            Comuna comunaP = comDAO.find(idComuna);
            if (comunaP == null) {
                throw new Exception("La comuna seleccionada no es válida");
            }
            Estado estadoP = estDAO.find(idEstadoIni);
            if (estadoP == null) {
                throw new Exception("El estado seleccionado no es válido");
            }
            Inspector inspectorEnc = inspDAO.find(numInspEnc);
            if (inspectorEnc == null) {
                throw new Exception("El inspector seleccionado no es válido");
            }
            if (nombre== null) {
                throw new Exception("El nombre ingresado no puede ser nulo");
            }
            if (nombre.trim().isEmpty()) {
                throw new Exception("El nombre ingresado no puede ser vacio");
            }
            if (direccion== null) {
                throw new Exception("La dirección ingresada no puede ser nula");
            }
            if (direccion.trim().isEmpty()) {
                throw new Exception("La dirección ingresada no puede ser vacia");
            }
            
            p.setNombre(nombre.trim());
            p.setUbicacion(direccion.trim());
            p.setFechaProxRevision(fechaProxRev);
            p.setComuna(comunaP);
            p.setEstadoGlobal(estadoP);
            p.setInspectorEncargado(inspectorEnc);
            ptoDAO.update(p);
        }
        catch (Exception e) {
            
            throw new Exception("No ha sido posible editar los datos del punto limpio N°".concat(idPtoLimpio.toString()));
        }
    }
    
    
    @Override
    public void eliminarPuntoLimpioByNum(Integer num) throws Exception{
        if (num != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
            try {
                if (!ptoDAO.delete(num))
                    throw new Exception("No ha sido posible eliminar el punto limpio N°".concat(num.toString()));
            }
            catch (Exception e) {
                throw new Exception("No ha sido posible eliminar el punto limpio N°".concat(num.toString()));
            }
        }
        throw new Exception("El N° del punto limpio no puede ser nulo");
    }
    
    @Override
    public void eliminarContenedorById(Integer num) throws Exception{
        if (num != null) {
            DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
            PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
            ContenedorDAO contDAO = factoryDeDAOs.getContenedorDAO();
            HistoricoContenedorDAO histDAO = factoryDeDAOs.getHistoricoContenedorDAO();
            Contenedor contEliminar = contDAO.find(num);
            PuntoLimpio puntoPerteneciente;
            if (contEliminar != null) {
                puntoPerteneciente = contEliminar.getPuntoLimpio();
                puntoPerteneciente.getContenedores().remove(contEliminar);
                for (HistoricoContenedor h : contEliminar.getHistorialContenedor()) {
                    try {
                        histDAO.delete(h);
                    } catch (Exception e) {
                        throw new Exception("No ha sido posible eliminar el contenedor de N°".concat(num.toString()));
                    }
                }
                contEliminar.getHistorialContenedor().clear();
                if (!contDAO.delete(contEliminar)) {
                    throw new Exception("No ha sido posible eliminar el contenedor N°".concat(num.toString()));
                }
                ptoDAO.update(puntoPerteneciente);
            } else {
                throw new Exception("El contenedor que se intenta eliminar no existe");
            }
        }
        else {
            throw new Exception("El N° del contenedor no puede ser nulo");
        }
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
    public Contenedor getContenedor(Integer id) {
        if (id == null)
            return null;
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        ContenedorDAO contDAO = factoryDeDAOs.getContenedorDAO();
        return contDAO.find(id.intValue());
    }
    
    @Override
    public Collection<Contenedor> getContenedoresByPuntoLimpio(Integer idPtoLimpio) {
        if (idPtoLimpio == null)
            return null;
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        ContenedorDAO contDAO = factoryDeDAOs.getContenedorDAO();
        return contDAO.findByPuntoLimpio(idPtoLimpio.intValue());
    }
    
    @Override
    public boolean cambiarEstadoContenedor(Integer idContenedor, Integer idEstadoContenedor, int llenadoContenedor) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        ContenedorDAO contDAO = factoryDeDAOs.getContenedorDAO();
        EstadoDAO estDAO = factoryDeDAOs.getEstadoDAO();
        HistoricoContenedorDAO histDAO = factoryDeDAOs.getHistoricoContenedorDAO();
        
        try {
            if (idEstadoContenedor == null)
                return false;
            Estado e = estDAO.find(idEstadoContenedor.intValue());
            if (idContenedor == null)
                return false;
            Contenedor cont = contDAO.find(idContenedor.intValue());
            if (cont == null)
                return false;
            cont.setEstadoContenedor(e);
            cont.setProcentajeUso(llenadoContenedor);
            
            //Agrego un nuevo histórico
            HistoricoContenedor primerHistorico = new HistoricoContenedor();
            primerHistorico.setPorcentajeLlenado(llenadoContenedor);
            primerHistorico.setFechaHora(Calendar.getInstance());
            primerHistorico.setContenedor(cont);
            cont.getHistorialContenedor().add(primerHistorico);
            
            histDAO.insert(primerHistorico);
            contDAO.update(cont);
        }
        catch (Exception e) {
            System.out.print("Error: "+e.getMessage());
            return false;
        }
        return true;
    }
    
    @Override
    public boolean existeNumPuntoLimpio(Integer numero) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        return ptoDAO.numExist(numero);
    }
    
    @Override
    public boolean existeNombrePuntoLimpio(String nombre) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        return ptoDAO.nombreExist(nombre);
    }
    
    @Override
    public boolean existeNombrePuntoLimpioExcepto(String nombre, PuntoLimpio p) {
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        PuntoLimpioDAO ptoDAO = factoryDeDAOs.getPuntoLimpioDAO();
        return ptoDAO.nombreExistExcept(nombre, p.getId());
    }
}
