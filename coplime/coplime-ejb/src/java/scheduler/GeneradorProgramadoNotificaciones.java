/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author Armando
 */
@Stateless
public class GeneradorProgramadoNotificaciones implements GeneradorProgramadoNotificacionesLocal {

    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "01")
    @Override
    //NOTA: Los parámetros de periodicidad de la tarea programada deben ser configurables externamente
    //a través de la aplicación.
    public void myTimer() {
        System.out.println("Timer event: " + new Date());
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
