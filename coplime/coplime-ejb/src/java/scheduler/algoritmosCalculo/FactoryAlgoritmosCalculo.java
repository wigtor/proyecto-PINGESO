/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.algoritmosCalculo;

import scheduler.algoritmosCalculo.implementaciones.AlgoritmoPromediosPonderados;

/**
 *
 * @author Armando
 */
public class FactoryAlgoritmosCalculo {
    
    /**
     * Indica que se va a utilizar un método de promedio de pendientes para realizar las estimaciones
     */
    public static final int PROMEDIO_PENDIENTES = 1;
    
    /**
     * Indica que se va a utilizar un método bayesiano para realizar las estimaciones
     */
    public static final int METODO_BAYESIANO = 2;
    
    /**
     * Obtiene una instancia de objeto con la interface "AlgoritmoCalculo" según la entrada que se especifique.
     * @param tipoAlgoritmo Indica el tipo de algoritmo de la clase que será instanciada, debe ser alguna constante definida en esta Factory.
     * @return 
     */
    public static AlgoritmoCalculo getAlgoritmoCalculo(int tipoAlgoritmo){
        switch (tipoAlgoritmo){
            case PROMEDIO_PENDIENTES:
                return new AlgoritmoPromediosPonderados();
            case METODO_BAYESIANO:
                return new AlgoritmoPromediosPonderados(); //Por ahora que use el mismo
            default:
                return null; //Algoritmo desconocido
        }
    }
    
    
}
