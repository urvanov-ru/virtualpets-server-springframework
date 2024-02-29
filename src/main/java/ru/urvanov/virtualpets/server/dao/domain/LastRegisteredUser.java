package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Date;

public class LastRegisteredUser {
    
    private Date registrationDate;
    
    private String name;
    
    private long petsCount;

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPetsCount() {
        return petsCount;
    }

    public void setPetsCount(long petsCount) {
        this.petsCount = petsCount;
    }
    
    

}
