package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Комната питомца.
 */
@Entity
@Table(name = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 2236553964596693179L;

    /**
     * Первичный ключ комнаты. По совместительству идентификатор питомца, так
     * как у одного питомца может быть только одна комната.
     */
    @Id
    private Integer petId;

    @ManyToOne
    @JoinColumn(name = "refrigerator_id")
    private Refrigerator refrigerator;

    @Column(name = "refrigerator_x")
    private Integer refrigeratorX;

    @Column(name = "refrigerator_y")
    private Integer refrigeratorY;

    @Version
    private Integer version; 
    
    @ManyToOne
    @JoinColumn(name = "bookcase_id")
    private Bookcase bookcase;
    
    @Column(name = "bookcase_x")
    private Integer bookcaseX;
    
    @Column(name = "bookcase_y")
    private Integer bookcaseY;
    
    @ManyToOne
    @JoinColumn(name="machine_with_drinks_id")
    private MachineWithDrinks machineWithDrinks;
    
    @Column(name = "machine_with_drinks_x")
    private Integer machineWithDrinksX;
    
    @Column(name = "machine_with_drinks_y")
    private Integer machineWithDrinksY;
    
    private boolean boxNewbie1 = false;
    
    private boolean boxNewbie2 = false;
    
    private boolean boxNewbie3 = false;
    
    private boolean journalOnFloor = true;
    
    private OffsetDateTime everyDayBoxLast;
    
    private boolean everyDayBox = false;

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    public Integer getRefrigeratorX() {
        return refrigeratorX;
    }

    public void setRefrigeratorX(Integer refrigeratorX) {
        this.refrigeratorX = refrigeratorX;
    }

    public Integer getRefrigeratorY() {
        return refrigeratorY;
    }

    public void setRefrigeratorY(Integer refrigeratorY) {
        this.refrigeratorY = refrigeratorY;
    }

    public Bookcase getBookcase() {
        return bookcase;
    }

    public void setBookcase(Bookcase bookcase) {
        this.bookcase = bookcase;
    }

    public Integer getBookcaseX() {
        return bookcaseX;
    }

    public void setBookcaseX(Integer bookcaseX) {
        this.bookcaseX = bookcaseX;
    }

    public Integer getBookcaseY() {
        return bookcaseY;
    }

    public void setBookcaseY(Integer bookcaseY) {
        this.bookcaseY = bookcaseY;
    }

    public MachineWithDrinks getMachineWithDrinks() {
        return machineWithDrinks;
    }

    public void setMachineWithDrinks(MachineWithDrinks machineWithDrinks) {
        this.machineWithDrinks = machineWithDrinks;
    }

    public Integer getMachineWithDrinksX() {
        return machineWithDrinksX;
    }

    public void setMachineWithDrinksX(Integer machineWithDrinksX) {
        this.machineWithDrinksX = machineWithDrinksX;
    }

    public Integer getMachineWithDrinksY() {
        return machineWithDrinksY;
    }

    public void setMachineWithDrinksY(Integer machineWithDrinksY) {
        this.machineWithDrinksY = machineWithDrinksY;
    }

    public boolean isBoxNewbie1() {
        return boxNewbie1;
    }

    public void setBoxNewbie1(boolean boxNewbie1) {
        this.boxNewbie1 = boxNewbie1;
    }

    public boolean isBoxNewbie2() {
        return boxNewbie2;
    }

    public void setBoxNewbie2(boolean boxNewbie2) {
        this.boxNewbie2 = boxNewbie2;
    }

    public boolean isBoxNewbie3() {
        return boxNewbie3;
    }

    public void setBoxNewbie3(boolean boxNewbie3) {
        this.boxNewbie3 = boxNewbie3;
    }

    public boolean isJournalOnFloor() {
        return journalOnFloor;
    }

    public void setJournalOnFloor(boolean journalOnFloor) {
        this.journalOnFloor = journalOnFloor;
    }

    public OffsetDateTime getEveryDayBoxLast() {
        return everyDayBoxLast;
    }

    public void setEveryDayBoxLast(OffsetDateTime everyDayBoxLast) {
        this.everyDayBoxLast = everyDayBoxLast;
    }

    public boolean isEveryDayBox() {
        return everyDayBox;
    }

    public void setEveryDayBox(boolean everyDayBox) {
        this.everyDayBox = everyDayBox;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        return Objects.equals(petId, other.petId);
    }

    @Override
    public String toString() {
        return "Room [petId=" + petId + ", boxNewbie1=" + boxNewbie1
                + ", boxNewbie2=" + boxNewbie2 + ", boxNewbie3=" + boxNewbie3
                + ", journalOnFloor=" + journalOnFloor + ", everyDayBoxLast="
                + everyDayBoxLast + ", everyDayBox=" + everyDayBox + "]";
    }
    
    
}
