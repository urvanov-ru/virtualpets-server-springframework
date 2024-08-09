package ru.urvanov.virtualpets.server.controller.game.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecoverPasswordArg(
        @NotNull @Size(min = 1, max = 50) String login,
        @NotNull @Size(min = 3, max = 50) String email,
        @NotNull @Size(min = 1, max = 50) String version) {
};
