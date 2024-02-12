/**
 * 
 */
package ru.urvanov.virtualpets.server.service.exception;

/**
 * @author fedya
 *
 */
public class NotEnoughPetResourcesException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 663373297355846631L;

    /**
     * 
     */
    public NotEnoughPetResourcesException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public NotEnoughPetResourcesException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public NotEnoughPetResourcesException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public NotEnoughPetResourcesException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
