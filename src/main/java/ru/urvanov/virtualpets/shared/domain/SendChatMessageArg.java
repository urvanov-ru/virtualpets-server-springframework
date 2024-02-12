/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class SendChatMessageArg implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 4492745786993485411L;
    private Integer addresseeId;
    private String message;
  
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
}
