package ru.urvanov.virtualpets.server.controller.game.domain;

import jakarta.validation.constraints.NotNull;

public record JoinHiddenObjectsGameArg(
        @NotNull HiddenObjectsGameType hiddenObjectsGameType) {
}
