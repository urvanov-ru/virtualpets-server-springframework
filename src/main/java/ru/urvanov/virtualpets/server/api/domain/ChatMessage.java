package ru.urvanov.virtualpets.server.api.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 3870376160365638500L;
    private Integer id;
    private Integer senderId;
    private String senderName;
    private Integer addresseeId;
    private String addresseeName;
    private String message;
    private OffsetDateTime sendTime;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSenderId() {
        return senderId;
    }
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public Integer getAddresseeId() {
        return addresseeId;
    }
    public void setAddresseeId(Integer addresseeId) {
        this.addresseeId = addresseeId;
    }
    public String getAddresseeName() {
        return addresseeName;
    }
    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public OffsetDateTime getSendTime() {
        return sendTime;
    }
    public void setSendTime(OffsetDateTime sendTime) {
        this.sendTime = sendTime;
    }
    

}
