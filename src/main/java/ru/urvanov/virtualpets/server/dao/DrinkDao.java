package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;

public interface DrinkDao {
    public Drink findById(Integer id);
    public Drink getReference(Integer id);
    public Drink findByCode(DrinkType code);
}
