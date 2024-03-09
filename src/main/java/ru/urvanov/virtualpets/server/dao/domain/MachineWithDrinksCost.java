package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Запись о количестве необходимого материала для строительства
 * / улучшения  машины с напитками.
 */
@Entity
@Table(name="machine_with_drinks_cost")
public class MachineWithDrinksCost {

    /**
     * Первичный ключ.
     */
    @Id
    private Integer id;
    
    /**
     * Машина с напитками.
     */
    @ManyToOne
    @JoinColumn(name="machine_with_drinks_id")
    private MachineWithDrinks machineWithDrinks;
    
    /**
     * Строительный материал.
     */
    @ManyToOne
    @JoinColumn(name="building_material_id")
    private BuildingMaterial buildingMaterial;
    
    /**
     * Необходимое количество материала {@link #buildingMaterial} для
     * строительства / улучшения {@link #machineWithDrinks}.
     */
    @Column(name="cost")
    private Integer cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MachineWithDrinks getMachineWithDrinks() {
        return machineWithDrinks;
    }

    public void setMachineWithDrinks(MachineWithDrinks machineWithDrinks) {
        this.machineWithDrinks = machineWithDrinks;
    }

    public BuildingMaterial getBuildingMaterial() {
        return buildingMaterial;
    }

    public void setBuildingMaterial(BuildingMaterial buildingMaterial) {
        this.buildingMaterial = buildingMaterial;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingMaterial, cost, id, machineWithDrinks);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MachineWithDrinksCost other = (MachineWithDrinksCost) obj;
        return Objects.equals(buildingMaterial.getId(), other.buildingMaterial.getId())
                && Objects.equals(cost, other.cost)
                && Objects.equals(id, other.id)
                && Objects.equals(machineWithDrinks.getId(), other.machineWithDrinks.getId());
    }

    @Override
    public String toString() {
        return "MachineWithDrinksCost [id=" + id + ", machineWithDrinks.id="
                + machineWithDrinks.getId()
                + ", buildingMaterial.id=" + buildingMaterial.getId()
                + ", cost=" + cost + "]";
    }

    

}
