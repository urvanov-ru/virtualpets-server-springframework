/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * @author fedya
 * 
 */
@Entity
@Table(name = "building_material")
public class BuildingMaterial implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6611026384958159106L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    @Enumerated(value = EnumType.STRING)
    private BuildingMaterialType code;

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
     * @return the code
     */
    public BuildingMaterialType getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(BuildingMaterialType code) {
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
        return id == null ? 0 : id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof BuildingMaterial) {
            BuildingMaterial other = (BuildingMaterial) obj;
            return getId() != null && other.getId() != null
                    && getId().equals(other.getId());
        }
        return false;
    }
}
