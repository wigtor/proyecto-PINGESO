/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.ConfiguracionDAO;
import entities.Configuracion;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import scheduler.GeneradorProgramadoNotificacionesLocal;

/**
 *
 * @author Armando
 */
@Stateless
public class ConfiguracionSistema implements ConfiguracionSistemaLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;
    
    @EJB
    private GeneradorProgramadoNotificacionesLocal generadorProgramadoNotificaciones;
    
    /**
     * Programa el valor <i>timer_estimacion_contenedores_intervalo</i> que determina
     * cada cuánto tiempo se ejecuta la estimación automática de llenado de contenedores.
     * @param intervalo Intervalo, expresado en horas.
     */
    @Override
    public void fijarIntervaloEstimacionContenedores(Integer intervalo) {
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        ConfiguracionDAO configDAO = fabricaDAO.getConfiguracionDAO();
        Long milisegundos;
        milisegundos = new Long(intervalo*3600*1000);
        Configuracion timerEstimacionContenedores = configDAO.buscarParamExacto("timer_estimacion_contenedores_intervalo");
        timerEstimacionContenedores.setValorParam(milisegundos.toString());
        configDAO.update(timerEstimacionContenedores);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    

    @Override
    public Integer obtenerIntervaloEstimacionContenedores() {
        DAOFactory fabricaDAO = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        ConfiguracionDAO configDAO = fabricaDAO.getConfiguracionDAO();
        Integer intervalo;
        Configuracion timerEstimacionContenedores = configDAO.buscarParamExacto("timer_estimacion_contenedores_intervalo");
        intervalo = Math.round(Long.parseLong(timerEstimacionContenedores.getValorParam())/3600000);
        return intervalo;
    }

    
}
