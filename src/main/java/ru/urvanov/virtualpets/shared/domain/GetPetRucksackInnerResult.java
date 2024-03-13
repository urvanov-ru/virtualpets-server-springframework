package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.Map;

public class GetPetRucksackInnerResult implements Serializable {

    private static final long serialVersionUID = -3556329584250693246L;
    private Map<BuildingMaterialType, Integer> buildingMaterialCounts;

    public Map<BuildingMaterialType, Integer> getBuildingMaterialCounts() {
        return buildingMaterialCounts;
    }

    /**
     * @param buildingMaterialCounts the buildingMaterialCounts to set
     */
    public void setBuildingMaterialCounts(
            Map<BuildingMaterialType, Integer> buildingMaterialCounts) {
        this.buildingMaterialCounts = buildingMaterialCounts;
    }
    
}
