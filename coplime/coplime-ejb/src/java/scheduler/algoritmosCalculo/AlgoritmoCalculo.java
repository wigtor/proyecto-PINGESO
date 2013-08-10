/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.algoritmosCalculo;

import entities.HistoricoContenedor;
import entities.PuntoLimpio;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Armando
 */
public interface AlgoritmoCalculo {
    Date estimar(LinkedList<HistoricoContenedor> historialContenedor, Integer porcentajeBuscado);
}
