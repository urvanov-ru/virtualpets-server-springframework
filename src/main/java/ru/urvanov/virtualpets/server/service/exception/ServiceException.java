package ru.urvanov.virtualpets.server.service.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -2265714399998738317L;

    private String errorCode = "unknown";
    
    public ServiceException() {
        // TODO Auto-generated constructor stub
    }

    public ServiceException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }
    
    protected void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return this.errorCode;
    }

}
