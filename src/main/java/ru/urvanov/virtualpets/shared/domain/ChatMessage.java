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
public class ChatMessage implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3870376160365638500L;
    private Integer id;
    private Integer senderId;
    private String senderName;
    private Integer addresseeId;
    private String addresseeName;
    private String messageName;
    private Date sendTime;
    
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
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }
    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    /**
     * @return the addresseeName
     */
    public String getAddresseeName() {
        return addresseeName;
    }
    /**
     * @param addresseeName the addresseeName to set
     */
    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return messageName;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.messageName = message;
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
     * @return the senderId
     */
    public Integer getSenderId() {
        return senderId;
    }
    /**
     * @param senderId the senderId to set
     */
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    /**
     * @return the addresseeId
     */
    public Integer getAddresseeId() {
        return addresseeId;
    }
    /**
     * @param addresseeId the addresseeId to set
     */
    public void setAddresseeId(Integer addresseeId) {
        this.addresseeId = addresseeId;
    }

}
