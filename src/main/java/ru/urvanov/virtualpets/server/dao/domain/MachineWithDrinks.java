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
@Table(name="machine_with_drinks")
public class MachineWithDrinks implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2181386187900603405L;

    @Id
    @Column(name="id")
    private Integer id;
    
    @Version
    @Column(name="version")
    private Integer version;
    
    @OneToMany(mappedBy = "machineWithDrinks", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="buildingMaterial")
    private Map<BuildingMaterial, MachineWithDrinksCost> machineWithDrinksCost;

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
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    public Map<BuildingMaterial, MachineWithDrinksCost> getMachineWithDrinksCost() {
        return machineWithDrinksCost;
    }

    public void setMachineWithDrinksCost(
            Map<BuildingMaterial, MachineWithDrinksCost> machineWithDrinksCost) {
        this.machineWithDrinksCost = machineWithDrinksCost;
    }

    
}
