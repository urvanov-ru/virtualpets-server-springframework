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
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="refrigerator_id")
    private Refrigerator refrigerator;
    
    @ManyToOne
    @JoinColumn(name="building_material_id")
    private BuildingMaterial buildingMaterial;
    
    @Column(name="cost")
    private Integer cost;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
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
        return Objects.hash(buildingMaterial.getId(), cost,
                refrigerator.getId());
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
        return Objects.equals(buildingMaterial.getId(),
                other.buildingMaterial.getId())
                && Objects.equals(cost, other.cost)
                && Objects.equals(refrigerator, other.refrigerator);
    }

    @Override
    public String toString() {
        return "RefrigeratorCost [id=" + id
                + ", refrigerator.id=" + refrigerator.getId()
                + ", buildingMaterial.id=" + buildingMaterial.getId()
                + ", cost=" + cost + "]";
    }

    
}
