/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import DAO.DAOFactory;
import DAO.interfaces.TipoIncidenciaDAO;
import entities.TipoIncidencia;
import java.util.Collection;
import java.util.LinkedList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class AvisosIncidencia implements AvisosIncidenciaLocal {
    @PersistenceContext(unitName = "coplime-ejbPU")
    private EntityManager em;

    @Override
    public Collection<TipoIncidencia> getTiposAvisos() {
        //Hago los DAO
        DAOFactory factoryDeDAOs = DAOFactory.getDAOFactory(DAOFactory.JPA, em);
        TipoIncidenciaDAO tiDAO = factoryDeDAOs.getTipoIncidenciaDAO();
        
        Collection<TipoIncidencia> resultado;
        resultado = tiDAO.findAllVisibles();
        if (resultado == null) {
            resultado = new LinkedList();
        }
        return resultado;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }
    
}
