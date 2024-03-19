package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;

public interface FoodDao {
    Food findById(FoodId id);
    Food getReference(FoodId id);
    List<Food> findAllOrderByRefrigeratorLevelAndRefrigeratorOrder();
}
