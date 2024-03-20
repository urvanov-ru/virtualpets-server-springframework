package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
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
        @NamedQuery(name = "findByUserId", query = "from Pet p where p.user.id = :userId"),
        @NamedQuery(name = "findFullById", query = " from Pet p left outer join fetch p.cloths c left outer join fetch p.books b left outer join fetch p.foods f left outer join fetch p.buildingMaterials bm left outer join fetch bm.buildingMaterial left outer join fetch p.drinks d left outer join fetch p.journalEntries je left outer join fetch p.achievements ach where p.id = :id") 
        }
        )
public class Pet implements Serializable {

    private static final long serialVersionUID = 2699175148933987413L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_seq")
    @SequenceGenerator(name="pet_seq",
        sequenceName="pet_id_seq", allocationSize=1)
    private Integer id;

    @Size(max = 50)
    private String name;

    private String sessionKey;

    private OffsetDateTime createdDate;

    private OffsetDateTime loginDate;

    private int satiety;

    private int mood;

    private int education;

    private int drink;

    @Size(max = 50)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated
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
    
    private int experience = 0;
    
    private int eatCount = 0;
    
    private int drinkCount = 0;
    
    private int teachCount = 0;
    
    private int buildCount = 0;
    
    private int hiddenObjectsGameCount;
    
    private OffsetDateTime everyDayLoginLast;
    
    private int everyDayLoginCount;
    
    @Version
    private int version;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="food_id")
    private Map<FoodId, PetFood> foods;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pet_cloth", joinColumns = @JoinColumn(name = "pet_id"), inverseJoinColumns = @JoinColumn(name = "cloth_id"))
    private Set<Cloth> cloths;
    
    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "building_material_id")
    private Map<BuildingMaterialId, PetBuildingMaterial> buildingMaterials;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="pet_book", joinColumns = @JoinColumn(name="pet_id"), inverseJoinColumns = @JoinColumn(name="book_id"))
    private Set<Book> books;
    
    @OneToMany(mappedBy= "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "drink_id")
    private Map<DrinkId, PetDrink> drinks;
    
    @OneToMany(mappedBy="pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "journal_entry_id")
    private Map<JournalEntryId, PetJournalEntry> journalEntries;
    
    @OneToMany(mappedBy="pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "achievement_id")
    private Map<AchievementId, PetAchievement> achievements;

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

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(OffsetDateTime loginDate) {
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getEatCount() {
        return eatCount;
    }

    public void setEatCount(int eatCount) {
        this.eatCount = eatCount;
    }

    public int getDrinkCount() {
        return drinkCount;
    }

    public void setDrinkCount(int drinkCount) {
        this.drinkCount = drinkCount;
    }

    public int getTeachCount() {
        return teachCount;
    }

    public void setTeachCount(int teachCount) {
        this.teachCount = teachCount;
    }

    public int getBuildCount() {
        return buildCount;
    }

    public void setBuildCount(int buildCount) {
        this.buildCount = buildCount;
    }

    public int getHiddenObjectsGameCount() {
        return hiddenObjectsGameCount;
    }

    public void setHiddenObjectsGameCount(int hiddenObjectsGameCount) {
        this.hiddenObjectsGameCount = hiddenObjectsGameCount;
    }

    public OffsetDateTime getEveryDayLoginLast() {
        return everyDayLoginLast;
    }

    public void setEveryDayLoginLast(OffsetDateTime everyDayLoginLast) {
        this.everyDayLoginLast = everyDayLoginLast;
    }

    public int getEveryDayLoginCount() {
        return everyDayLoginCount;
    }

    public void setEveryDayLoginCount(int everyDayLoginCount) {
        this.everyDayLoginCount = everyDayLoginCount;
    }

    public Map<FoodId, PetFood> getFoods() {
        return foods;
    }

    public void setFoods(Map<FoodId, PetFood> foods) {
        this.foods = foods;
    }

    public Set<Cloth> getCloths() {
        return cloths;
    }

    public void setCloths(Set<Cloth> cloths) {
        this.cloths = cloths;
    }

    public Map<BuildingMaterialId, PetBuildingMaterial> getBuildingMaterials() {
        return buildingMaterials;
    }

    public void setBuildingMaterials(
            Map<BuildingMaterialId, PetBuildingMaterial> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Map<DrinkId, PetDrink> getDrinks() {
        return drinks;
    }

    public void setDrinks(Map<DrinkId, PetDrink> drinks) {
        this.drinks = drinks;
    }

    public Map<JournalEntryId, PetJournalEntry> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(
            Map<JournalEntryId, PetJournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public Map<AchievementId, PetAchievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Map<AchievementId, PetAchievement> achievements) {
        this.achievements = achievements;
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
