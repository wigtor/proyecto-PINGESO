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
    public List<Contenedor> findByPuntoLimpio(int idPtoLimpio);
}
