package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "drink")
public class Drink implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5407671889327194327L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private DrinkType drinkType;

    @Version
    @Column(name = "version")
    private Integer version;

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

    public Integer getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return id == null ? -1 : id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Drink) {
            Drink otherDrink = (Drink) other;
            if (id == null && otherDrink.getId() == null || id != null
                    && id.equals(otherDrink.getId()))
                return true;
        }
        return false;
    }
}
