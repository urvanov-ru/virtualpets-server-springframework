package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @ManyToOne
    private MachineWithDrinks machineWithDrinks;
    
    /**
     * Строительный материал.
     */
    @ManyToOne
    private BuildingMaterial buildingMaterial;
    
    /**
     * Необходимое количество материала {@link #buildingMaterial} для
     * строительства / улучшения {@link #machineWithDrinks}.
     */
    @Column(name="cost")
    private int cost;

    public int getId() {
        return id;
    }

    public MachineWithDrinks getMachineWithDrinks() {
        return machineWithDrinks;
    }

    public BuildingMaterial getBuildingMaterial() {
        return buildingMaterial;
    }

    public int getCost() {
        return cost;
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
