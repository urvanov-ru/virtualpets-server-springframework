package ru.urvanov.virtualpets.server.controller.api.domain;

import ru.urvanov.virtualpets.server.dao.domain.PetType;

public record PetInfo(Integer id, String name, PetType petType) {
};
