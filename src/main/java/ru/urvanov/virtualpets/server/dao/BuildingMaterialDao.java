/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType;

/**
 * @author fedya
 *
 */
public interface BuildingMaterialDao {
    public BuildingMaterial findById(Integer id);

    public BuildingMaterial findByCode(BuildingMaterialType iron);
}
