package ru.urvanov.virtualpets.server.controller.game.domain;

import jakarta.validation.constraints.Min;

public record SelectPetArg(@Min(1) int petId) {
};
