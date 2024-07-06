package ru.urvanov.virtualpets.server.service.exception;

public class NameIsBusyException extends ServiceException {

    private static final long serialVersionUID = -4720882988091063517L;

    public NameIsBusyException() {
        this.setErrorCode("name_is_busy");
    }


}
