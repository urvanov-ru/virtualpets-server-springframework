package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.Level;

public interface LevelDao {

    Optional<Level> findById(Integer id);

    List<Level> findAll();
}
