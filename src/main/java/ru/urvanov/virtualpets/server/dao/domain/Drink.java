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
    private Integer id;

    /**
     * Натуральный ключ. Код напитка.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private DrinkType drinkType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkType drinkType) {
        this.drinkType = drinkType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(drinkType);
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
        return drinkType == other.drinkType;
    }

    @Override
    public String toString() {
        return "Drink [id=" + id + ", drinkType=" + drinkType + "]";
    }

    
}
