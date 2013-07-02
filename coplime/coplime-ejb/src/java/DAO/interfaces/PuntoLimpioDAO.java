/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.PuntoLimpio;
import java.util.Collection;

/**
 *
 * @author victor
 */
public interface PuntoLimpioDAO {
    public int insertPuntoLimpio();
    public boolean deletePuntoLimpio();
    public PuntoLimpio findPuntoLimpio();
    public boolean updatePuntoLimpio();
    public Collection selectPuntoLimpioesTO();
}
