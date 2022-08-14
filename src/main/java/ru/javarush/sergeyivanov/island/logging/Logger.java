package ru.javarush.sergeyivanov.island.logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String OUT_DIRECTORY = "logs";
    private static final String OUT_FILE = "output.log";
    private static final String OUTPUT_FILEPATH = OUT_DIRECTORY + SEPARATOR + OUT_FILE;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static Logger INSTANCE;
    Path outputFilePath;

    public Logger() throws IOException {
        validateAndCreateDirectory();
        validateAndCreateLogFile();

        this.outputFilePath = Path.of(OUTPUT_FILEPATH);
    }

    public static Logger getINSTANCE() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new Logger();
            } catch (IOException ex) {
                throw new LoggerInitializationException("Error during message initialization");
            }
        }
        return INSTANCE;
    }

    public void info(String message) {
        writeLog("INFO: " + message);
    }

    public void debug(String message) {
        writeLog("DEBUG: " + message);
    }

    public void warn(String message) {
        writeLog("WARN: " + message);
    }

    public void error(String message) {
        writeLog("ERROR: " + message);
    }

    public void fatal(String message) {
        writeLog("FATAL: " + message);
    }

    private void writeLog(String message) {
        synchronized (INSTANCE) {
            try {
                String writeTime = "[" + (LocalDateTime.now().format(DATE_TIME_FORMATTER)) + "]";
                Files.writeString(outputFilePath, writeTime + message + "\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println("Logger does not work properly. Error message: " + e.getMessage());
            }
        }
    }

    private void validateAndCreateDirectory() throws IOException {
        Path outputDirectory = Path.of(OUT_DIRECTORY);
        if (!Files.exists(outputDirectory)) {
            Files.createDirectory(outputDirectory);
        }
    }

    private void validateAndCreateLogFile() throws IOException {
        Path outputFile = Path.of(OUTPUT_FILEPATH);
        if (!Files.exists(outputFile)) {
            Files.createFile(outputFile);
        }
    }
}
