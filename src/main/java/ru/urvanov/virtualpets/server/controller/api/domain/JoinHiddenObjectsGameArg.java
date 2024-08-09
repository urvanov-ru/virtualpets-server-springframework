package ru.urvanov.virtualpets.server.controller.api.domain;

import jakarta.validation.constraints.NotNull;

public record JoinHiddenObjectsGameArg(
        @NotNull HiddenObjectsGameType hiddenObjectsGameType) {
}
