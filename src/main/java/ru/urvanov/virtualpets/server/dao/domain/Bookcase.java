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
@Table(name="bookcase")
public class Bookcase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6636132588014164542L;

    @Id
    @Column(name="id")
    private Integer id;
    
    @Version
    @Column(name="version")
    private int version;

    @OneToMany(mappedBy = "bookcase", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="buildingMaterial")
    private Map<BuildingMaterial, BookcaseCost> bookcaseCost;
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
    public int getVersion() {
        return version;
    }

    /**
     * @return the bookcaseCost
     */
    public Map<BuildingMaterial, BookcaseCost> getBookcaseCost() {
        return bookcaseCost;
    }

    /**
     * @param bookcaseCost the bookcaseCost to set
     */
    public void setBookcaseCost(Map<BuildingMaterial, BookcaseCost> bookcaseCost) {
        this.bookcaseCost = bookcaseCost;
    }

}
