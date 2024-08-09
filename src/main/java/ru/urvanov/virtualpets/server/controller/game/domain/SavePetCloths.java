package ru.urvanov.virtualpets.server.controller.game.domain;

import jakarta.validation.constraints.Size;

public record SavePetCloths(
        @Size(min = 1, max = 50) String hatId,
        @Size(min = 1, max = 50) String clothId,
        @Size(min = 1, max = 50) String bowId) {
};
