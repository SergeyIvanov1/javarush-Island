package ru.javarush.sergeyivanov.island.logging;

public class LoggerInitializationException extends RuntimeException {
    String message;
    public LoggerInitializationException(String message) {
        this.message = "error during message initialization";
    }
}
