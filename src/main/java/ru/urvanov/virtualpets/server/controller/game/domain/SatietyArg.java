package ru.urvanov.virtualpets.server.controller.game.domain;

import jakarta.validation.constraints.NotNull;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;

public record SatietyArg(@NotNull FoodId foodId) {
};
