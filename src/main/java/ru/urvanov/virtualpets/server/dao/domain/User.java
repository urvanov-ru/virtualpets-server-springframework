package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;


/**
 * Информация о пользователе-игроке.
 */
@Entity
@Table(name = "user")
@NamedQueries({@NamedQuery(name = "findByLogin", query = "from User u where u.login=:login"),
    @NamedQuery(name="list", query="from User"),
    @NamedQuery(name="findByLoginAndPassword", query="from User u where u.login=:login and u.password=:password"),
    @NamedQuery(name="findOnline", query="from User u where u.activeDate > :date"),
    @NamedQuery(name="findByLoginAndEmail", query="from User u where u.login=:login and u.email=:email")})
public class User implements UserDetails, Serializable{

    private static final long serialVersionUID = 6592049980085443679L;

    /**
     * Первичный ключ. Генерируемый.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_seq")
    @SequenceGenerator(name="user_seq",
        sequenceName="user_id_seq", allocationSize=1)
    private Integer id;

    /**
     * Логин. Натуральный ключ.
     */
    @Column(name = "login")
    @Size(max = 50)
    private String login;
    
    @Column(name = "name")
    @Size(max = 50)
    private String name;

    @Column(name = "password")
    @Size(max = 100)
    private String password;
    

    @Column(name = "registration_date")
    private OffsetDateTime registrationDate;

    @Column(name = "login_date")
    private OffsetDateTime loginDate;

    @Column(name = "active_date")
    private OffsetDateTime activeDate;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "birthdate")
    private LocalDate birthdate;

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
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "email")
    @Size(max = 100)
    private String email;
    
    @Column(name="recover_password_key")
    @Size(max=50)
    private String recoverPasswordKey;
    
    @Column(name="recover_password_valid")
    private OffsetDateTime recoverPasswordValid;
    
    /**
     * Уникальный идентификатор. Используется для восстановления подключения
     * без ввода пароля.
     */
    @Column(name="unid")
    private String unid;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy="user")
    private Set<Pet> pets;
    
    @Version
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OffsetDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(OffsetDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public OffsetDateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(OffsetDateTime loginDate) {
        this.loginDate = loginDate;
    }

    public OffsetDateTime getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(OffsetDateTime activeDate) {
        this.activeDate = activeDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public String getRecoverPasswordKey() {
        return recoverPasswordKey;
    }

    public void setRecoverPasswordKey(String recoverPasswordKey) {
        this.recoverPasswordKey = recoverPasswordKey;
    }

    public OffsetDateTime getRecoverPasswordValid() {
        return recoverPasswordValid;
    }

    public void setRecoverPasswordValid(OffsetDateTime recoverPasswordValid) {
        this.recoverPasswordValid = recoverPasswordValid;
    }

    public Integer getVersion() {
        return version;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();
        granted.add(new SimpleGrantedAuthority("ROLE_USER"));
        return granted;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(login, other.login);
    }

    @Override
    public String toString() {
        return "User [login=" + login + ", name=" + name + ", loginDate="
                + loginDate + ", sex=" + sex + ", birthdate=" + birthdate
                + ", email=" + email + "]";
    }

}
