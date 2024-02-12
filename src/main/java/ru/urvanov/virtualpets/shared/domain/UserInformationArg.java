/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class UserInformationArg  implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -4301503973351649355L;
    private int userId;
    
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

}
