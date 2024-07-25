package ru.urvanov.virtualpets.server.api.domain;

import jakarta.validation.constraints.NotNull;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

public record DrinkArg(@NotNull DrinkId drinkId) {};
