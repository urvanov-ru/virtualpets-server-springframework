package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

public interface BuildingMaterialDao {
    BuildingMaterial findById(BuildingMaterialId id);

    BuildingMaterial findByCode(BuildingMaterialId code);

    BuildingMaterial getReference(BuildingMaterialId id);

    List<BuildingMaterial> findAll();
}
