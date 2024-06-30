package ru.urvanov.virtualpets.server.api.domain;

import ru.urvanov.virtualpets.server.dao.domain.FoodId;

public record Food(
        FoodId id,
        int refrigeratorLevel,
        int refrigeratorOrder,
        int count) {}
