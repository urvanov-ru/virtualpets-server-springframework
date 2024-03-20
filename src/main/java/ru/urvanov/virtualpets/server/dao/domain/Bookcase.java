package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Книжный шкаф.
 */
@Entity
@Table(name="bookcase")
public class Bookcase implements Serializable {

    private static final long serialVersionUID = 6636132588014164542L;

    /**
     * Первичный ключ. Новые записи в справочнике книжных шкафов добавляются
     * только скриптами liquibase, поэтому первичные ключи не генерируются
     * ни в БД, ни в Java-коде.
     */
    @Id
    private int id;

    /**
     * Количество получаемого при постройке/ улучшении опыта
     */
    private int experience;
    
    @OneToMany(mappedBy = "bookcase", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "building_material_id")
    private Map<BuildingMaterialId, BookcaseCost> bookcaseCost;

    public int getId() {
        return id;
    }

    public int getExperience() {
        return experience;
    }

    public Map<BuildingMaterialId, BookcaseCost> getBookcaseCost() {
        return bookcaseCost;
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
        Bookcase other = (Bookcase) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Bookcase [id=" + id + "]";
    }

}
