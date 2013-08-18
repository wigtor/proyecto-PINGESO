/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.AdministradorDAO;
import DAO.interfaces.ComunaDAO;
import DAO.interfaces.ContenedorDAO;
import DAO.interfaces.EstadoDAO;
import DAO.interfaces.HistoricoContenedorDAO;
import DAO.interfaces.InspectorDAO;
import DAO.interfaces.MaterialDAO;
import DAO.interfaces.NotificacionDAO;
import DAO.interfaces.OperarioDAO;
import DAO.interfaces.PuntoLimpioDAO;
import DAO.interfaces.RolDAO;
import DAO.interfaces.TipoIncidenciaDAO;
import DAO.interfaces.UnidadMedidaDAO;
import DAO.interfaces.UsuarioDAO;
import entities.Administrador;
import entities.Comuna;
import entities.Contenedor;
import entities.Estado;
import entities.HistoricoContenedor;
import entities.Inspector;
import entities.Material;
import entities.NotificacionDeUsuario;
import entities.OperarioMantencion;
import entities.PuntoLimpio;
import entities.Rol;
import entities.TipoIncidencia;
import entities.UnidadMedida;
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
    List<Inspector> inspectores;
    List<PuntoLimpio> puntosLimpios;
    List<Estado> estadosPuntosLimpios;
    List<Material> materialesPuntosLimpios;
    List<Comuna> listaComunas;
    List<UnidadMedida> listaUnidadesMedida;
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void primeraEjecicion() {
        factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        cargaUsuarios();
        cargarEstadosPuntosLimpios();
        cargarTipoIncidencias();
        cargarComunas();
        cargarUnidadesMedida();
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
    
    private void cargarComunas() {
        ComunaDAO comDAO = factoryDeDAOs.getComunaDAO();
        this.listaComunas = new LinkedList();
        Comuna temp;
        String[] comunas = {"San Bernardo", "Santiago", "San Miguel", "La Cisterna", 
        "Puente Alto", "La Pintana", "El Bosque", "Maipú", "Pedro Aguirre Cerda",
        "San Ramón", "Estación Central", "Pudahuel", "Providencia", "Las Condes", 
        "Recoleta", "Independencia", "Renca", "La Florida"};
        
        for(String str : comunas) {
            temp = new Comuna();
            temp.setNombre(str);
            comDAO.insert(temp);
            this.listaComunas.add(temp);
        }
    }
    
    private void cargarUnidadesMedida() {
        UnidadMedidaDAO uniDAO = factoryDeDAOs.getUnidadMedidaDAO();
        this.listaUnidadesMedida = new LinkedList();
        UnidadMedida temp;
        String[] unidades = {"M^3", "Kg", "Lts"};
        
        for(String str : unidades) {
            temp = new UnidadMedida();
            temp.setNombreUnidad(str);
            uniDAO.insert(temp);
            this.listaUnidadesMedida.add(temp);
        }
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
        PuntoLimpio p1 = new PuntoLimpio("Plaza San Bernardo", "Plaza de armas", 1);
        p1.setComuna(this.listaComunas.get(0));
        p1.setEstadoGlobal(this.estadosPuntosLimpios.get(0));
        p1.setFechaProxRevision(Calendar.getInstance());
        p1.setInspectorEncargado(this.inspectores.get(0));
        
        PuntoLimpioDAO plDAO = factoryDeDAOs.getPuntoLimpioDAO();
        ContenedorDAO contDAO = factoryDeDAOs.getContenedorDAO();
        HistoricoContenedorDAO histDAO = factoryDeDAOs.getHistoricoContenedorDAO();
        plDAO.insert(p1);
        
        Contenedor c1 = new Contenedor();
        c1.setEstadoContenedor(this.estadosPuntosLimpios.get(0));
        c1.setCapacidad(200);
        c1.setPuntoLimpio(p1);
        c1.setUnidadMedida(listaUnidadesMedida.get(0));
        c1.setPorcentajeUsoEstimado(0);
        c1.setProcentajeUso(0);
        c1.setMaterialDeAcopio(this.materialesPuntosLimpios.get(0));
        HistoricoContenedor hist1 = new HistoricoContenedor();
        hist1.setPorcentajeLlenado(0);
        hist1.setFechaHora(Calendar.getInstance());
        hist1.setContenedor(c1);
        c1.getHistorialContenedor().add(hist1);
        
        Contenedor c2 = new Contenedor();
        c2.setEstadoContenedor(this.estadosPuntosLimpios.get(0));
        c2.setCapacidad(200);
        c2.setPuntoLimpio(p1);
        c2.setUnidadMedida(listaUnidadesMedida.get(0));
        c2.setPorcentajeUsoEstimado(0);
        c2.setProcentajeUso(0);
        c2.setMaterialDeAcopio(this.materialesPuntosLimpios.get(1));
        HistoricoContenedor hist2 = new HistoricoContenedor();
        hist2.setPorcentajeLlenado(0);
        hist2.setFechaHora(Calendar.getInstance());
        hist2.setContenedor(c2);
        c2.getHistorialContenedor().add(hist2);
        
        Contenedor c3 = new Contenedor();
        c3.setEstadoContenedor(this.estadosPuntosLimpios.get(0));
        c3.setCapacidad(200);
        c3.setPuntoLimpio(p1);
        c3.setUnidadMedida(listaUnidadesMedida.get(0));
        c3.setPorcentajeUsoEstimado(0);
        c3.setProcentajeUso(0);
        c3.setMaterialDeAcopio(this.materialesPuntosLimpios.get(2));
        HistoricoContenedor hist3 = new HistoricoContenedor();
        hist3.setPorcentajeLlenado(0);
        hist3.setFechaHora(Calendar.getInstance());
        hist3.setContenedor(c3);
        c3.getHistorialContenedor().add(hist3);
        
        p1.getContenedores().add(c1);
        p1.getContenedores().add(c2);
        p1.getContenedores().add(c3);
        contDAO.insert(c1);
        contDAO.insert(c2);
        contDAO.insert(c3);
        histDAO.insert(hist1);
        histDAO.insert(hist2);
        histDAO.insert(hist3);
        
        this.puntosLimpios = new LinkedList();
        this.puntosLimpios.add(p1);
        
        
        
        
        p1 = new PuntoLimpio("Homecenter Plaza vespucio", "Av. Américo Vespucio #4335", 2);
        p1.setComuna(this.listaComunas.get(2));
        p1.setEstadoGlobal(this.estadosPuntosLimpios.get(0));
        p1.setFechaProxRevision(Calendar.getInstance());
        p1.setInspectorEncargado(this.inspectores.get(0));
        
        plDAO.insert(p1);
        
        c1 = new Contenedor();
        c1.setEstadoContenedor(this.estadosPuntosLimpios.get(0));
        c1.setCapacidad(500);
        c1.setPuntoLimpio(p1);
        c1.setUnidadMedida(listaUnidadesMedida.get(0));
        c1.setPorcentajeUsoEstimado(0);
        c1.setProcentajeUso(0);
        c1.setMaterialDeAcopio(this.materialesPuntosLimpios.get(3));
        hist1 = new HistoricoContenedor();
        hist1.setPorcentajeLlenado(0);
        hist1.setFechaHora(Calendar.getInstance());
        hist1.setContenedor(c1);
        c1.getHistorialContenedor().add(hist1);
        
        c2 = new Contenedor();
        c2.setEstadoContenedor(this.estadosPuntosLimpios.get(0));
        c2.setCapacidad(500);
        c2.setPuntoLimpio(p1);
        c2.setUnidadMedida(listaUnidadesMedida.get(0));
        c2.setPorcentajeUsoEstimado(0);
        c2.setProcentajeUso(0);
        c2.setMaterialDeAcopio(this.materialesPuntosLimpios.get(4));
        hist2 = new HistoricoContenedor();
        hist2.setPorcentajeLlenado(0);
        hist2.setFechaHora(Calendar.getInstance());
        hist2.setContenedor(c2);
        c2.getHistorialContenedor().add(hist2);
        
        p1.getContenedores().add(c1);
        p1.getContenedores().add(c2);
        contDAO.insert(c1);
        contDAO.insert(c2);
        histDAO.insert(hist1);
        histDAO.insert(hist2);
        
        this.puntosLimpios.add(p1);
    }
    
    private void cargarMantenciones() {
        
    }
    
    private void cargarNotificaciones() {
        NotificacionDeUsuario notif = new NotificacionDeUsuario();
        notif.setComentario("El punto limpio está con todos los desechos botados por perros");
        notif.setEmailContacto("victor.floress@usach.cl");
        notif.setImagenAdjunta("C:\\glassfish3\\jdk7\\uploads_coplime\\img_notif_1374215183342.jpg");
        notif.setFechaHora(Calendar.getInstance());
        notif.setResuelto(false);
        notif.setRevisado(false);
        notif.setTipoImagen("image/jpeg");
        notif.setTipoIncidencia(tiposIncidencias.get(1));
        notif.setPuntoLimpio(puntosLimpios.get(0));
        
        NotificacionDAO notifDAO = factoryDeDAOs.getNotificacionDAO();
        notifDAO.insert(notif);
        
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
        
        this.inspectores = new LinkedList();
        this.inspectores.add(nvoInspect);
        
        OperarioMantencion nvoOperario;
        Usuario nvoUsuario3 = new Usuario();
        nvoOperario = new OperarioMantencion();
        nvoUsuario3.setNombre("Armando");
        nvoUsuario3.setApellido1("Casas");
        nvoUsuario3.setEmail("armando.casas@usach.cl");
        nvoUsuario3.setApellido2("Rojas");
        nvoUsuario3.setUsername("arojas");
        nvoUsuario3.setRut(17723598);
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
