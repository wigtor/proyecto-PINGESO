/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface configuracionInicialLocal {
    public void primeraEjecucion();

    void cargarConfiguracion();

    public void primeraEjecucionTesting();
}
