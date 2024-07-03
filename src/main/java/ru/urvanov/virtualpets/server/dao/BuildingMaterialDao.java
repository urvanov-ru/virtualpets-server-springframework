package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

public interface BuildingMaterialDao {
    Optional<BuildingMaterial> findById(BuildingMaterialId id);

    Optional<BuildingMaterial> findByCode(BuildingMaterialId code);

    BuildingMaterial getReference(BuildingMaterialId id);

    List<BuildingMaterial> findAll();
}
