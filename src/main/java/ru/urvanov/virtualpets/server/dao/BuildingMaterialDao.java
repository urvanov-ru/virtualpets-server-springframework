package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType;

public interface BuildingMaterialDao {
    public BuildingMaterial findById(BuildingMaterialType id);

    public BuildingMaterial findByCode(BuildingMaterialType code);

    public BuildingMaterial getReference(BuildingMaterialType id);
}
