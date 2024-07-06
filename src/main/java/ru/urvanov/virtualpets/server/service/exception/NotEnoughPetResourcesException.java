package ru.urvanov.virtualpets.server.service.exception;

public class NotEnoughPetResourcesException extends ServiceException {

    private static final long serialVersionUID = 663373297355846631L;

    public NotEnoughPetResourcesException() {
        this.setErrorCode("not_enough_pet_resources");
    }

    public NotEnoughPetResourcesException(String message) {
        super(message);
        this.setErrorCode("not_enough_pet_resources");
    }

    public NotEnoughPetResourcesException(Throwable cause) {
        super(cause);
        this.setErrorCode("not_enough_pet_resources");
    }

    public NotEnoughPetResourcesException(String message, Throwable cause) {
        super(message, cause);
        this.setErrorCode("not_enough_pet_resources");
    }

}
