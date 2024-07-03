package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

public interface DrinkDao {

    Optional<Drink> findById(DrinkId id);

    Drink getReference(DrinkId id);

    List<Drink> findAllOrderByMachineWithDrinksLevelAndMachineWithDrinksOrder();
}
