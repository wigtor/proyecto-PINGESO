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
    
    public void myTimer();
}
