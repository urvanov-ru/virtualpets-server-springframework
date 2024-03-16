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
 * Запись из справочника еды.
 */
@Entity
@Table(name = "food")
public class Food implements Serializable {

    private static final long serialVersionUID = 8791181701061581183L;

    /**
     * Первичный ключ. Новые записи в справочник еды добавляются только
     * скриптами liquibase, поэтому первичный ключ не генерируется ни в БД,
     * ни в Java-коде.
     */
    @Id
    @Enumerated(EnumType.STRING)
    private FoodType id;
    
    @Column(name = "refrigerator_id")
    private int refrigeratorLevel;
    
    @Column(name = "refrigerator_order")
    private int refrigeratorOrder;
    
    @Column(name = "hidden_objects_game_drop_rate")
    private float hiddenObjectsGameDropRate;

    public FoodType getId() {
        return id;
    }

    public void setId(FoodType id) {
        this.id = id;
    }

    public int getRefrigeratorLevel() {
        return refrigeratorLevel;
    }

    public void setRefrigeratorLevel(int refrigeratorLevel) {
        this.refrigeratorLevel = refrigeratorLevel;
    }

    public int getRefrigeratorOrder() {
        return refrigeratorOrder;
    }

    public void setRefrigeratorOrder(int refrigeratorOrder) {
        this.refrigeratorOrder = refrigeratorOrder;
    }

    public float getHiddenObjectsGameDropRate() {
        return hiddenObjectsGameDropRate;
    }

    public void setHiddenObjectsGameDropRate(float hiddenObjectsGameDropRate) {
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
        Food other = (Food) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Food [id=" + id + ", refrigeratorLevel=" + refrigeratorLevel
                + ", refrigeratorOrder=" + refrigeratorOrder
                + ", hiddenObjectsGameDropRate=" + hiddenObjectsGameDropRate
                + "]";
    }


}
