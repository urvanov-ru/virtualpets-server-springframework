package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.Chat;

interface ChatDao {
    Optional<Chat> findById(Integer id);

    List<Chat> findLast(Integer count, Integer userId);

    void save(Chat chat);

    List<Chat> findFromId(Integer fromId, Integer userId);
}
