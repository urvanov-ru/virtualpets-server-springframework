package ru.urvanov.virtualpets.server.controller.game.domain;

import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

public record Drink(
        DrinkId id,
        int machineWithDrinksLevel,
        int machineWithDrinksOrder,
        int count) {}
