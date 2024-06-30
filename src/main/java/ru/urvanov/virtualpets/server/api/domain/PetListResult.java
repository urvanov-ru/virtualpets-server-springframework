package ru.urvanov.virtualpets.server.api.domain;

import java.util.List;

public record PetListResult(List<PetInfo> petsInfo) {
};
