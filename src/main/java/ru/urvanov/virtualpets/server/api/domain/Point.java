package ru.urvanov.virtualpets.server.api.domain;

import jakarta.validation.constraints.NotNull;

public record Point(@NotNull int x, @NotNull int y) {
};
