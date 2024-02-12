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
@Table(name = "food")
public class Food implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8791181701061581183L;

    @Id
    @Column(name = "id")
    private Integer id;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "code")
    private FoodType code;
    
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
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public FoodType getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(FoodType code) {
        this.code = code;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return id == null ? -1 : id.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Food) {
            Food foodOther = (Food)other;
            if (foodOther.getId() != null && id != null && id.equals(foodOther.getId())) 
                return true;
        }
        return false;
    }
}
