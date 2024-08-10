package ru.urvanov.virtualpets.server.controller.api.domain;

import java.io.Serializable;

import ru.urvanov.virtualpets.server.dao.domain.PetType;

public class HiddenObjectsPlayer implements Serializable {

    private static final long serialVersionUID = 2030117646394527554L;

    private Integer userId;
    private Integer petId;
    private String petName;
    private String userName;
    private PetType petType;
    private String hatId;
    private String clothId;
    private String bowId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
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
