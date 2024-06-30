package ru.urvanov.virtualpets.server.api.domain;

import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

public record Drink(
        DrinkId id,
        int machineWithDrinksLevel,
        int machineWithDrinksOrder,
        int count) {}
