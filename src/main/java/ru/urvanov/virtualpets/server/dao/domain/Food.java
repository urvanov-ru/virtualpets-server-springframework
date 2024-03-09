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
    private Integer id;
    
    /**
     * Натуральный ключ. Код еды.
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "code")
    private FoodType code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FoodType getCode() {
        return code;
    }

    public void setCode(FoodType code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
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
        return code == other.code;
    }

    @Override
    public String toString() {
        return "Food [id=" + id + ", code=" + code + "]";
    }

}
