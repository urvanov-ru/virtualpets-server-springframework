package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import jakarta.validation.constraints.Size;

/**
 * Питомец.
 */
@Entity
@Table(name = "pet")
@NamedQueries({
        @NamedQuery(name = "findByUserId", query = "from Pet p where p.user.id = :userId")
        //,
//        @NamedQuery(name = "findFullById", query = " from Pet p left outer join fetch p.cloths c left outer join fetch p.books b left outer join fetch p.foods f left outer join fetch p.buildingMaterials bm left outer join fetch bm.buildingMaterial left outer join fetch p.drinks d left outer join fetch p.journalEntries je left outer join fetch p.achievements ach where p.id = :id") 
        }
        )
public class Pet implements Serializable {

    private static final long serialVersionUID = 2699175148933987413L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_seq")
    @SequenceGenerator(name="pet_seq",
        sequenceName="pet_id_seq", allocationSize=1)
    private Integer id;

    @Column
    @Size(max = 50)
    private String name;

    @Column(name = "session_key")
    private String sessionKey;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "login_date")
    private Date loginDate;

    @Column(name = "satiety")
    private int satiety;

    @Column(name = "mood")
    private int mood;

    @Column(name = "education")
    private int education;

    @Column(name = "drink")
    private int drink;

    @Column(name = "comment")
    @Size(max = 50)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated
    @Column(name = "pet_type")
    private PetType petType;
    
    @ManyToOne
    @JoinColumn(name="hat_id")
    private Cloth hat;
    
    @ManyToOne
    @JoinColumn(name="cloth_id")
    private Cloth cloth;
    
    @ManyToOne
    @JoinColumn(name="bow_id")
    private Cloth bow;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;
    
    @Column(name="experience")
    private Integer experience = 0;
    
    @Column(name = "eat_count")
    private Integer eatCount = 0;
    
    @Column(name = "drink_count")
    private Integer drinkCount = 0;
    
    @Column(name = "teach_count")
    private Integer teachCount = 0;
    
    @Column(name = "build_count")
    private Integer buildCount = 0;
    
    @Column(name = "hidden_objects_game_count")
    private Integer hiddenObjectsGameCount = 0;
    
    @Version
    private Integer version;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="food")
    private Map<Food, PetFood> foods;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pet_cloth", joinColumns = @JoinColumn(name = "pet_id"), inverseJoinColumns = @JoinColumn(name = "cloth_id"))
    private Set<Cloth> cloths;
    
    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="buildingMaterial")
    private Map<BuildingMaterial, PetBuildingMaterial> buildingMaterials;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="pet_book", joinColumns = @JoinColumn(name="pet_id"), inverseJoinColumns = @JoinColumn(name="book_id"))
    private Set<Book> books;
    
    @OneToMany(mappedBy= "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="drink")
    private Map<Drink, PetDrink> drinks;
    
    @OneToMany(mappedBy="pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="journalEntry")
    private Map<JournalEntry, PetJournalEntry> journalEntries;
    
    @OneToMany(mappedBy="pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "achievement")
    private Map<Achievement, PetAchievement> achievements;

    public Pet() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getDrink() {
        return drink;
    }

    public void setDrink(int drink) {
        this.drink = drink;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Integer getVersion() {
        return version;
    }

    public Map<Food, PetFood> getFoods() {
        return foods;
    }

    public void setFoods(Map<Food, PetFood> foods) {
        this.foods = foods;
    }

    public Set<Cloth> getCloths() {
        return cloths;
    }

    public void setCloths(Set<Cloth> cloths) {
        this.cloths = cloths;
    }

    public Cloth getHat() {
        return hat;
    }

    public void setHat(Cloth hat) {
        this.hat = hat;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    public Cloth getBow() {
        return bow;
    }

    public void setBow(Cloth bow) {
        this.bow = bow;
    }

    public Map<BuildingMaterial, PetBuildingMaterial> getBuildingMaterials() {
        return buildingMaterials;
    }

    public void setBuildingMaterials(
            Map<BuildingMaterial, PetBuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Map<Drink, PetDrink> getDrinks() {
        return drinks;
    }

    public void setDrinks(Map<Drink, PetDrink> drinks) {
        this.drinks = drinks;
    }

    public Map<JournalEntry, PetJournalEntry> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(Map<JournalEntry, PetJournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Map<Achievement, PetAchievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Map<Achievement, PetAchievement> achievements) {
        this.achievements = achievements;
    }

    public Integer getEatCount() {
        return eatCount;
    }

    public void setEatCount(Integer eatCount) {
        this.eatCount = eatCount;
    }

    public Integer getDrinkCount() {
        return drinkCount;
    }

    public void setDrinkCount(Integer drinkCount) {
        this.drinkCount = drinkCount;
    }

    public Integer getTeachCount() {
        return teachCount;
    }

    public void setTeachCount(Integer teachCount) {
        this.teachCount = teachCount;
    }

    public Integer getBuildCount() {
        return buildCount;
    }

    public void setBuildCount(Integer buildCount) {
        this.buildCount = buildCount;
    }

    public Integer getHiddenObjectsGameCount() {
        return hiddenObjectsGameCount;
    }

    public void setHiddenObjectsGameCount(Integer hiddenObjectsGameCount) {
        this.hiddenObjectsGameCount = hiddenObjectsGameCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, name, petType, user.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pet other = (Pet) obj;
        return Objects.equals(createdDate, other.createdDate)
                && Objects.equals(name, other.name) && petType == other.petType
                && Objects.equals(user.getId(), other.user.getId());
    }

    @Override
    public String toString() {
        return "Pet [id=" + id + ", name=" + name + ", createdDate="
                + createdDate + ", comment=" + comment
                + ", user.id=" + user.getId()
                + ", petType=" + petType + "]";
    }

    
}
