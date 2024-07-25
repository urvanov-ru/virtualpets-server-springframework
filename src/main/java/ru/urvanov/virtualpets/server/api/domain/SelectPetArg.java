package ru.urvanov.virtualpets.server.api.domain;

import jakarta.validation.constraints.Min;

public record SelectPetArg(@Min(1) int petId) {
};
