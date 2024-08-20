package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class RefrigeratorCost {

    /**
     * Первичный ключ.
     */
    @Id
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Refrigerator refrigerator;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_material_id")
    private BuildingMaterial buildingMaterial;
    
    private int cost;
    
    /**
     * Конструктор без параметров, необходимый JPA.
     */
    public RefrigeratorCost() {
        super();
    }

    /**
     * Конструктор с параметрами, используемый приложением, например
     * тестами.
     * @param id {@link #id Первичный ключ}
     * @param refrigerator {@link #refrigerator}
     * @param buildingMaterial {@link #buildingMaterial}
     * @param cost {@link #cost Необходимое количество материала}
     */
    public RefrigeratorCost(int id, Refrigerator refrigerator,
            BuildingMaterial buildingMaterial, int cost) {
        super();
        this.id = id;
        this.refrigerator = refrigerator;
        this.buildingMaterial = buildingMaterial;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
