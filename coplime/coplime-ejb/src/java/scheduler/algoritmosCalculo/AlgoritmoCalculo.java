/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.algoritmosCalculo;

import entities.HistoricoContenedor;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Armando
 */
public interface AlgoritmoCalculo {
    
    /**
     * Estima la fecha para cuando un contenedor llegue a cierto porcentaje de llenado según su lista de histórico.
     * @param historialContenedor Una colección de históricos con que será realizada la estimación
     * @param porcentajeBuscado El porcentaje que espera tener de llenado en la estimación entregada
     * @return La fecha en que se estima el contenedor obtendrá el porcentaje de llenado entregado
     */
    Date estimar(Collection<HistoricoContenedor> historialContenedor, Integer porcentajeBuscado);
}
