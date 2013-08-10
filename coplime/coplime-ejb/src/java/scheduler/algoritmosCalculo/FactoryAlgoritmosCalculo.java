/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.algoritmosCalculo;

import scheduler.algoritmosCalculo.implementaciones.AlgoritmoCalculoLineal;

/**
 *
 * @author Armando
 */
public class FactoryAlgoritmosCalculo {
    
    public static final int REGRESION_LINEAL = 1;
    public static final int METODO_BAYESIANO = 2;
    
    public static AlgoritmoCalculo getAlgoritmoCalculo(int tipoAlgoritmo){
        switch (tipoAlgoritmo){
            case REGRESION_LINEAL:
                return new AlgoritmoCalculoLineal();
            default:
                return null;
        }
    }
    
    
}
