/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;


import java.util.Collection;

/**
 * Interface que todos los DAO deben soportar, es común a todos ellos.
 * @author victor
 */
public interface genericDAO<T> {
    
    /**
     * Realiza una inserción de una entity en la fuente de datos.
     * @param nvaEntity La entidad que desea ser persistida en la fuente de datos
     * @return true si la inserción fue correcta, false si hubo algún error
     */
    public boolean insert(T nvaEntity);
    
    /**
     * Realiza un borrado de una entity en la fuente de datos.
     * @param nvaEntity La entidad que desea ser eliminada de la fuente de datos
     * @return true si la eliminación fue correcta, false si hubo algún error
     */
    public boolean delete(T nvaEntity);
    
    /**
     * Realiza un borrado de una entity en la fuente de datos, pero utilizando su id.
     * @param id El identificador primario de la entidad que desea ser eliminada de la fuente de datos
     * @return true si la eliminación fue correcta, false si hubo algún error
     */
    public boolean delete(int id);
    
    /**
     * Busca una entity en la fuente de datos utilizando su id.
     * @param id El identificador primario de la entity que se está buscando
     * @return La entity encontrada, null si no se encontró
     */
    public T find(int id);
    
    /**
     * Realiza una actualización de la entity en la fuente de datos.
     * @param nvaEntity La entity que desea actualizarse en la fuente de datos
     * @return La misma entity
     */
    public T update(T nvaEntity);
    
    /**
     * Obtiene todas las instancias de una entity class que se encuentren en la fuente de datos.
     * @return Una colección de objetos, puede ser vacía, pero nunca null
     */
    public Collection<T> findAll();
    
    /**
     * Obtiene las instancias de una entity class que se encuentre en la fuente de datos, pero por rangos
     * @param inicio Índice de inicio de la búsqueda
     * @param fin Índice del final de la búsqueda
     * @return Una colección de objetos, puede ser vacía, pero nunca null
     */
    public Collection<T> findRange(int inicio, int fin);
    
    /**
     * Cuenta cuantas instancias existen en la fuente de datos de cierta entity
     * @return La cantidad de instancias
     */
    public int count();
}
