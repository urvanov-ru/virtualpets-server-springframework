package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;

public interface DrinkDao {
    public Drink findById(DrinkType id);
    public Drink getReference(Integer id);
    public Drink findByCode(DrinkType code);
    public Drink getReference(DrinkType id);
}
