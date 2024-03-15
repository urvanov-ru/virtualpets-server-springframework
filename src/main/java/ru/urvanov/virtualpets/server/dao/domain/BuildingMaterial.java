package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
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
    private BuildingMaterialType id;

    @Column(name = "rucksack_order")
    private int rucksackOrder;
    
    @Column(name = "newbie_box_drop_min")
    private int newbieBoxDropMin;
    
    @Column(name = "newbie_box_drop_max")
    private int newbieBoxDropMax;
    
    @Column(name = "newbie_box_drop_rate")
    private double newbieBoxDropRate;
    
    @Column(name = "hidden_objects_game_drop_rate")
    private double hiddenObjectsGameDropRate;

    public BuildingMaterialType getId() {
        return id;
    }

    public void setId(BuildingMaterialType id) {
        this.id = id;
    }

    public int getRucksackOrder() {
        return rucksackOrder;
    }

    public void setRucksackOrder(int rucksackOrder) {
        this.rucksackOrder = rucksackOrder;
    }

    public int getNewbieBoxDropMin() {
        return newbieBoxDropMin;
    }

    public void setNewbieBoxDropMin(int newbieBoxDropMin) {
        this.newbieBoxDropMin = newbieBoxDropMin;
    }

    public int getNewbieBoxDropMax() {
        return newbieBoxDropMax;
    }

    public void setNewbieBoxDropMax(int newbieBoxDropMax) {
        this.newbieBoxDropMax = newbieBoxDropMax;
    }

    public double getNewbieBoxDropRate() {
        return newbieBoxDropRate;
    }

    public void setNewbieBoxDropRate(double newbieBoxDropRate) {
        this.newbieBoxDropRate = newbieBoxDropRate;
    }

    public double getHiddenObjectsGameDropRate() {
        return hiddenObjectsGameDropRate;
    }

    public void setHiddenObjectsGameDropRate(double hiddenObjectsGameDropRate) {
        this.hiddenObjectsGameDropRate = hiddenObjectsGameDropRate;
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
