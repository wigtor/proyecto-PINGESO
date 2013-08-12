/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;

import entities.Inspector;

/**
 *
 * @author victor
 */
public interface InspectorDAO extends genericDAO<Inspector>{
    
    /**
     * Obtiene un inspector a través de su rut
     * @param rut El rut sin dígito verificador del inspector que se busca
     * @return El inspector buscado, null si no se encuentra
     */
    public Inspector findByRut(int rut);
    
    /**
     * Elimina de la base de datos un inspector a través de su rut.
     * @param rut El rut sin dígito verificador del inspector que se elimina
     * @return true si pudo ser borrado exitosamente, false si hubo algún problema o no existía el rut
     */
    public boolean deleteByRut(int rut);
    
    /**
     * Obtiene un inspector a través de su nombre de usuario.
     * @param username El nombre de usuario del inspector que se busca
     * @return El inspector buscado, null si no se encuentra
     */
    public Inspector findByUsername(String username);
}
