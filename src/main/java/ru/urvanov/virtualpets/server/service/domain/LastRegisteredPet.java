package ru.urvanov.virtualpets.server.service.domain;

import java.util.Date;

public class LastRegisteredPet {
    private Integer id;
    private Date createdDate;
    private String name;
    
    public LastRegisteredPet(Integer id, Date createdDate, String name) {
        this.createdDate = createdDate;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
