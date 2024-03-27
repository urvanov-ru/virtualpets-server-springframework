package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Запись из справочника машин с напитками.
 */
@Entity
@Table(name="machine_with_drinks")
public class MachineWithDrinks implements Serializable {

    private static final long serialVersionUID = -2181386187900603405L;

    /**
     * Перввичный ключ. Новые записи в справочник машин с напитками
     * добавляются только скриптами liquibase, поэтому первичный ключ 
     * не генерируется ни в БД, ни в Java-коде.
     */
    @Id
    private int id;
    
    /**
     * Количество получаемого при постройке/ улучшении опыта
     */
    private int experience;
    
    /**
     * Необходимое количество материалов для постройки / улучшения.
     */
    @OneToMany(mappedBy = "machineWithDrinks",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "building_material_id")
    private Map<BuildingMaterialId, MachineWithDrinksCost> machineWithDrinksCost;

    public int getId() {
        return id;
    }

    public int getExperience() {
        return experience;
    }

    public Map<BuildingMaterialId, MachineWithDrinksCost> getMachineWithDrinksCost() {
        return machineWithDrinksCost;
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
        MachineWithDrinks other = (MachineWithDrinks) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "MachineWithDrinks [id=" + id + ", machineWithDrinksCost="
                + machineWithDrinksCost + "]";
    }

    
}
