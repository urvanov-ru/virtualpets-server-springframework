/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class RefreshChatArg implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5582975770371075976L;
    private Integer lastChatMessageId;

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
