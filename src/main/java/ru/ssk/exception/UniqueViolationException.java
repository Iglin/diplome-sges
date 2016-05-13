package ru.ssk.exception;

/**
 * Created by user on 13.05.2016.
 */
public class UniqueViolationException extends Exception {

    private UniqueViolationException() {
    }

    public UniqueViolationException(String message) {
        super(message);
    }
}
