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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * @author fedya
 *
 */
@Entity
@Table (name = "pet_building_material")
public class PetBuildingMaterial implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2864536542057382514L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_building_material_seq")
    @SequenceGenerator(name="pet_building_material_seq",
        sequenceName="pet_building_material_id_seq", allocationSize=1)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    
    @ManyToOne
    @JoinColumn(name = "building_material_id")
    private BuildingMaterial buildingMaterial;
    
    @Column(name = "building_material_count")
    private Integer buildingMaterialCount;
    
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
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * @param pet the pet to set
     */
    public void setPet(Pet pet) {
        this.pet = pet;
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
     * @return the buildingMaterialCount
     */
    public Integer getBuildingMaterialCount() {
        return buildingMaterialCount;
    }

    /**
     * @param buildingMaterialCount the buildingMaterialCount to set
     */
    public void setBuildingMaterialCount(Integer buildingMaterialCount) {
        this.buildingMaterialCount = buildingMaterialCount;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }
    
    
}
