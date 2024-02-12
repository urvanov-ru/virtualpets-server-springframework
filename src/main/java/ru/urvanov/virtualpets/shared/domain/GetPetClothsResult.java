/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class GetPetClothsResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5769705176285404538L;
    
    private Cloth[] cloths;
    private Integer hatId;
    private Integer clothId;
    private Integer bowId;

    /**
     * @return the cloths
     */
    public Cloth[] getCloths() {
        return cloths;
    }

    /**
     * @param cloths the cloths to set
     */
    public void setCloths(Cloth[] cloths) {
        this.cloths = cloths;
    }

    /**
     * @return the hatId
     */
    public Integer getHatId() {
        return hatId;
    }

    /**
     * @param hatId the hatId to set
     */
    public void setHatId(Integer hatId) {
        this.hatId = hatId;
    }

    /**
     * @return the clothId
     */
    public Integer getClothId() {
        return clothId;
    }

    /**
     * @param clothId the clothId to set
     */
    public void setClothId(Integer clothId) {
        this.clothId = clothId;
    }

    /**
     * @return the bowId
     */
    public Integer getBowId() {
        return bowId;
    }

    /**
     * @param bowId the bowId to set
     */
    public void setBowId(Integer bowId) {
        this.bowId = bowId;
    }


}
