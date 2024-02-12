/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class CollectObjectArg implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 7991341084414710497L;
    
    private Integer objectId;

    /**
     * @return the objectId
     */
    public Integer getObjectId() {
        return objectId;
    }

    /**
     * @param objectId the objectId to set
     */
    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }
    
}
