/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import jakarta.validation.constraints.Size;


/**
 * @author fedya
 *
 */
@Entity
@Table(name="chat")
@NamedQueries({@NamedQuery(name="findLast", query="from Chat c where (c.addressee is null or c.addressee.id = :userId or c.sender.id = :userId) order by c.sendTime desc, c.id desc"),
    @NamedQuery(name="findFromId", query="from Chat c where c.id > :fromId and (c.addressee is null or c.addressee.id = :userId or c.sender.id = :userId) order by c.sendTime asc")})
public class Chat implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6614311694772485588L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="chat_seq")
    @SequenceGenerator(name="chat_seq",
        sequenceName="chat_id_seq", allocationSize=1)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="addressee")
    private User addressee;
    
    @ManyToOne
    @JoinColumn(name="sender")
    private User sender;
    
    @Column(name="send_time")
    private Date sendTime;
    
    @Column
    @Size(max=250)
    private String message;
    
    @Version
    @Column(name="version")
    private Integer version;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the addressee
     */
    public User getAddressee() {
        return addressee;
    }

    /**
     * @param addressee the addressee to set
     */
    public void setAddressee(User addressee) {
        this.addressee = addressee;
    }

    /**
     * @return the sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * @return the sendTime
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime the sendTime to set
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }
}
