package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Запись справочника холодильников.
 */
@Entity
@Table(name = "refrigerator")
public class Refrigerator implements Serializable {
    
    private static final long serialVersionUID = -6335332962524762996L;

    /**
     * Первичный ключ. Новые записи в справочник холодильников
     * добавляются только скриптами liqubiase, поэтому первичный ключ
     * не генерируется ни в БД, ни в Java-коде.
     */
    @Id
    private int id;
    
    /**
     * Количество получаемого при постройке/ улучшении опыта
     */
    private int experience;
    
    /**
     * Количество ресурсов, необходимое для строительства / улучшения
     * холодильника.
     */
    @OneToMany(mappedBy = "refrigerator", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "building_material_id")
    private Map<BuildingMaterialId, RefrigeratorCost> refrigeratorCost;

    public int getId() {
        return id;
    }

    public int getExperience() {
        return experience;
    }

    public Map<BuildingMaterialId, RefrigeratorCost> getRefrigeratorCost() {
        return refrigeratorCost;
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
        return "Refrigerator [id=" + id + "]";
    }

}
