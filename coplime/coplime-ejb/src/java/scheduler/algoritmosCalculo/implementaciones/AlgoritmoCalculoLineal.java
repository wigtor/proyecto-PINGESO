/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.algoritmosCalculo.implementaciones;

import entities.HistoricoContenedor;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import scheduler.algoritmosCalculo.AlgoritmoCalculo;

/**
 *
 * @author Armando
 */
public class AlgoritmoCalculoLineal implements AlgoritmoCalculo {
    
    @Override
    public Date estimar(Collection<HistoricoContenedor> historialContenedorOri, Integer porcentajeBuscado) {
        LinkedList<HistoricoContenedor> historialContenedor;
        if (historialContenedorOri.getClass().isAssignableFrom(LinkedList.class)) {
            System.out.println("Era una LinkedList, casteando...");
            historialContenedor = (LinkedList<HistoricoContenedor>)historialContenedorOri;
        }
        else {
            historialContenedor = new LinkedList<>(historialContenedorOri);
        }
        
        Double pendiente = obtenerPendiente(historialContenedor);
        Calendar fechaEstimada;
        double diasEstimados;
        long diasEstimadosInt;
        
        diasEstimados = (porcentajeBuscado - historialContenedor.getLast().getPorcentajeLlenado())/pendiente;
        diasEstimados = Math.ceil(diasEstimados);
        diasEstimadosInt = Math.round(diasEstimados);
        fechaEstimada = historialContenedor.getLast().getFechaHora();
        fechaEstimada.add(Calendar.DATE, (int) diasEstimadosInt);
        return null;
    }
    
    /*
     * Función obtenerPendiente: Genera la pendiente a aplicar para estimar el día de revisión.
     */
    
     private double obtenerPendiente(LinkedList<HistoricoContenedor> historialContenedor){
        LinkedList<Double> pendientesIndividuales = new LinkedList<>();
        LinkedList<Integer> puntosPendiente = new LinkedList<>();
        double pendienteFinal=0.0;
        int cantidadPendientes = 0;
        //Se procede a construir las pendientes, hasta un máximo de cinco pendientes
        /*
         * Ponderaciones:
         * Cinco pendientes: 0.35, 0.3, 0.2, 0.1, 0.05
         * Cuatro pendientes: 0.4, 0.3, 0.2, 0.1
         * Tres pendientes: 0.5, 0.3, 0.2
         * Dos pendientes: 0.6, 0.4
         * Una pendiente: 1
         */
        ListIterator iter = historialContenedor.listIterator(historialContenedor.size());
        HistoricoContenedor hTemp;
        while((iter.hasPrevious())&&(cantidadPendientes<5)){
            hTemp = (HistoricoContenedor)iter.previous();
            puntosPendiente.add(hTemp.getPorcentajeLlenado());
        }
        return pendienteFinal;
    }
     
     /*
     * Función estimacion: retorna verdadero si un contenedor debe revisarse el día de hoy.
     */
    
    
}
