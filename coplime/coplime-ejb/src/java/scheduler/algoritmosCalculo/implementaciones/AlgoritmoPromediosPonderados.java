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
public class AlgoritmoPromediosPonderados implements AlgoritmoCalculo {
    
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
        
        /*
         * Failsafe: Si el contenedor por alguna razón no tuviera ningún punto histórico
         * (algo poco probable pues por defecto se crea uno) la estimación arroja como fecha
         * de revisión el día ANTERIOR A HOY. El generador programado de notificaciones interpreta
         * este resultado.
         */
        
        if (historialContenedor.isEmpty()==true){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -1);
            return cal.getTime();
        }
        // NOTA: ¿Debemos incluir el siguiente código en un else o se entiende que no se ejecutará
        // si el if de arriba se cumple? (Según buenas prácticas de programación).
        
        //TODO resolver duda.
        Double pendiente;
        pendiente = obtenerPendiente(historialContenedor);
        Calendar fechaEstimada;
        double diasEstimados;
        long diasEstimadosInt;
        diasEstimados = (porcentajeBuscado - historialContenedor.getLast().getPorcentajeLlenado())/pendiente;
        diasEstimados = Math.ceil(diasEstimados);
        diasEstimadosInt = Math.round(diasEstimados);
        fechaEstimada = historialContenedor.getLast().getFechaHora();
        fechaEstimada.add(Calendar.DATE, (int) diasEstimadosInt);
        
        return fechaEstimada.getTime();
    }
    
    /*
     * Función obtenerPendiente: Genera la pendiente a aplicar para estimar el día de revisión.
     */
    
     private double obtenerPendiente(LinkedList<HistoricoContenedor> historialContenedor){
        LinkedList<Double> pendientesIndividuales = new LinkedList<>();
        LinkedList<Integer> puntosPendiente = new LinkedList<>();
        double pendienteFinal=0.0,deltaLlenado;
        int cantidadPendientes = 0;
        Calendar fechaInicial,fechaFinal=null;
        boolean detener;
        //Se procede a construir las pendientes, hasta un máximo de cinco pendientes
        ListIterator iter = historialContenedor.listIterator(historialContenedor.size());
        HistoricoContenedor hTemp;
        while((iter.hasPrevious())&&(cantidadPendientes<5)){
            hTemp = (HistoricoContenedor)iter.previous();
            puntosPendiente.add(hTemp.getPorcentajeLlenado());
            //Si es el primer punto, tomamos su fecha asociada. Es la final, ya que estamos
            //recorriendo el historial en el sentido inverso al paso del tiempo.
            if(fechaFinal==null){
                fechaFinal = hTemp.getFechaHora();
            }
            //Verificamos que hayan al menos dos puntos en la lista de puntos antes de comparar
            if(!(puntosPendiente.size()<2)){
                /*
                 * Comparamos el último punto añadido con el anterior a fin de verificar que ambos puntos
                 * pertenezca a un mismo ciclo de llenado. Para eso, el porcentaje de llenado del punto recién
                 * agregado debe ser MENOR al punto que le antecede.
                 */
                if(!(puntosPendiente.getLast()<puntosPendiente.get(puntosPendiente.size()-2))){
                    /*
                     * En este caso el último punto agregado tiene un porcentaje mayor al penúltimo y
                     * por lo tanto, corresponde a un ciclo de vaciado o al extremo superior de otro ciclo
                     * de llenado. Por tanto corresponde eliminar este punto de la pendiente.
                     */
                    puntosPendiente.removeLast();
                    /*
                     * Se procede a calcular el promedio de la pendiente con los puntos
                     * y se añade el resultado a pendientesIndividuales
                     */
                    deltaLlenado = puntosPendiente.getFirst() - puntosPendiente.getLast();
                    fechaInicial = ((HistoricoContenedor)iter.next()).getFechaHora();
                    pendientesIndividuales.add(deltaLlenado/fechaInicial.compareTo(fechaFinal));
                    //Limpieza de puntos de la lista puntosPendientes
                    puntosPendiente.clear();
                    cantidadPendientes++;
                    fechaFinal=null;
                    /*
                     * Se procede a descartar todos los puntos del ciclo de vaciado, para ello,
                     * los puntos se comparan sucesivamente hasta volver a encontrar un nuevo ciclo
                     * de llenado, donde el último punto comparado tiene un porcentaje de llenado
                     * menor al penútimo.
                     */
                    hTemp=(HistoricoContenedor)iter.previous();
                    /*
                     * El ciclo while nos asegura que hay un punto previo antes de aplicar la evaluación del if,
                     * por eso la condición del if no se aplica directamente al ciclo while.
                     */
                    detener=false;
                    while(iter.hasPrevious()&&(detener==false)){
                        /* Si el porcentaje de llenado del punto anterior a hTemp es mayor o igual al de hTemp,
                         * entonces retrocedemos en el historial.
                         */
                        if(((HistoricoContenedor)iter.previous()).getPorcentajeLlenado()>= hTemp.getPorcentajeLlenado()){
                            hTemp = (HistoricoContenedor)iter;
                        } else {
                            //Devolvemos el iterador al punto "inicial" de nuestro nuevo ciclo de llenado
                            iter.next();
                            iter.next();
                            detener = true;
                        }
                    }
                }
            }
        }//Fin ciclo de elaboración de pendientes individuales
            
        /* Cálculo pendiente final
             * Dependiendo de la cantidad de pendientes obtenidas, se aplicarán las ponderaciones:
             * 
             * Ponderaciones:
             * Cinco pendientes: 0.35, 0.3, 0.2, 0.1, 0.05
             * Cuatro pendientes: 0.4, 0.3, 0.2, 0.1
             * Tres pendientes: 0.5, 0.3, 0.2
             * Dos pendientes: 0.6, 0.4
             * Una pendiente: 1
             */
            
            switch(pendientesIndividuales.size()){
                case 1:
                    pendienteFinal = pendientesIndividuales.getFirst();
                    break;
                
                case 2:
                    pendienteFinal = pendientesIndividuales.get(0)*0.6 + pendientesIndividuales.get(1)*0.4;
                    break;
                
                case 3:
                    pendienteFinal = pendientesIndividuales.get(0)*0.5 + pendientesIndividuales.get(1)*0.3 + pendientesIndividuales.get(2)*0.2;
                    break;
                    
                case 4:
                    pendienteFinal = pendientesIndividuales.get(0)*0.4 + pendientesIndividuales.get(1)*0.3 + pendientesIndividuales.get(2)*0.2+ pendientesIndividuales.get(3)*0.1;
                    break;
                    
                case 5:
                    pendienteFinal = pendientesIndividuales.get(0)*0.35 + pendientesIndividuales.get(1)*0.3 + pendientesIndividuales.get(2)*0.2 + pendientesIndividuales.get(3)*0.1 + pendientesIndividuales.get(4)*0.05;
                    break;
            }
        
        
        return pendienteFinal;
    }
}
