/**
 * 
 */
package ru.urvanov.virtualpets.shared.exception;

/**
 * @author fedya
 *
 */
public class ConnectionException extends DaoException {

    /**
     * 
     */
    private static final long serialVersionUID = -6508583784303802986L;

    /**
     * 
     */
    public ConnectionException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public ConnectionException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public ConnectionException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ConnectionException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

}
