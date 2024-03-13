package ru.urvanov.virtualpets.shared.domain;

public record Food(
        FoodType id,
        int refrigeratorLevel,
        int refrigeratorOrder,
        int count) {}
