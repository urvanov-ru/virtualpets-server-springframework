package ru.urvanov.virtualpets.server.api.domain;

import java.io.Serializable;
import java.time.LocalDate;

import ru.urvanov.virtualpets.server.dao.domain.Sex;

public class UserInformation implements Serializable {

    private static final long serialVersionUID = 7727325715161117786L;
    
    private int id;
    private String name;
    private LocalDate birthdate;
    private Sex sex;
    private String country;
    private String city;
    private String comment;
    private byte[] photo;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    public Sex getSex() {
        return sex;
    }
    public void setSex(Sex sex) {
        this.sex = sex;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
   

}
