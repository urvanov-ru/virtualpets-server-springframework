package ru.urvanov.virtualpets.server.service.exception;

public class NotNowException extends ServiceException {

    private static final long serialVersionUID = 3672050831557824205L;

    public NotNowException() {
        this.setErrorCode("not_now");
    }

    public NotNowException(String message) {
        super(message);
        this.setErrorCode("not_now");
    }

    public NotNowException(Throwable throwable) {
        super(throwable);
        this.setErrorCode("not_now");
    }

    public NotNowException(String message, Throwable throwable) {
        super(message, throwable);
        this.setErrorCode("not_now");
    }

}
