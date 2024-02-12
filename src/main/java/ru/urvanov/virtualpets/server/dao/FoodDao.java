/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;

/**
 * @author fedya
 *
 */
public interface FoodDao {
    public Food findById(Integer id);
    public Food findByCode(FoodType code);
}
