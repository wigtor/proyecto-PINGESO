/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Contenedor;
import java.util.List;

/**
 *
 * @author victor
 */
public interface ContenedorDAO  extends genericDAO<Contenedor>{
    
    /**
     * Busca los contenedores pertenecientes a un punto limpio con cierto identificador.
     * @param idPtoLimpio El identificador primario del punto limpio al cual pertenecen los contenedores
     * @return Se devuelve una lista de contenedores pertenecientes al punto limpio
     */
    public List<Contenedor> findByPuntoLimpio(int idPtoLimpio);
}
