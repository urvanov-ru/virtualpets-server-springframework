/**
 * 
 */
package ru.urvanov.virtualpets.shared.exception;

/**
 * @author fedya
 *
 */
public class NameIsBusyException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = -4720882988091063517L;

    /**
     * 
     */
    public NameIsBusyException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public NameIsBusyException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public NameIsBusyException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     */
    public NameIsBusyException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

}
