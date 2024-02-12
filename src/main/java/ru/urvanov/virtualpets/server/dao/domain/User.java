/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import jakarta.validation.constraints.Size;


/**
 * @author fedya
 * 
 */
@Entity
@Table(name = "user")
@NamedQueries({@NamedQuery(name = "findByLogin", query = "from User u where u.login=:login"),
    @NamedQuery(name="list", query="from User"),
    @NamedQuery(name="findByLoginAndPassword", query="from User u where u.login=:login and u.password=:password"),
    @NamedQuery(name="findOnline", query="from User u where u.activeDate > :date"),
    @NamedQuery(name="findByLoginAndEmail", query="from User u where u.login=:login and u.email=:email")})
public class User implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 6592049980085443679L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_seq")
    @SequenceGenerator(name="user_seq",
        sequenceName="user_id_seq", allocationSize=1)
    private Integer id;

    @Column(name = "login")
    @Size(max = 50)
    private String login;
    
    @Column(name = "name")
    @Size(max = 50)
    private String name;

    @Column(name = "password")
    @Size(max = 50)
    private String password;
    
    @Column(name = "facebook_key")
    @Size(max = 50)
    private String facebookKey;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "login_date")
    private Date loginDate;

    @Column(name = "active_date")
    private Date activeDate;

    @Column(name = "sex")
    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "comment")
    @Size(max = 50)
    private String comment;

    @Column(name = "country")
    @Size(max = 50)
    private String country;

    @Column(name = "city")
    @Size(max = 50)
    private String city;

    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name = "email")
    @Size(max = 100)
    private String email;
    
    //@Basic(fetch = FetchType.LAZY)
    //@Lob
    //@Column(name="photo")
    //private byte[] photo;
    
    @Column(name="recover_password_key")
    @Size(max=50)
    private String recoverPasswordKey;
    
    @Column(name="recover_password_valid")
    private Date recoverPasswordValid;
    
    /**
     * Уникальный идентификатор. Используется для восстановления подключения
     * без ввода пароля.
     */
    @Column(name="unid")
    private String unid;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy="user")
    private Set<Pet> pets;
    
    @Version
    @Column(name="version")
    private Integer version;
    
    @Column(name="vkontakte_key")
    private String vkontakteKey;
    
    @Column(name="twitter_key")
    private String twitterKey;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the facebookKey
     */
    public String getFacebookKey() {
        return facebookKey;
    }

    /**
     * @param facebookKey the facebookKey to set
     */
    public void setFacebookKey(String facebookKey) {
        this.facebookKey = facebookKey;
    }

    /**
     * @return the registrationDate
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate
     *            the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the loginDate
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * @param loginDate
     *            the loginDate to set
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * @return the activeDate
     */
    public Date getActiveDate() {
        return activeDate;
    }

    /**
     * @param activeDate
     *            the activeDate to set
     */
    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    /**
     * @return the sex
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * @return the birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate
     *            the birthdate to set
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
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
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

//    /**
//     * @return the photo
//     */
//    public byte[] getPhoto() {
//        return photo;
//    }
//
//    /**
//     * @param photo the photo to set
//     */
//    public void setPhoto(byte[] photo) {
//        this.photo = photo;
//    }

    /**
     * @return the pets
     */
    public Set<Pet> getPets() {
        return pets;
    }

    /**
     * @param pets the pets to set
     */
    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    /**
     * @return the recoverPasswordKey
     */
    public String getRecoverPasswordKey() {
        return recoverPasswordKey;
    }

    /**
     * @param recoverPasswordKey the recoverPasswordKey to set
     */
    public void setRecoverPasswordKey(String recoverPasswordKey) {
        this.recoverPasswordKey = recoverPasswordKey;
    }

    /**
     * @return the recoverPasswordValid
     */
    public Date getRecoverPasswordValid() {
        return recoverPasswordValid;
    }

    /**
     * @param recoverPasswordValid the recoverPasswordValid to set
     */
    public void setRecoverPasswordValid(Date recoverPasswordValid) {
        this.recoverPasswordValid = recoverPasswordValid;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @return the unid
     */
    public String getUnid() {
        return unid;
    }

    /**
     * @param unid the unid to set
     */
    public void setUnid(String unid) {
        this.unid = unid;
    }

    /**
     * @return the vkontakteKey
     */
    public String getVkontakteKey() {
        return vkontakteKey;
    }

    /**
     * @param vkontakteKey the vkontakteKey to set
     */
    public void setVkontakteKey(String vkontakteKey) {
        this.vkontakteKey = vkontakteKey;
    }

    /**
     * @return the twitterKey
     */
    public String getTwitterKey() {
        return twitterKey;
    }

    /**
     * @param twitterKey the twitterKey to set
     */
    public void setTwitterKey(String twitterKey) {
        this.twitterKey = twitterKey;
    }

}
