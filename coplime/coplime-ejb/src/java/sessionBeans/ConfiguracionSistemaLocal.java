/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import javax.ejb.Local;

/**
 *
 * @author Armando
 */
@Local
public interface ConfiguracionSistemaLocal {

    public void fijarIntervaloEstimacionContenedores(Integer intervalo);
    public Integer obtenerIntervaloEstimacionContenedores();
}
