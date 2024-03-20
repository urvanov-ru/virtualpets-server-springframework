package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Запись о количестве строительного материала, необходимого для
 * постройки / улучшения холодильника.
 */
@Entity
@Table(name="refrigerator_cost")
public class RefrigeratorCost implements Serializable {

    private static final long serialVersionUID = -4597700957457308301L;

    /**
     * Первичный ключ.
     */
    @Id
    private int id;
    
    @ManyToOne
    @JoinColumn(name="refrigerator_id")
    private Refrigerator refrigerator;
    
    @ManyToOne
    @JoinColumn(name="building_material_id")
    private BuildingMaterial buildingMaterial;
    
    @Column(name="cost")
    private int cost;
    
    
    public int getId() {
        return id;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
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
        RefrigeratorCost other = (RefrigeratorCost) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "RefrigeratorCost [id=" + id
                + ", refrigerator=" + refrigerator
                + ", buildingMaterial=" + buildingMaterial
                + ", cost=" + cost
                + "]";
    }

}
