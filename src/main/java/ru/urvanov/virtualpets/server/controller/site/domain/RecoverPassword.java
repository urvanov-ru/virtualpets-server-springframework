/**
 * 
 */
package ru.urvanov.virtualpets.server.controller.site.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class RecoverPassword implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 957955965106226661L;
    private String recoverPasswordKey;
    private String newPassword;
    /**
     * @return the recoverPasswordKey
     */
    public String getRecoverPasswordKey() {
        return recoverPasswordKey;
    }
    /**
     * @param recoverPasswordKey the recoverPasswordKey to set
     */
    public void setRecoverPasswordKey(String recoverPasswordKey) {
        this.recoverPasswordKey = recoverPasswordKey;
    }
    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }
    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    

}
