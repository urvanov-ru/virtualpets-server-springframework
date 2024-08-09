package ru.urvanov.virtualpets.server.controller.game.domain;

import ru.urvanov.virtualpets.server.dao.domain.FoodId;

public record Food(
        FoodId id,
        int refrigeratorLevel,
        int refrigeratorOrder,
        int count) {}
