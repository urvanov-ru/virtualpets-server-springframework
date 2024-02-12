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
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name="pet_drink")
public class PetDrink implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8225590371680345671L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_drink_seq")
    @SequenceGenerator(name="pet_drink_seq",
        sequenceName="pet_drink_id_seq", allocationSize=1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @Column(name = "drink_count")
    private Integer drinkCount;

    @Version
    @Column(name = "version")
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

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Integer getDrinkCount() {
        return drinkCount;
    }

    public void setDrinkCount(Integer drinkCount) {
        this.drinkCount = drinkCount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
