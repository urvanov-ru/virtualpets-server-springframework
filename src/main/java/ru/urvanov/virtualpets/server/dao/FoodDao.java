package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;

public interface FoodDao {
    Food findById(Integer id);
    Food findByCode(FoodType code);
    Food getReference(FoodType id);
    List<Food> findAllOrderByRefrigeratorLevelAndRefrigeratorOrder();
}
