package ru.urvanov.virtualpets.server.api.domain;

import ru.urvanov.virtualpets.server.dao.domain.PetType;

public record CreatePetArg(String name, PetType petType,
        String comment) {
};
