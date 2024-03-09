package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Запись справочника холодильников.
 */
@Entity
@Table(name = "refrigerator")
public class Refrigerator implements Serializable {
    
    private static final long serialVersionUID = -6335332962524762996L;

    /**
     * Первичный ключ. Новые записи в справочник холодильников добавляются
     * только скриптами liqubiase, поэтому первичный ключ не генерируется
     * ни в БД, ни в Java-коде.
     */
    @Id
    private Integer id;
    
    /**
     * Максимальный индекс еды из перечисления {@link FoodType},
     * который может храниться в этом холодильнике.
     */
    @Column(name = "max_food_type")
    private int maxFoodType;
    
    /**
     * Количество ресурсов, необходимое для строительства / улучшения
     * холодильника.
     */
    @OneToMany(mappedBy = "refrigerator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="buildingMaterial")
    private Map<BuildingMaterial, RefrigeratorCost> refrigeratorCost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMaxFoodType() {
        return maxFoodType;
    }

    public void setMaxFoodType(int maxFoodType) {
        this.maxFoodType = maxFoodType;
    }

    public Map<BuildingMaterial, RefrigeratorCost> getRefrigeratorCost() {
        return refrigeratorCost;
    }

    public void setRefrigeratorCost(
            Map<BuildingMaterial, RefrigeratorCost> refrigeratorCost) {
        this.refrigeratorCost = refrigeratorCost;
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
        Refrigerator other = (Refrigerator) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Refrigerator [id=" + id + ", maxFoodType=" + maxFoodType + "]";
    }

}
