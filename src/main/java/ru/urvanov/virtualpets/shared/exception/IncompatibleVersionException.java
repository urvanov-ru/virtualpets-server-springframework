/**
 * 
 */
package ru.urvanov.virtualpets.shared.exception;

/**
 * @author fedya
 *
 */
public class IncompatibleVersionException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 2825881727091118166L;
    
    private String serverVersion;
    private String clientVersion;

    /**
     * 
     */
    public IncompatibleVersionException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public IncompatibleVersionException(String message, String serverVersion, String clientVersion) {
        super(message);
        this.serverVersion = serverVersion;
        this.clientVersion = clientVersion;
    }

    /**
     * @param cause
     */
    public IncompatibleVersionException(Throwable cause, String serverVersion, String clientVersion) {
        super(cause);
        this.serverVersion = serverVersion;
        this.clientVersion = clientVersion;
    }

    /**
     * @param message
     * @param cause
     */
    public IncompatibleVersionException(String message, Throwable cause, String serverVersion, String clientVersion) {
        super(message, cause);
        this.serverVersion = serverVersion;
        this.clientVersion = clientVersion;
    }

    /**
     * @return the serverVersion
     */
    public String getServerVersion() {
        return serverVersion;
    }

    /**
     * @param serverVersion the serverVersion to set
     */
    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    /**
     * @return the clientVersion
     */
    public String getClientVersion() {
        return clientVersion;
    }

    /**
     * @param clientVersion the clientVersion to set
     */
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

}
