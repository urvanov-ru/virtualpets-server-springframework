package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class JoinHiddenObjectsGameArg implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2276397504607542639L;
    private HiddenObjectsGameType hiddenObjectsGameType;

    public HiddenObjectsGameType getHiddenObjectsGameType() {
        return hiddenObjectsGameType;
    }

    public void setHiddenObjectsGameType(HiddenObjectsGameType hiddenObjectsGameType) {
        this.hiddenObjectsGameType = hiddenObjectsGameType;
    }
    
    

}
