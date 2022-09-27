package ru.javarush.sergeyivanov.island.content_of_island.exceptions;

public class CreateOfNatureObjectException extends RuntimeException {
    private final Exception hiddenException;

    public CreateOfNatureObjectException(String message, Exception cause) {
        super(message, cause);
        hiddenException = cause;
    }

    public Exception getHiddenException() {
        return hiddenException;
    }
}
