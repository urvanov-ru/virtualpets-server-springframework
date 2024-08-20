package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

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
public class Cloth {

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
    private ClothType clothType;

    private int wardrobeOrder;
    
    private float hiddenObjectsGameDropRate;
    
    /**
     * Конструктор по умолчанию требуется JPA для создания объекта.
     */
    public Cloth() {
        super();
    }
    
    /**
     * Конструктор для создания экземпляров в самом приложении
     * виртуальных питомцев, например в тестах.
     * @param id {@link #id}
     * @param clothType {@link #clothType}
     * @param wardrobeOrder {@link #wardrobeOrer}
     * @param hiddenObjectsGameDropRate {@link
     * #hiddenObjectsGameDropRate}
     */
    public Cloth(String id, ClothType clothType, int wardrobeOrder,
            float hiddenObjectsGameDropRate) {
        super();
        this.id = id;
        this.clothType = clothType;
        this.wardrobeOrder = wardrobeOrder;
        this.hiddenObjectsGameDropRate = hiddenObjectsGameDropRate;
    }

    public String getId() {
        return id;
    }

    public ClothType getClothType() {
        return clothType;
    }

    public int getWardrobeOrder() {
        return wardrobeOrder;
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
