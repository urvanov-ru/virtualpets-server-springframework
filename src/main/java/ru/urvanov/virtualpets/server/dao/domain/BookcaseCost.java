package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Запись строки из таблицы информации о затратах на строительство или
 * улучшение книжного шкафа.
 */
@Entity
@Table(name="bookcase_cost")
public class BookcaseCost implements Serializable {

    private static final long serialVersionUID = 2619627580580783008L;

    /**
     * Первичный ключ.
     */
    @Id
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Bookcase bookcase;

    @ManyToOne(fetch = FetchType.LAZY)
    private BuildingMaterial buildingMaterial;
    
    private int cost;

    public int getId() {
        return id;
    }

    public Bookcase getBookcase() {
        return bookcase;
    }

    public BuildingMaterial getBuildingMaterial() {
        return buildingMaterial;
    }

    public int getCost() {
        return cost;
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
        BookcaseCost other = (BookcaseCost) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "BookcaseCost [id=" + id + ", bookcase.id=" + bookcase.getId()
                + ", buildingMaterial.id=" + buildingMaterial.getId()
                + ", cost=" + cost
                + "]";
    }

    
}
