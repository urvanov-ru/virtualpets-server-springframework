/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class Cloth implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1414760004716132656L;
    
    private Integer id;
    private ClothType clothType;
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

}
