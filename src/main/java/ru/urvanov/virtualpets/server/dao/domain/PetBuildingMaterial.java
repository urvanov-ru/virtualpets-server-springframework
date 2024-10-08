package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Запись о количестве строительного материала у питомца.
 */
@Entity
@Table (name = "pet_building_material")
public class PetBuildingMaterial {

    /**
     * Первичный ключ. Генерируемый.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_building_material_seq")
    @SequenceGenerator(name="pet_building_material_seq",
        sequenceName="pet_building_material_id_seq", allocationSize = 1)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private BuildingMaterial buildingMaterial;
    
    private int buildingMaterialCount;
    
    @Version
    private int version;

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

    public BuildingMaterial getBuildingMaterial() {
        return buildingMaterial;
    }

    public void setBuildingMaterial(BuildingMaterial buildingMaterial) {
        this.buildingMaterial = buildingMaterial;
    }

    public int getBuildingMaterialCount() {
        return buildingMaterialCount;
    }

    public void setBuildingMaterialCount(int buildingMaterialCount) {
        this.buildingMaterialCount = buildingMaterialCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingMaterial.getId(), buildingMaterialCount, pet.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PetBuildingMaterial other = (PetBuildingMaterial) obj;
        return Objects.equals(buildingMaterial.getId(), other.buildingMaterial.getId())
                && Objects.equals(buildingMaterialCount,
                        other.buildingMaterialCount)
                && Objects.equals(pet.getId(), other.pet.getId());
    }

    @Override
    public String toString() {
        return "PetBuildingMaterial [id=" + id + ", pet.id=" + pet.getId()
                + ", buildingMaterial.id=" + buildingMaterial.getId()
                + ", buildingMaterialCount=" + buildingMaterialCount
                + ", version=" + version + "]";
    }
    
}
