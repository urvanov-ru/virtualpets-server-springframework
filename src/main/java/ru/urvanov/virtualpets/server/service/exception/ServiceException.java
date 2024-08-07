package ru.urvanov.virtualpets.server.service.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -2265714399998738317L;

    private String errorCode = "unknown";
    
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    protected void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return this.errorCode;
    }

}
