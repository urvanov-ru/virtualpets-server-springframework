/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;

/**
 * @author fedya
 *
 */
public interface RefrigeratorDao {
    Refrigerator findById(Integer id);

    Refrigerator findFullById(Integer id);
}
