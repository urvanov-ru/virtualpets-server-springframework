package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.Map;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

public record OpenBoxNewbieResult(int index,
        Map<BuildingMaterialId, Integer> buildingMaterials) {
};