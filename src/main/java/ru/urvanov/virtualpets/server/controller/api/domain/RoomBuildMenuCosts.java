package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;
import java.util.Map;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

public class RoomBuildMenuCosts {
    private List<Map<BuildingMaterialId, Integer>> refrigeratorCosts;
    private List<Map<BuildingMaterialId, Integer>> machineWithDrinksCosts;
    private List<Map<BuildingMaterialId, Integer>> bookcaseCosts;

    public List<Map<BuildingMaterialId, Integer>> getRefrigeratorCosts() {
        return refrigeratorCosts;
    }

    public void setRefrigeratorCosts(
            List<Map<BuildingMaterialId, Integer>> refrigeratorCosts) {
        this.refrigeratorCosts = refrigeratorCosts;
    }

    public List<Map<BuildingMaterialId, Integer>> getMachineWithDrinksCosts() {
        return machineWithDrinksCosts;
    }

    public void setMachineWithDrinksCosts(
            List<Map<BuildingMaterialId, Integer>> machineWithDrinksCosts) {
        this.machineWithDrinksCosts = machineWithDrinksCosts;
    }

    public List<Map<BuildingMaterialId, Integer>> getBookcaseCosts() {
        return bookcaseCosts;
    }

    public void setBookcaseCosts(
            List<Map<BuildingMaterialId, Integer>> bookcaseCosts) {
        this.bookcaseCosts = bookcaseCosts;
    }

}
