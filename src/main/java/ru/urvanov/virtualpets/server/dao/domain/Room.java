/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * @author fedya
 * 
 */
@Entity
@Table(name = "room")
public class Room implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2236553964596693179L;

    @Id
    @Column(name = "pet_id")
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
    
    @Column(name="machine_with_drinks_x")
    private Integer machineWithDrinksX;
    
    @Column(name="machine_with_drinks_y")
    private Integer machineWithDrinksY;
    
    @Column(name = "box_newbie1")
    private Boolean boxNewbie1 = false;
    
    @Column(name="box_newbie2")
    private Boolean boxNewbie2 = false;
    
    @Column(name="box_newbie3")
    private Boolean boxNewbie3 = false;
    
    @Column(name="journal_on_floor")
    private Boolean journalOnFloor = true;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Boolean getBoxNewbie1() {
        return boxNewbie1;
    }

    public void setBoxNewbie1(Boolean boxNewbie1) {
        this.boxNewbie1 = boxNewbie1;
    }

    public Boolean getBoxNewbie2() {
        return boxNewbie2;
    }

    public void setBoxNewbie2(Boolean boxNewbie2) {
        this.boxNewbie2 = boxNewbie2;
    }

    public Boolean getBoxNewbie3() {
        return boxNewbie3;
    }

    public void setBoxNewbie3(Boolean boxNewbie3) {
        this.boxNewbie3 = boxNewbie3;
    }

    public Boolean getJournalOnFloor() {
        return journalOnFloor;
    }

    public void setJournalOnFloor(Boolean journalOnFloor) {
        this.journalOnFloor = journalOnFloor;
    }


}
