/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author fedya
 *
 */
public class RoomBuildMenuCosts implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7317824338376286772L;
    private List<Map<BuildingMaterialType, Integer>> refrigeratorCosts;
    private List<Map<BuildingMaterialType, Integer>> machineWithDrinksCosts;
    private List<Map<BuildingMaterialType, Integer>> bookcaseCosts;
    /**
     * @return the refrigeratorCosts
     */
    public List<Map<BuildingMaterialType, Integer>> getRefrigeratorCosts() {
        return refrigeratorCosts;
    }
    /**
     * @param refrigeratorCosts the refrigeratorCosts to set
     */
    public void setRefrigeratorCosts(
            List<Map<BuildingMaterialType, Integer>> refrigeratorCosts) {
        this.refrigeratorCosts = refrigeratorCosts;
    }
    
    public List<Map<BuildingMaterialType, Integer>> getMachineWithDrinksCosts() {
        return machineWithDrinksCosts;
    }
    public void setMachineWithDrinksCosts(
            List<Map<BuildingMaterialType, Integer>> machineWithDrinksCosts) {
        this.machineWithDrinksCosts = machineWithDrinksCosts;
    }
    /**
     * @return the bookcaseCosts
     */
    public List<Map<BuildingMaterialType, Integer>> getBookcaseCosts() {
        return bookcaseCosts;
    }
    /**
     * @param bookcaseCosts the bookcaseCosts to set
     */
    public void setBookcaseCosts(
            List<Map<BuildingMaterialType, Integer>> bookcaseCosts) {
        this.bookcaseCosts = bookcaseCosts;
    }

}
