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
@Table(name="bookcase_cost")
public class BookcaseCost implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2619627580580783008L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="bookcase_id")
    private Bookcase bookcase;

    @ManyToOne
    @JoinColumn(name="building_material_id")
    private BuildingMaterial buildingMaterial;
    
    @Column(name="cost")
    private Integer cost;
    
    @Version
    @Column(name="version")
    private int version;

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
     * @return the bookcase
     */
    public Bookcase getBookcase() {
        return bookcase;
    }

    /**
     * @param bookcase the bookcase to set
     */
    public void setBookcase(Bookcase bookcase) {
        this.bookcase = bookcase;
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
    public int getVersion() {
        return version;
    }
}
