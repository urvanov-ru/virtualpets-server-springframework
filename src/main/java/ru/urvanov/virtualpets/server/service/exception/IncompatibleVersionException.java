package ru.urvanov.virtualpets.server.service.exception;

public class IncompatibleVersionException extends ServiceException {

    private static final long serialVersionUID = 2825881727091118166L;
    
    private String serverVersion;
    private String clientVersion;

    public IncompatibleVersionException() {
        this.setErrorCode("incompatible_version");
    }

    public IncompatibleVersionException(String message, String serverVersion, String clientVersion) {
        super(message);
        this.setErrorCode("incompatible_version");
        this.serverVersion = serverVersion;
        this.clientVersion = clientVersion;
    }

    public IncompatibleVersionException(Throwable cause, String serverVersion, String clientVersion) {
        super(cause);
        this.setErrorCode("incompatible_version");
        this.serverVersion = serverVersion;
        this.clientVersion = clientVersion;
    }

    public IncompatibleVersionException(String message, Throwable cause, String serverVersion, String clientVersion) {
        super(message, cause);
        this.setErrorCode("incompatible_version");
        this.serverVersion = serverVersion;
        this.clientVersion = clientVersion;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

}
