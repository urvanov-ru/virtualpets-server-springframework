package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class SavePetCloths implements Serializable {

    private static final long serialVersionUID = 4732810658992905727L;

    private String hatId;
    private String clothId;
    private String bowId;
    
    public String getHatId() {
        return hatId;
    }
    public void setHatId(String hatId) {
        this.hatId = hatId;
    }
    public String getClothId() {
        return clothId;
    }
    public void setClothId(String clothId) {
        this.clothId = clothId;
    }
    public String getBowId() {
        return bowId;
    }
    public void setBowId(String bowId) {
        this.bowId = bowId;
    }

}
