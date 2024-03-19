package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
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
    private Integer id;
    
    /**
     * Количество получаемого при постройке/ улучшении опыта
     */
    @Column
    private int experience;
    
    /**
     * Необходимое количество материалов для постройки / улучшения.
     */
    @OneToMany(mappedBy = "machineWithDrinks", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "building_material_id")
    private Map<BuildingMaterialId, MachineWithDrinksCost> machineWithDrinksCost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Map<BuildingMaterialId, MachineWithDrinksCost> getMachineWithDrinksCost() {
        return machineWithDrinksCost;
    }

    public void setMachineWithDrinksCost(
            Map<BuildingMaterialId, MachineWithDrinksCost> machineWithDrinksCost) {
        this.machineWithDrinksCost = machineWithDrinksCost;
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
