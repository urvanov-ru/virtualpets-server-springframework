/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Cloth;

/**
 * @author fedya
 *
 */
public interface ClothDao {
    public Cloth findById(Integer id);
    public Cloth getReference(Integer id);
    public Integer getCount();
}
