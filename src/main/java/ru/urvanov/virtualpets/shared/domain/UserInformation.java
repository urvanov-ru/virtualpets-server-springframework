/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fedya
 *
 */
public class UserInformation implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7727325715161117786L;
    
    private int id;
    private String name;
    private Date birthdate;
    private Sex sex;
    private String country;
    private String city;
    private String comment;
    private byte[] photo;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }
    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    /**
     * @return the sex
     */
    public Sex getSex() {
        return sex;
    }
    /**
     * @param sex the sex to set
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }
    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }
    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }
    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * @return the photo
     */
    public byte[] getPhoto() {
        return photo;
    }
    /**
     * @param photo the photo to set
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


}
