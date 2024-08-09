package ru.urvanov.virtualpets.server.controller.game.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.urvanov.virtualpets.server.dao.domain.PetType;

public record CreatePetArg(
        @NotNull @Size(min = 3, max = 50) String name,
        @NotNull PetType petType,
        @Size(min = 1, max = 50) String comment) {
};
