/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class RefreshChatResult implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -242543503145846621L;
    private boolean success;
    private String message;
    private ChatMessage[] chatMessages;
    private Integer lastChatMessageId;
    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }
    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
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
     * @return the chatMessages
     */
    public ChatMessage[] getChatMessages() {
        return chatMessages;
    }
    /**
     * @param chatMessages the chatMessages to set
     */
    public void setChatMessages(ChatMessage[] chatMessages) {
        this.chatMessages = chatMessages;
    }
    /**
     * @return the lastChatMessageId
     */
    public Integer getLastChatMessageId() {
        return lastChatMessageId;
    }
    /**
     * @param lastChatMessageId the lastChatMessageId to set
     */
    public void setLastChatMessageId(Integer lastChatMessageId) {
        this.lastChatMessageId = lastChatMessageId;
    }

}
