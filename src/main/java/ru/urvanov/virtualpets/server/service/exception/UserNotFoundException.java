package ru.urvanov.virtualpets.server.service.exception;

public class UserNotFoundException extends ServiceException {

    private static final long serialVersionUID = 6804000735049830517L;

    public UserNotFoundException() {
    }
    
    public UserNotFoundException(Integer id) {
        this("User with id " + id + " was not found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public UserNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
