/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class PetListResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6493382662203586706L;
    private boolean success;
    private String message;
    private PetInfo[] petsInfo;
    /**
     * @return the petsInfo
     */
    public PetInfo[] getPetsInfo() {
        return petsInfo;
    }
    /**
     * @param petsInfo the petsInfo to set
     */
    public void setPetsInfo(PetInfo[] petsInfo) {
        this.petsInfo = petsInfo;
    }
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
    
}
