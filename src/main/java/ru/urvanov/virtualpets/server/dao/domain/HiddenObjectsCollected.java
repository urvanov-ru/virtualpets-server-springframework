package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

public class HiddenObjectsCollected implements Serializable {

    private static final long serialVersionUID = -8250007568842329830L;
    
    private int objectId;
    private HiddenObjectsPlayer player;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public HiddenObjectsPlayer getPlayer() {
        return player;
    }

    public void setPlayer(HiddenObjectsPlayer player) {
        this.player = player;
    }
    
}
