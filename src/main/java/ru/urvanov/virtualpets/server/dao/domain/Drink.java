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
 * Запись из справочника напитков.
 */
@Entity
@Table(name = "drink")
public class Drink implements Serializable {

    private static final long serialVersionUID = -5407671889327194327L;

    /**
     * Первичный ключ. Новые записи в справочник напитков добавляются только
     * из скриптов liquibase, поэтому первичный ключ не генерируется ни в БД,
     * ни в Java-коде.
     */
    @Id
    @Enumerated(EnumType.STRING)
    private DrinkType id;

    @Column(name = "machine_with_drinks_id")
    private int machineWithDrinksLevel;
    
    @Column(name = "machine_with_drinks_order")
    private int machineWithDrinksOrder;
    
    @Column(name = "hidden_objects_game_drop_rate")
    private float hiddenObjectsGameDropRate;

    public DrinkType getId() {
        return id;
    }

    public int getMachineWithDrinksLevel() {
        return machineWithDrinksLevel;
    }

    public void setMachineWithDrinksLevel(int machineWithDrinksLevel) {
        this.machineWithDrinksLevel = machineWithDrinksLevel;
    }

    public int getMachineWithDrinksOrder() {
        return machineWithDrinksOrder;
    }

    public void setMachineWithDrinksOrder(int machineWithDrinksOrder) {
        this.machineWithDrinksOrder = machineWithDrinksOrder;
    }

    public float getHiddenObjectsGameDropRate() {
        return hiddenObjectsGameDropRate;
    }

    public void setHiddenObjectsGameDropRate(float hiddenObjectsGameDropRate) {
        this.hiddenObjectsGameDropRate = hiddenObjectsGameDropRate;
    }

    public void setId(DrinkType id) {
        this.id = id;
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
        Drink other = (Drink) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Drink [id=" + id + ", machineWithDrinksLevel="
                + machineWithDrinksLevel + ", machineWithDrinksOrder="
                + machineWithDrinksOrder + ", hiddenObjectsGameDropRate="
                + hiddenObjectsGameDropRate + "]";
    }
}
