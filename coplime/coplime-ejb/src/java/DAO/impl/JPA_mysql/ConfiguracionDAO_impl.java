/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.impl.JPA_mysql;

import DAO.interfaces.ConfiguracionDAO;
import entities.Configuracion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Armando
 */
public class ConfiguracionDAO_impl extends genericDAO_impl<Configuracion> implements ConfiguracionDAO {

    
    public ConfiguracionDAO_impl(EntityManager em) {
        super(Configuracion.class);
        this.em = em;
    }

    //TODO Lista es reemplazable por varibale única de tipo Configuración?
    @Override
    public Configuracion buscarParamExacto(String parametro) {
        List<Configuracion> res;
        Query q = this.em.createNamedQuery("Configuracion.findByParam");
        q.setParameter("idParam", parametro);
        res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        else {
            return res.get(0);
        }
    }

    @Override
    public Configuracion buscarParamAprox(String cadenaBusq) {
        List<Configuracion> res;
        Query q = this.em.createNamedQuery("Configuracion.findByParamLike");
        q.setParameter("cadenaBusq", cadenaBusq);
        res = q.getResultList();
        if (res.isEmpty()){
            return null;
        } else {
            return res.get(0);
        }
    }

    @Override
    public List<Configuracion> buscarTodosParamsAprox(String cadenaBusq) {
        List<Configuracion> res;
        Query q = this.em.createNamedQuery("Configuracion.findByParamLike");
        q.setParameter("cadenaBusq", cadenaBusq);
        res = q.getResultList();
        if (res.isEmpty()){
            return null;
        } else {
            return res;
        }
    }   
}
