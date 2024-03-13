package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

/**
 * Запись о количестве еды у питомца.
 */
@Entity(name = "pet_food")
public class PetFood implements Serializable {

    private static final long serialVersionUID = -8225590371680345671L;

    /**
     * Первичный ключ. Генерируемый.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_food_seq")
    @SequenceGenerator(name="pet_food_seq",
        sequenceName="pet_food_id_seq", allocationSize=1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @Column(name = "food_count")
    private int foodCount;

    /**
     * Для оптимистичной блокировки.
     */
    @Version
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(food, foodCount, pet);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PetFood other = (PetFood) obj;
        return Objects.equals(food.getId(), other.food.getId())
                && Objects.equals(foodCount, other.foodCount)
                && Objects.equals(pet.getId(), other.pet.getId());
    }

    @Override
    public String toString() {
        return "PetFood [id=" + id
                + ", pet.id=" + pet.getId()
                + ", food.id=" + food.getId()
                + ", foodCount=" + foodCount
                + ", version=" + version + "]";
    }
    
}
