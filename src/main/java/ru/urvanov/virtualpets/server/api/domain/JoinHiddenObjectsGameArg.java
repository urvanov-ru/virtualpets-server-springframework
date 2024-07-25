package ru.urvanov.virtualpets.server.api.domain;

import jakarta.validation.constraints.NotNull;

public record JoinHiddenObjectsGameArg(
        @NotNull HiddenObjectsGameType hiddenObjectsGameType) {
}
