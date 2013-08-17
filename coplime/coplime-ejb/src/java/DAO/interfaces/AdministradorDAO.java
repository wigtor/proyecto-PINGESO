/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Administrador;

/**
 *
 * @author victor
 */
public interface AdministradorDAO extends genericDAO<Administrador>{
    
    /**
     * Obtiene un administrador a través de su rut
     * @param rut El rut sin dígito verificador del administrador que se busca
     * @return El administrador buscado, si no se encuentra se retorna null
     */
    public Administrador findByRut(int rut);
    
    /**
     * Elimina de la base de datos un administrador a través de su rut.
     * @param rut El rut sin dígito verificador del administrador que se elimina
     * @return true si pudo ser borrado exitosamente, false si hubo algún problema o no existía el rut
     */
    public boolean deleteByRut(int rut);
    
    public Administrador findByUsername(String username);
}
