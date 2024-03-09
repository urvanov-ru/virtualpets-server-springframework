package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="bookcase_id")
    private Bookcase bookcase;

    @ManyToOne
    @JoinColumn(name="building_material_id")
    private BuildingMaterial buildingMaterial;
    
    @Column(name="cost")
    private Integer cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bookcase getBookcase() {
        return bookcase;
    }

    public void setBookcase(Bookcase bookcase) {
        this.bookcase = bookcase;
    }

    public BuildingMaterial getBuildingMaterial() {
        return buildingMaterial;
    }

    public void setBuildingMaterial(BuildingMaterial buildingMaterial) {
        this.buildingMaterial = buildingMaterial;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookcase.getId(), buildingMaterial.getId(), cost);
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
        return Objects.equals(bookcase.getId(), other.bookcase.getId())
                && Objects.equals(buildingMaterial.getId(),
                        other.buildingMaterial.getId())
                && Objects.equals(cost, other.cost);
    }

    @Override
    public String toString() {
        return "BookcaseCost [id=" + id + ", bookcase.id=" + bookcase.getId()
                + ", buildingMaterial.id=" + buildingMaterial.getId()
                + ", cost=" + cost
                + "]";
    }

    
}
