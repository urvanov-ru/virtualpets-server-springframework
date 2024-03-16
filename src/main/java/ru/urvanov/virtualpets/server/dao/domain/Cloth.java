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
 * Запись из справочника одежды.
 */
@Entity
@Table(name="cloth")
public class Cloth implements Serializable {
    
    private static final long serialVersionUID = 6431503399036752134L;

    /**
     * Первичный ключ. Новые записи в справочник одежды добавляются только
     * с помощью скриптов liquibase, поэтому первичный ключ не генерируется ни
     * в БД, ни в Java-коде.
     */
    @Id
    private String id;
    
    /**
     * Тип одежды.
     */
    @Enumerated(EnumType.STRING)
    @Column(name="cloth_type")
    private ClothType clothType;

    @Column(name = "wardrobe_order")
    private int wardrobeOrder;
    
    @Column(name = "hidden_objects_game_drop_rate")
    private float hiddenObjectsGameDropRate;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClothType getClothType() {
        return clothType;
    }

    public void setClothType(ClothType clothType) {
        this.clothType = clothType;
    }
    
    public int getWardrobeOrder() {
        return wardrobeOrder;
    }

    public void setWardrobeOrder(int wardrobeOrder) {
        this.wardrobeOrder = wardrobeOrder;
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
        Cloth other = (Cloth) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Cloth [id=" + id + ", clothType=" + clothType
                + ", wardrobeOrder=" + wardrobeOrder
                + ", hiddenObjectsGameDropRate=" + hiddenObjectsGameDropRate
                + "]";
    }


}
