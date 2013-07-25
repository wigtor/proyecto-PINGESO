/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Comuna;
import entities.Estado;
import entities.Inspector;
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

    public boolean eliminarPuntoLimpio(Integer id);

    public PuntoLimpio getPuntoLimpioByNum(Integer num);

    public Collection<PuntoLimpio> getAllPuntosLimpios();

    public Integer agregarPuntoLimpio(String nombre, Integer numeroDadoPorCliente, Integer idComuna, String direccion, Calendar fechaProxRev, Integer idEstadoIni, Integer numInspEnc);
    
    public boolean agregarContenedor( Integer numPuntoLimpio, Integer idMaterial, Integer idEstadoIni, int llenadoIni, int capacidad, Integer idUnidadMedida);
    
    public void editarPuntoLimpio(Integer idPtoLimpio, String nombre, Integer idComuna, String direccion, Calendar fechaProxRev, Integer estadoIni, Integer numInspEnc);

    public boolean eliminarPuntoLimpioByNum(Integer num);

    public Collection<Inspector> getAllInspectores();

    public Collection<Estado> getAllEstadosPuntoLimpio();

    public Collection<Comuna> getAllComunas();

    public Collection<Material> getAllMateriales();

    public Collection<UnidadMedida> getAllUnidadesMedida();
    
}
