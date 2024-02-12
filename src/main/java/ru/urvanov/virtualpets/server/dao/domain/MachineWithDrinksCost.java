/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

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
@Table(name="machine_with_drinks_cost")
public class MachineWithDrinksCost {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="machine_with_drinks_id")
    private MachineWithDrinks machineWithDrinks;
    
    @ManyToOne
    @JoinColumn(name="building_material_id")
    private BuildingMaterial buildingMaterial;
    
    @Column(name="cost")
    private Integer cost;
    
    @Version
    @Column(name="version")
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
    
    

    public MachineWithDrinks getMachineWithDrinks() {
        return machineWithDrinks;
    }

    public void setMachineWithDrinks(MachineWithDrinks machineWithDrinks) {
        this.machineWithDrinks = machineWithDrinks;
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
