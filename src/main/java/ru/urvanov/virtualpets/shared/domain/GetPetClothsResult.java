package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.List;

public class GetPetClothsResult implements Serializable {

    private static final long serialVersionUID = 5769705176285404538L;
    
    private List<Cloth> cloths;
    private String hatId;
    private String clothId;
    private String bowId;
    public List<Cloth> getCloths() {
        return cloths;
    }
    public void setCloths(List<Cloth> cloths) {
        this.cloths = cloths;
    }
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
