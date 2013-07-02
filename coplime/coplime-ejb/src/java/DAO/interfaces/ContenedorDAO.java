/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Contenedor;
import java.util.Collection;

/**
 *
 * @author victor
 */
public interface ContenedorDAO {
    public int insertContenedor();
    public boolean deleteContenedor();
    public Contenedor findContenedor();
    public boolean updateContenedor();
    public Collection selectContenedoresTO();
}
