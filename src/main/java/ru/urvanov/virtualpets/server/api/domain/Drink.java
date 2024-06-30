package ru.urvanov.virtualpets.server.api.domain;

public record Drink(
        DrinkType id,
        int machineWithDrinksLevel,
        int machineWithDrinksOrder,
        int count) {}
