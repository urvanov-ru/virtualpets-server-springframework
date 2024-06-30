package ru.urvanov.virtualpets.server.service.domain;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserPetDetails {
    private Integer userId;
    private LocalDate userBirthdate;
    private String userName;
    private String userEmail;
    private Integer petId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(LocalDate userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

}
