package ru.urvanov.virtualpets.server.api.domain;

public record Food(
        FoodType id,
        int refrigeratorLevel,
        int refrigeratorOrder,
        int count) {}
