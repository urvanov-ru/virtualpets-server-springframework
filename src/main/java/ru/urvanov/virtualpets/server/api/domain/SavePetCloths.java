package ru.urvanov.virtualpets.server.api.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SavePetCloths(
        @NotNull @Size(min = 1, max = 50) String hatId,
        @NotNull @Size(min = 1, max = 50) String clothId,
        @NotNull @Size(min = 1, max = 50) String bowId) {
};
