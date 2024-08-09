package ru.urvanov.virtualpets.server.controller.game.domain;

import jakarta.validation.constraints.NotNull;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

public record DrinkArg(@NotNull DrinkId drinkId) {};
