/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Comuna;
import entities.Contenedor;
import entities.Estado;
import entities.Material;
import entities.PuntoLimpio;
import entities.UnidadMedida;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudPuntoLimpioLocal {

    /**
     * Busca un punto limpio en la fuente de datos utilizando su N°.
     * @param num El N° del punto limpio
     * @return El objeto "PuntoLimpio", null si no se encuentra
     */
    public PuntoLimpio getPuntoLimpioByNum(Integer num);

    /**
     * Busca todos los puntos limpios registrados en el sistema.
     * @return Una colección de objetos "PuntoLimpio", puede ser vacía, pero no null
     */
    public Collection<PuntoLimpio> getAllPuntosLimpios();

    public Integer agregarPuntoLimpio(Integer numeroDadoPorCliente, String nombre, Integer idComuna, String direccion, Calendar fechaProxRev, Integer idEstadoIni, Integer numInspEnc) throws Exception;
    
    public void agregarContenedor( Integer numPuntoLimpio, Integer idMaterial, Integer idEstadoIni, int llenadoIni, int capacidad, Integer idUnidadMedida) throws Exception;
    
    public void editarPuntoLimpio(Integer idPtoLimpio, String nombre, Integer idComuna, String direccion, Calendar fechaProxRev, Integer idEstadoIni, Integer numInspEnc) throws Exception;

    /**
     * Elimina un punto limpio de la fuente de datos, sus mantenciones, revisiones y solicitudes de mantención.
     * @param id El N° del punto limpio
     * @return true si pudo eliminarse, false si hubo un error
     */
    public void eliminarPuntoLimpioByNum(Integer num) throws Exception;

    /**
     * Busca todos los estados en que puede encotnrarse un punto limpio.
     * @return Una colección de objetos "Estado", puede ser vacía, pero no null
     */
    public Collection<Estado> getAllEstadosPuntoLimpio();

    /**
     * Busca todas las comunas registradas en el sistema.
     * @return Una colección de objetos "Comuna", puede ser vacía, pero no null
     */
    public Collection<Comuna> getAllComunas();

    /**
     * Busca todos los materiales que pueden tener los contenedores de los puntos limpios.
     * @return Una colección de objetos "Material", puede ser vacía, pero no null
     */
    public Collection<Material> getAllMateriales();

    /**
     * Busca todas las unidades de medida para los materiales registradas en el sistema.
     * @return Una colección de objetos "UnidadMedida", puede ser vacía, pero no null
     */
    public Collection<UnidadMedida> getAllUnidadesMedida();

    /**
     * Busca un contenedor por su identificador.
     * @param id El identificador primario del contenedor a buscar
     * @return Un objeto "Contenedor", null si no se encuentra
     */
    public Contenedor getContenedor(Integer id);

    /**
     * Busca todos los contenedores de un punto limpio
     * @param idPtoLimpio El identificador del punto limpio del que se quieren buscar sus contenedores
     * @return Una 
     */
    public Collection<Contenedor> getContenedoresByPuntoLimpio(Integer idPtoLimpio);

    /**
     * Cambia el estado y el porcentaje de llenado de un contenedor, además genera un nuevo historico de contenedor.
     * @param idContenedor El id del contenedor que se desea editar
     * @param idEstadoContenedor El id del nuevo estado del contenedor
     * @param llenadoContenedor El nuevo porcentaje de llenado que tendrá el contenedor
     * @return true si pudo realizarse la actualización, false si hubo un error
     */
    public boolean cambiarEstadoContenedor(Integer idContenedor, Integer idEstadoContenedor, int llenadoContenedor);

    /**
     * Indica si ya existe un punto limpio con cierto número registrado en el sistema.
     * @param numero El número del punto limpio que se desea comprobar
     * @return Devuelve true si el punto limpio ya existe, falso en caso contrario
     */
    public boolean existeNumPuntoLimpio(Integer numero);

    /**
     * Indica si ya existe un punto limpio con cierto nombre registrado en el sistema.
     * @param nombre El nombre del punto limpio que se desea comprobar
     * @return Devuelve true si el punto limpio ya existe, falso en caso contrario
     */
    public boolean existeNombrePuntoLimpio(String nombre);
    
    public boolean existeNombrePuntoLimpioExcepto(String nombre, PuntoLimpio p);

    public void eliminarContenedorById(Integer num) throws Exception;

    public void editarContenedor(Integer numContenedor, Integer idMaterial, Integer idEstadoIni, int llenadoIni, int capacidad, Integer idUnidadMedida) throws Exception;
}
