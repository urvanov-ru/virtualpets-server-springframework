package ru.urvanov.virtualpets.server.controller.api.domain;

import jakarta.validation.constraints.NotNull;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;

public record SatietyArg(@NotNull FoodId foodId) {
};
