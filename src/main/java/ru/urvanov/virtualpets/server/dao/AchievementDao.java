package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Achievement;
import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;

public interface AchievementDao {
    public Achievement findById(Integer id);
    public Achievement findByCode(AchievementCode code);
    public List<Achievement> findAll();
}
