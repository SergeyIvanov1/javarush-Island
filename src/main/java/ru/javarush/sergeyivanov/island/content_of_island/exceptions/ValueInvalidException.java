package ru.javarush.sergeyivanov.island.content_of_island.exceptions;

public class ValueInvalidException extends RuntimeException {

    private final Exception hiddenException;

    public ValueInvalidException(String message, Exception cause) {
        super(message, cause);
        hiddenException = cause;
    }

    public Exception getHiddenException() {
        return hiddenException;
    }
}
