/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 * 
 */
public class PetInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5366365656067078720L;
    private Integer id;
    private String name;
    private PetType petType;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the petType
     */
    public PetType getPetType() {
        return petType;
    }

    /**
     * @param petType
     *            the petType to set
     */
    public void setPetType(PetType petType) {
        this.petType = petType;
    }

}
