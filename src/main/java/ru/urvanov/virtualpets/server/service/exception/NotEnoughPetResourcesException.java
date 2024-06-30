package ru.urvanov.virtualpets.server.service.exception;

public class NotEnoughPetResourcesException extends ServiceException {

    private static final long serialVersionUID = 663373297355846631L;

    public NotEnoughPetResourcesException() {
        // TODO Auto-generated constructor stub
    }

    public NotEnoughPetResourcesException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public NotEnoughPetResourcesException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public NotEnoughPetResourcesException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
