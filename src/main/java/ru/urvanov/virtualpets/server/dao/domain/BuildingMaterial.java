package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Запись из справочника материалов для строительства.
 */
@Entity
@Table(name = "building_material")
public class BuildingMaterial implements Serializable {

    private static final long serialVersionUID = -6611026384958159106L;

    /**
     * Первичный ключ. Новые записи в справочник материалов для строительства
     * добавляются скриптами liquibase, первичный ключ не генерируется ни в БД,
     * ни в Java-коде.
     */
    @Id
    @Enumerated(EnumType.STRING)
    private BuildingMaterialId id;

    private int rucksackOrder;
    
    private int newbieBoxDropMin;
    
    private int newbieBoxDropMax;
    
    private float newbieBoxDropRate;
    
    private float hiddenObjectsGameDropRate;

    public BuildingMaterialId getId() {
        return id;
    }

    public int getRucksackOrder() {
        return rucksackOrder;
    }

    public int getNewbieBoxDropMin() {
        return newbieBoxDropMin;
    }

    public int getNewbieBoxDropMax() {
        return newbieBoxDropMax;
    }

    public float getNewbieBoxDropRate() {
        return newbieBoxDropRate;
    }

    public float getHiddenObjectsGameDropRate() {
        return hiddenObjectsGameDropRate;
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
        BuildingMaterial other = (BuildingMaterial) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "BuildingMaterial [id=" + id + ", rucksackOrder=" + rucksackOrder
                + ", newbieBoxDropMin=" + newbieBoxDropMin
                + ", newbieBoxDropMax=" + newbieBoxDropMax
                + ", newbieBoxDropRate=" + newbieBoxDropRate
                + ", hiddenObjectsGameDropRate=" + hiddenObjectsGameDropRate
                + "]";
    }

}
