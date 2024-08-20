package ru.urvanov.virtualpets.server.dao.domain;

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
public class BookcaseCost {

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

    /**
     * Конструктор без параметров, необходимый JPA.
     */
    public BookcaseCost() {

    }

    /**
     * Конструктор с параметрами, используемый приложением для
     * создания экземпляров, например в тестах.
     * @param id {@link #id}
     * @param bookcase {@link #bookcase}
     * @param buildingMaterial {@link #buildingMaterial}
     * @param cost {@link #cost}
     */
    public BookcaseCost(int id, Bookcase bookcase,
            BuildingMaterial buildingMaterial, int cost) {
        super();
        this.id = id;
        this.bookcase = bookcase;
        this.buildingMaterial = buildingMaterial;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
