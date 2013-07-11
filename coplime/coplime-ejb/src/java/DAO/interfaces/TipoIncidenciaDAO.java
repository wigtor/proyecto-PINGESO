/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.TipoIncidencia;

/**
 *
 * @author victor
 */
public interface TipoIncidenciaDAO extends genericDAO<TipoIncidencia>{
    // Interface that all AdministradorDAOs must support

    public TipoIncidencia find(String tipoName);
}
