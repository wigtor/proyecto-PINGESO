/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Configuracion;
import java.util.List;

/**
 *
 * @author Armando
 */
public interface ConfiguracionDAO extends genericDAO<Configuracion> {
    
    //Se declara este método en la interfaz porque find de genericDAO sólo recibe
    //un int como argumento de entrada.
    
    /**
     * Devuelve una configuración parámetro + valor a partir de una cadena de búsqueda.
     * @param parametro El nombre del parámetro a buscar.
     * @return La configuración buscada.<br><i>Null</i> si no encuentra resultados.
     *
     */
    public Configuracion buscarParamExacto(String parametro);
    public Configuracion buscarParamAprox(String cadenaBusq);
    public List<Configuracion> buscarTodosParamsAprox(String cadenaBusq);
}
