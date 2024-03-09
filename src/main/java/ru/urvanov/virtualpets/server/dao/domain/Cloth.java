package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Integer id;
    
    /**
     * Натуральный ключ. Код одежды.
     */
    @Enumerated
    @Column(name="cloth_type")
    private ClothType clothType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClothType getClothType() {
        return clothType;
    }

    public void setClothType(ClothType clothType) {
        this.clothType = clothType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clothType);
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
        return clothType == other.clothType;
    }

    @Override
    public String toString() {
        return "Cloth [id=" + id + ", clothType=" + clothType + "]";
    }


}
