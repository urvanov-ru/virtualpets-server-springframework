package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity(name = "pet_food")
public class PetFood implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8225590371680345671L;

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
    private Integer foodCount;

    @Version
    @Column(name = "version")
    private Integer version;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * @param pet
     *            the pet to set
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * @return the food
     */
    public Food getFood() {
        return food;
    }

    /**
     * @param food the food to set
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * @return the foodCount
     */
    public Integer getFoodCount() {
        return foodCount;
    }

    /**
     * @param foodCount
     *            the foodCount to set
     */
    public void setFoodCount(Integer foodCount) {
        this.foodCount = foodCount;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }
}
