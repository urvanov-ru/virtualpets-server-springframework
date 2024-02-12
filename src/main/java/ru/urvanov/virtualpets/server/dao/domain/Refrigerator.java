/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * @author fedya
 *
 */
@Entity
@Table(name = "refrigerator")
public class Refrigerator implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -6335332962524762996L;

    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "max_food_type")
    private int maxFoodType;
    
    @Version
    @Column(name  = "version")
    private Integer version;
    
    @OneToMany(mappedBy = "refrigerator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="buildingMaterial")
    private Map<BuildingMaterial, RefrigeratorCost> refrigeratorCost;

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
     * @return the maxFoodType
     */
    public int getMaxFoodType() {
        return maxFoodType;
    }

    /**
     * @param maxFoodType the maxFoodType to set
     */
    public void setMaxFoodType(int maxFoodType) {
        this.maxFoodType = maxFoodType;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @return the refrigeratorCost
     */
    public Map<BuildingMaterial, RefrigeratorCost> getRefrigeratorCost() {
        return refrigeratorCost;
    }

    /**
     * @param refrigeratorCost the refrigeratorCost to set
     */
    public void setRefrigeratorCost(
            Map<BuildingMaterial, RefrigeratorCost> refrigeratorCost) {
        this.refrigeratorCost = refrigeratorCost;
    }

}
