package ru.ssk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by user on 01.06.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DatabaseConnectionException extends RuntimeException {
    private DatabaseConnectionException() {
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }
}
