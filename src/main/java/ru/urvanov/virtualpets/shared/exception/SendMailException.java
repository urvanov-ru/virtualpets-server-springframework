/**
 * 
 */
package ru.urvanov.virtualpets.shared.exception;

/**
 * @author fedya
 *
 */
public class SendMailException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 5865368409542701452L;

    /**
     * @param message
     */
    public SendMailException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param root
     */
    public SendMailException(String message, Throwable root) {
        super(message, root);
        // TODO Auto-generated constructor stub
    }

}
