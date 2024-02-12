/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
 * @author fedya
 * 
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

    /**
     * 
     */
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
    @Column(name = "version")
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
//    
    /**
     * 
     */
    public Pet() {
        // TODO Auto-generated constructor stub
    }

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
     * @return the sessionKey
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * @param sessionKey
     *            the sessionKey to set
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
     * @return the satiety
     */
    public int getSatiety() {
        return satiety;
    }

    /**
     * @param satiety
     *            the satiety to set
     */
    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    /**
     * @return the mood
     */
    public int getMood() {
        return mood;
    }

    /**
     * @param mood
     *            the mood to set
     */
    public void setMood(int mood) {
        this.mood = mood;
    }

    /**
     * @return the education
     */
    public int getEducation() {
        return education;
    }

    /**
     * @param education
     *            the education to set
     */
    public void setEducation(int education) {
        this.education = education;
    }

    /**
     * @return the drink
     */
    public int getDrink() {
        return drink;
    }

    /**
     * @param drink
     *            the drink to set
     */
    public void setDrink(int drink) {
        this.drink = drink;
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
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the petType
     */
    public PetType getPetType() {
        return petType;
    }

    /**
     * @param petType
     *            the petType to set
     */
    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @return the foods
     */
    public Map<Food, PetFood> getFoods() {
        return foods;
    }

    /**
     * @param foods the foods to set
     */
    public void setFoods(Map<Food, PetFood> foods) {
        this.foods = foods;
    }

    /**
     * @return the cloths
     */
    public Set<Cloth> getCloths() {
        return cloths;
    }

    /**
     * @param cloths
     *            the cloths to set
     */
    public void setCloths(Set<Cloth> cloths) {
        this.cloths = cloths;
    }

    /**
     * @return the hat
     */
    public Cloth getHat() {
        return hat;
    }

    /**
     * @param hat the hat to set
     */
    public void setHat(Cloth hat) {
        this.hat = hat;
    }

    /**
     * @return the cloth
     */
    public Cloth getCloth() {
        return cloth;
    }

    /**
     * @param cloth the cloth to set
     */
    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    /**
     * @return the bow
     */
    public Cloth getBow() {
        return bow;
    }

    /**
     * @param bow the bow to set
     */
    public void setBow(Cloth bow) {
        this.bow = bow;
    }

    /**
     * @return the buildingMaterials
     */
    public Map<BuildingMaterial, PetBuildingMaterial> getBuildingMaterials() {
        return buildingMaterials;
    }

    /**
     * @param buildingMaterials the buildingMaterials to set
     */
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

}
