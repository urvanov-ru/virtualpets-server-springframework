package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name="cloth")
public class Cloth implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6431503399036752134L;

    @Id
    private Integer id;
    
    @Enumerated
    @Column(name="cloth_type")
    private ClothType clothType;
    
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

    /**
     * @return the clothType
     */
    public ClothType getClothType() {
        return clothType;
    }

    /**
     * @param clothType the clothType to set
     */
    public void setClothType(ClothType clothType) {
        this.clothType = clothType;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }
}
