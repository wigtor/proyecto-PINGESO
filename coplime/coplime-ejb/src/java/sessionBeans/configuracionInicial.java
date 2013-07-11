/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.ContenedorDAO;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.MaterialDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.TipoIncidenciaDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Administrador;
import entities.Contenedor;
import entities.Estado;
import entities.Inspector;
import entities.Material;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
import entities.Rol;
import entities.TipoIncidencia;
import entities.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class configuracionInicial implements configuracionInicialLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    DAOFactory factoryDeDAOs;
    List<TipoIncidencia> tiposIncidencias;
    List<Estado> estadosPuntosLimpios;
    List<Material> materialesPuntosLimpios;
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void primeraEjecicion() {
        factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        cargaUsuarios();
        cargarEstadosPuntosLimpios();
        cargarTipoIncidencias();
        cargarMateriales();
        cargarPuntosLimpios();
        cargarMantenciones();
        cargarNotificaciones();
    }
    
    private void cargarEstadosPuntosLimpios() {
        Estado e1 = new Estado("Operativo");
        Estado e2 = new Estado("Lleno");
        Estado e3 = new Estado("Defectuoso");
        Estado e4 = new Estado("Sucio");
        EstadoDAO estadoDAO = factoryDeDAOs.getEstadoDAO();
        estadoDAO.insert(e1);
        estadoDAO.insert(e2);
        estadoDAO.insert(e3);
        estadoDAO.insert(e4);
        
        this.estadosPuntosLimpios = new LinkedList();
        estadosPuntosLimpios.add(e1);
        estadosPuntosLimpios.add(e2);
        estadosPuntosLimpios.add(e3);
        estadosPuntosLimpios.add(e4);
    }
    
    private void cargarTipoIncidencias() {
        TipoIncidencia t1 = new TipoIncidencia("Contenedor lleno", true);
        TipoIncidencia t2 = new TipoIncidencia("Punto limpio deteriorado", true);
        TipoIncidencia t3 = new TipoIncidencia("Punto limpio mal señalizado", true);
        TipoIncidencia t4 = new TipoIncidencia("Contenedor posiblemente lleno", false);
        TipoIncidenciaDAO tiDAO = factoryDeDAOs.getTipoIncidenciaDAO();
        tiDAO.insert(t1);
        tiDAO.insert(t2);
        tiDAO.insert(t3);
        tiDAO.insert(t4);
        
        this.tiposIncidencias = new LinkedList();
        tiposIncidencias.add(t1);
        tiposIncidencias.add(t2);
        tiposIncidencias.add(t3);
        tiposIncidencias.add(t4);
    }
    
    private void cargarMateriales() {
        Material m1 = new Material("Vidrio");
        Material m2 = new Material("Papel");
        Material m3 = new Material("Latas");
        Material m4 = new Material("Plasticos");
        Material m5 = new Material("Pilas");
        
        MaterialDAO matDAO = factoryDeDAOs.getMaterialDAO();
        matDAO.insert(m1);
        matDAO.insert(m2);
        matDAO.insert(m3);
        matDAO.insert(m4);
        matDAO.insert(m5);
        
        this.materialesPuntosLimpios = new LinkedList();
        materialesPuntosLimpios.add(m1);
        materialesPuntosLimpios.add(m2);
        materialesPuntosLimpios.add(m3);
        materialesPuntosLimpios.add(m4);
        materialesPuntosLimpios.add(m5);
    }
    
    private void cargarPuntosLimpios() {
        PuntoLimpio p1 = new PuntoLimpio("Plaza San Bernardo", "San bernardo", "Plaza de armas", 1);
        p1.setEstadoGlobal(this.estadosPuntosLimpios.get(0));
        p1.setFechaProxRevision(Calendar.getInstance().getTime());
        
        PuntoLimpioDAO plDAO = factoryDeDAOs.getPuntoLimpioDAO();
        ContenedorDAO contDAO = factoryDeDAOs.getContenedorDAO();
        plDAO.insert(p1);
        
        Contenedor c1 = new Contenedor();
        c1.setEstadoContenedor(this.estadosPuntosLimpios.get(0));
        c1.setCapacidad(200);
        c1.setPuntoLimpio(p1);
        c1.setUnidadMedida("M^3");
        c1.setPorcentajeUsoEstimado(0);
        c1.setProcentajeUso(0);
        c1.setMaterialDeAcopio(this.materialesPuntosLimpios.get(0));
        
        Contenedor c2 = new Contenedor();
        c2.setEstadoContenedor(this.estadosPuntosLimpios.get(0));
        c2.setCapacidad(200);
        c2.setPuntoLimpio(p1);
        c2.setUnidadMedida("M^3");
        c2.setPorcentajeUsoEstimado(0);
        c2.setProcentajeUso(0);
        c2.setMaterialDeAcopio(this.materialesPuntosLimpios.get(1));
        
        Contenedor c3 = new Contenedor();
        c3.setEstadoContenedor(this.estadosPuntosLimpios.get(0));
        c3.setCapacidad(200);
        c3.setPuntoLimpio(p1);
        c3.setUnidadMedida("M^3");
        c3.setPorcentajeUsoEstimado(0);
        c3.setProcentajeUso(0);
        c3.setMaterialDeAcopio(this.materialesPuntosLimpios.get(2));
        
        contDAO.insert(c1);
        contDAO.insert(c2);
        contDAO.insert(c3);
    }
    
    private void cargarMantenciones() {
        
    }
    
    private void cargarNotificaciones() {
        
    }
    
    private void cargaUsuarios() {
        String password = "12345";
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
        
        Administrador nvoAdmin;
        Usuario nvoUsuario = new Usuario();
        nvoAdmin = new Administrador();
        nvoUsuario.setNombre("Víctor");
        nvoUsuario.setApellido1("Flores");
        nvoUsuario.setEmail("victor.floress@usach.cl");
        nvoUsuario.setApellido2("Sánchez");
        nvoUsuario.setUsername("vflores");
        nvoUsuario.setRut(17565743);
        nvoUsuario.setTelefono(333333333);
        nvoUsuario.setPassword(password);
        nvoAdmin.setUsuario(nvoUsuario);
        
        
        //Hago los DAO
        
        AdministradorDAO adminDAO = factoryDeDAOs.getAdministradorDAO();
        InspectorDAO inspectDAO = factoryDeDAOs.getInspectorDAO();
        OperarioDAO OperDAO = factoryDeDAOs.getOperarioDAO();
        UsuarioDAO userDAO = factoryDeDAOs.getUsuarioDAO();
        RolDAO rolDAO = factoryDeDAOs.getRolDAO();
        Rol nvoRol = rolDAO.find("Administrador");
        if (nvoRol == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol = new Rol();
            nvoRol.setNombreRol("Administrador");
            rolDAO.insert(nvoRol);
        }
        nvoUsuario.setRol(nvoRol);
        rolDAO.insert(nvoRol);
        
        
        userDAO.insert(nvoUsuario);
        adminDAO.insert(nvoAdmin);
        
        
        Inspector nvoInspect;
        Usuario nvoUsuario2 = new Usuario();
        nvoInspect = new Inspector();
        nvoUsuario2.setNombre("Carlos");
        nvoUsuario2.setApellido1("Barrera");
        nvoUsuario2.setEmail("carlos.barrerap@usach.cl");
        nvoUsuario2.setApellido2("Pulgar");
        nvoUsuario2.setUsername("cabarrera");
        nvoUsuario2.setRut(17705318);
        nvoUsuario2.setPassword(password);
        nvoUsuario2.setTelefono(111111111);
        nvoInspect.setUsuario(nvoUsuario2);
        Rol nvoRol2 = rolDAO.find("Inspector");
        if (nvoRol2 == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol2 = new Rol();
            nvoRol2.setNombreRol("Inspector");
            rolDAO.insert(nvoRol2);
        }
        nvoUsuario2.setRol(nvoRol2);
        
        rolDAO.insert(nvoRol2);
        userDAO.insert(nvoUsuario2);
        inspectDAO.insert(nvoInspect);
        
        OperarioMantencion nvoOperario;
        Usuario nvoUsuario3 = new Usuario();
        nvoOperario = new OperarioMantencion();
        nvoUsuario3.setNombre("Armando");
        nvoUsuario3.setApellido1("Casas");
        nvoUsuario3.setEmail("armando.casas@usach.cl");
        nvoUsuario3.setApellido2("Rojas");
        nvoUsuario3.setUsername("arojas");
        nvoUsuario3.setRut(17705318);
        nvoUsuario3.setPassword(password);
        nvoUsuario3.setTelefono(222222222);
        nvoOperario.setUsuario(nvoUsuario3);
        Rol nvoRol3 = rolDAO.find("Operario");
        if (nvoRol3 == null) { //Para crear el rol en caso que no exista en la DB
            nvoRol3 = new Rol();
            nvoRol3.setNombreRol("Operario");
            rolDAO.insert(nvoRol3);
        }
        nvoUsuario3.setRol(nvoRol3);
        
        rolDAO.insert(nvoRol3);
        userDAO.insert(nvoUsuario3);
        OperDAO.insert(nvoOperario);
    }
}
