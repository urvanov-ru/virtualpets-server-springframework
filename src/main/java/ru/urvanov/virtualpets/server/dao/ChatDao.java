/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Chat;

/**
 * @author fedya
 *
 */
public interface ChatDao {
    public Chat findById(Integer id);
    public List<Chat> findLast(Integer count, Integer userId);
    public void save(Chat chat);
    public List<Chat> findFromId(Integer fromId, Integer userId);
}
