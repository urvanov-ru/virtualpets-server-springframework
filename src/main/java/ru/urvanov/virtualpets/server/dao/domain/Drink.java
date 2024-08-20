package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

/**
 * Запись из справочника напитков.
 */
@Entity
@Table(name = "drink")
@SqlResultSetMapping(
        name = "Drink.defaultMapping",
        entities = {
                @EntityResult(entityClass = Drink.class,
                        fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "machineWithDrinksLevel", column = "machine_with_drinks_id"),
                                @FieldResult(name = "machineWithDrinksOrder", column = "machine_with_drinks_order"),
                                @FieldResult(name = "hiddenObjectsGameDropRate", column = "hidden_objects_game_drop_rate")
                        })
        }
)
@NamedNativeQuery(
        name = "Drink.findAllOrderByMachineWithDrinksLevelAndMachineWithDrinksOrder",
        query = """
                SELECT
                    d.id,
                    d.machine_with_drinks_id,
                    d.machine_with_drinks_order,
                    d.hidden_objects_game_drop_rate
                FROM drink d
                ORDER BY d.machine_with_drinks_id,
                    d.machine_with_drinks_order
        """,
        resultSetMapping = "Drink.defaultMapping"
        
        )
public class Drink {

    /**
     * Первичный ключ. Новые записи в справочник напитков добавляются
     * только из скриптов liquibase, поэтому первичный ключ
     * не генерируется ни в БД, ни в Java-коде.
     */
    @Id
    @Enumerated(EnumType.STRING)
    private DrinkId id;

    @Column(name = "machine_with_drinks_id")
    private int machineWithDrinksLevel;
    
    private int machineWithDrinksOrder;
    
    private float hiddenObjectsGameDropRate;

    /**
     * Конструктор по умолчанию требуется JPA для создания объекта.
     */
    public Drink() {
        super();
    }

    /**
     * Конструктор для создания экземпляров в самом приложении
     * виртуальных питомцев, например в тестах.
     * @param id {@link #id Первичный ключ}
     * @param machineWithDrinksLevel {@link #machineWithDrinksLevel}
     * @param machineWithDrinksOrder {@link #machineWithDrinksOrder}
     * @param hiddenObjectsGameDropRate {@link
     * #hiddenObjectsGameDropRate}
     */
    public Drink(DrinkId id, int machineWithDrinksLevel,
            int machineWithDrinksOrder,
            float hiddenObjectsGameDropRate) {
        super();
        this.id = id;
        this.machineWithDrinksLevel = machineWithDrinksLevel;
        this.machineWithDrinksOrder = machineWithDrinksOrder;
        this.hiddenObjectsGameDropRate = hiddenObjectsGameDropRate;
    }


    public DrinkId getId() {
        return id;
    }

    public int getMachineWithDrinksLevel() {
        return machineWithDrinksLevel;
    }

    public int getMachineWithDrinksOrder() {
        return machineWithDrinksOrder;
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
