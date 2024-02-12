package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Level;

public interface LevelDao {
    Level findById(Integer id);
    
    List<Level> findAll();
}
