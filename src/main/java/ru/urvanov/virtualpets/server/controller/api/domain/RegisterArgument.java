package ru.urvanov.virtualpets.server.controller.api.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterArgument(
        @NotNull @Size(min = 3, max = 50) String login,
        @NotNull @Size(min = 3, max = 50) String name,
        @NotNull @Size(min = 3, max = 50) String password,
        @NotNull @Size(min = 3, max = 50) String email,
        @NotNull @Size(min = 1, max = 50) String version) {
};
