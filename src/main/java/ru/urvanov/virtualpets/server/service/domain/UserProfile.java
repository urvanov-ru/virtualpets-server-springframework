package ru.urvanov.virtualpets.server.service.domain;

import java.util.Date;

public class UserProfile {
    private String authStr;
    private String email;
    private String name;
    private Date birthdate;
    public String getAuthStr() {
        return authStr;
    }
    public void setAuthStr(String authStr) {
        this.authStr = authStr;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    
}
