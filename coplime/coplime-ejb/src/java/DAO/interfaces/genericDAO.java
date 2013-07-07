/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.interfaces;


import java.util.Collection;

/**
 *
 * @author victor
 */
public interface genericDAO<T> {
    // Interface that all DAOs must support

    public boolean insert(T nvaEntity);
    public boolean delete(T nvaEntity);
    public boolean delete(int id);
    public T find(int id);
    public T update(T nvaEntity);
    public Collection<T> findAll();
    public Collection<T> findRange(int inicio, int fin);
    public int count();
}
