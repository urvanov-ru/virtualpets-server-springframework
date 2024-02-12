/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * @author fedya
 *
 */
public class OpenBoxNewbieResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4319115555878317347L;
    private Map<BuildingMaterialType, Integer> buildingMaterials;
    private int index;

    /**
     * @return the buildingMaterials
     */
    public Map<BuildingMaterialType, Integer> getBuildingMaterials() {
        return buildingMaterials;
    }

    /**
     * @param buildingMaterials the buildingMaterials to set
     */
    public void setBuildingMaterials(
            Map<BuildingMaterialType, Integer> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
    
}
