package ru.urvanov.virtualpets.server.api.domain;

import java.util.Map;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

public record GetPetRucksackInnerResult(
        Map<BuildingMaterialId, Integer> buildingMaterialCounts) {
};
