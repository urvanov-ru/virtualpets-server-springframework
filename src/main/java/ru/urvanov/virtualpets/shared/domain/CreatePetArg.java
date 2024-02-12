/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 * 
 */
public class CreatePetArg implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5466915602295834311L;
    private String name;
    private PetType petType;
    private String comment;

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
     * @param petType the petType to set
     */
    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
