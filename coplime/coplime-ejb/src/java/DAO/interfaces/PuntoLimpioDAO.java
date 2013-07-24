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
public interface PuntoLimpioDAO extends genericDAO<PuntoLimpio>{
    public PuntoLimpio find(String puntoLimpioName);
    public PuntoLimpio findByNum(int num);
    public boolean deleteByNum(int num);
}
