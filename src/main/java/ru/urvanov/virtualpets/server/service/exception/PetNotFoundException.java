package ru.urvanov.virtualpets.server.service.exception;

public class PetNotFoundException extends ServiceException {

    private static final long serialVersionUID = 6804000735049830517L;

    public PetNotFoundException() {
    }
    
    public PetNotFoundException(Integer id) {
        this("Pet with id " + id + " was not found.");
    }

    public PetNotFoundException(String message) {
        super(message);
    }

    public PetNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public PetNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
