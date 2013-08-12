/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Comuna;
import entities.Contenedor;
import entities.Estado;
import entities.Inspector;
import entities.Material;
import entities.PuntoLimpio;
import entities.UnidadMedida;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface CrudPuntoLimpioLocal {

    /**
     * Elimina un punto limpio de la fuente de datos, sus mantenciones, revisiones y solicitudes de mantención.
     * @param id El N° del punto limpio
     * @return true si pudo eliminarse, false si hubo un error
     */
    public boolean eliminarPuntoLimpio(Integer id);

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

    public Integer agregarPuntoLimpio(String nombre, Integer numeroDadoPorCliente, Integer idComuna, String direccion, Calendar fechaProxRev, Integer idEstadoIni, Integer numInspEnc);
    
    public boolean agregarContenedor( Integer numPuntoLimpio, Integer idMaterial, Integer idEstadoIni, int llenadoIni, int capacidad, Integer idUnidadMedida);
    
    public void editarPuntoLimpio(Integer idPtoLimpio, String nombre, Integer idComuna, String direccion, Calendar fechaProxRev, Integer estadoIni, Integer numInspEnc);

    public boolean eliminarPuntoLimpioByNum(Integer num);

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

    public Contenedor getContenedor(Integer id);

    public List<Contenedor> getContenedoresByPuntoLimpio(Integer idPtoLimpio);

    public boolean cambiarEstadoContenedor(Integer idContenedor, Integer idEstadoContenedor, int llenadoContenedor);
}
