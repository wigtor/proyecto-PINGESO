/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import javax.ejb.Local;

/**
 *
 * @author Armando
 */
@Local
public interface GeneradorProgramadoNotificacionesLocal {
    
    /**
     * Método que es ejecutado periodicamente para realizar las estimaciones de 
     * fecha de revisión de los puntos limpios.
     */
    public void myTimer();
}
