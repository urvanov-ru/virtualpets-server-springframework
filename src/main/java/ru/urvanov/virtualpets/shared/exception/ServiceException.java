/**
 * 
 */
package ru.urvanov.virtualpets.shared.exception;

/**
 * @author fedya
 *
 */
public class ServiceException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2265714399998738317L;

    /**
     * 
     */
    public ServiceException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public ServiceException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public ServiceException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ServiceException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

}
