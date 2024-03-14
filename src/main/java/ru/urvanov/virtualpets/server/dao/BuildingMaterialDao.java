package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType;

public interface BuildingMaterialDao {
    BuildingMaterial findById(BuildingMaterialType id);

    BuildingMaterial findByCode(BuildingMaterialType code);

    BuildingMaterial getReference(BuildingMaterialType id);

    List<BuildingMaterial> findAll();
}
