/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * @author fedya
 *
 */
@Entity
@Table(name="refrigerator_cost")
public class RefrigeratorCost implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4597700957457308301L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="refrigerator_id")
    private Refrigerator refrigerator;
    
    @ManyToOne
    @JoinColumn(name="building_material_id")
    private BuildingMaterial buildingMaterial;
    
    @Column(name="cost")
    private Integer cost;
    
    @Column
    @Version
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
     * @return the refrigerator
     */
    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    /**
     * @param refrigerator the refrigerator to set
     */
    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    /**
     * @return the buildingMaterial
     */
    public BuildingMaterial getBuildingMaterial() {
        return buildingMaterial;
    }

    /**
     * @param buildingMaterial the buildingMaterial to set
     */
    public void setBuildingMaterial(BuildingMaterial buildingMaterial) {
        this.buildingMaterial = buildingMaterial;
    }

    /**
     * @return the cost
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

}
