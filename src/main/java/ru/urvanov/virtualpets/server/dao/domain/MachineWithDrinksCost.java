package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
    private int id;
    
    /**
     * Машина с напитками.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private MachineWithDrinks machineWithDrinks;
    
    /**
     * Строительный материал.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private BuildingMaterial buildingMaterial;
    
    /**
     * Необходимое количество материала {@link #buildingMaterial} для
     * строительства / улучшения {@link #machineWithDrinks}.
     */
    private int cost;
    
    /**
     * Конструктор без параметров, необходимый JPA.
     */
    public MachineWithDrinksCost() {
        super();
    }
    
    /**
     * Конструктор с параметрами для создания экземпляров приложением,
     * например в тестах.
     * @param id {@link #id}
     * @param machineWithDrinks {@link #machineWithDrinks}
     * @param buildingMaterial {@link #buildingMaterial}
     * @param cost {@link #cost}
     */
    public MachineWithDrinksCost(int id,
            MachineWithDrinks machineWithDrinks,
            BuildingMaterial buildingMaterial, int cost) {
        super();
        this.id = id;
        this.machineWithDrinks = machineWithDrinks;
        this.buildingMaterial = buildingMaterial;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
        return id == other.id;
    }

    @Override
    public String toString() {
        return "MachineWithDrinksCost [id=" + id + ", machineWithDrinks.id="
                + machineWithDrinks.getId()
                + ", buildingMaterial.id=" + buildingMaterial.getId()
                + ", cost=" + cost + "]";
    }

    

}
