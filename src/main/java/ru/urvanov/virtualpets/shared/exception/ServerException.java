/**
 * 
 */
package ru.urvanov.virtualpets.shared.exception;

/**
 * @author fedya
 *
 */
public class ServerException extends DaoException {

    /**
     * 
     */
    private static final long serialVersionUID = 2203239663885705846L;

    /**
     * 
     */
    public ServerException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public ServerException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public ServerException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ServerException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

}
