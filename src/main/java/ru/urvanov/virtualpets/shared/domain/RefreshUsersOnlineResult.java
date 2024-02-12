/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class RefreshUsersOnlineResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2427557389940483735L;
    private boolean success;
    private String message;
    private UserInfo[] users; 
    
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
     * @return the users
     */
    public UserInfo[] getUsers() {
        return users;
    }
    /**
     * @param users the users to set
     */
    public void setUsers(UserInfo[] users) {
        this.users = users;
    }
}
