/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.OperarioMantencion;

/**
 *
 * @author victor
 */
public interface OperarioDAO extends genericDAO<OperarioMantencion>{
    
    /**
     * Obtiene un operario a través de su rut
     * @param rut El rut sin dígito verificador del operario que se busca
     * @return El operario buscado, null si no se encuentra
     */
    public OperarioMantencion findByRut(int rut);
    
    /**
     * Elimina de la base de datos un operario a través de su rut.
     * @param rut El rut sin dígito verificador del operario que se elimina
     * @return true si pudo ser borrado exitosamente, false si hubo algún problema o no existía el rut
     */
    public boolean deleteByRut(int rut);
    
    /**
     * Obtiene un operario a través de su nombre de usuario.
     * @param username El nombre de usuario del operario que se busca
     * @return El operario buscado, null si no se encuentra
     */
    public OperarioMantencion findByUsername(String username);
}
