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
     * Первичный ключ. Новые записи в справочник еды добавляются
     * только скриптами liquibase, поэтому первичный ключ
     * не генерируется ни в БД, ни в Java-коде.
     */
    @Id
    @Enumerated(EnumType.STRING)
    private FoodId id;
    
    @Column(name = "refrigeratorId")
    private int refrigeratorLevel;
    
    private int refrigeratorOrder;
    
    private float hiddenObjectsGameDropRate;

    public FoodId getId() {
        return id;
    }

    public int getRefrigeratorLevel() {
        return refrigeratorLevel;
    }

    public int getRefrigeratorOrder() {
        return refrigeratorOrder;
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
        Food other = (Food) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Food [id=" + id
                + ", refrigeratorLevel=" + refrigeratorLevel
                + ", refrigeratorOrder=" + refrigeratorOrder
                + ", hiddenObjectsGameDropRate="
                + hiddenObjectsGameDropRate
                + "]";
    }


}
