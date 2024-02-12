/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class HiddenObjectsCollected implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8250007568842329830L;
    
    private int objectId;
    private HiddenObjectsPlayer player;
    
    /**
     * @return the objectId
     */
    public int getObjectId() {
        return objectId;
    }
    /**
     * @param objectId the objectId to set
     */
    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
    /**
     * @return the player
     */
    public HiddenObjectsPlayer getPlayer() {
        return player;
    }
    /**
     * @param player the player to set
     */
    public void setPlayer(HiddenObjectsPlayer player) {
        this.player = player;
    }

    
}
