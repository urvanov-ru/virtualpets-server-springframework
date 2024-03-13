package ru.urvanov.virtualpets.shared.domain;

public record Drink(
        DrinkType id,
        int machineWithDrinksLevel,
        int machineWithDrinksOrder,
        int count) {}
