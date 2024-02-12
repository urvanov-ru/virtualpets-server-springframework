/**
 * 
 */
package ru.urvanov.virtualpets.shared.exception;

/**
 * @author fedya
 *
 */
public class DaoException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 4702884611353708623L;

    /**
     * 
     */
    public DaoException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public DaoException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public DaoException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     */
    public DaoException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

}
