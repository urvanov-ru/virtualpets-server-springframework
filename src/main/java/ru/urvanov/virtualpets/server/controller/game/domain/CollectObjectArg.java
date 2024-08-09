package ru.urvanov.virtualpets.server.controller.game.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public record CollectObjectArg(
        @NotNull @Min(0) Integer objectId
        ) {};
