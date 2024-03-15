package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;

public interface DrinkDao {
    Drink findById(DrinkType id);
    Drink getReference(DrinkType id);
    List<Drink> findAllOrderByMachineWithDrinksLevelAndMachineWithDrinksOrder();
}
