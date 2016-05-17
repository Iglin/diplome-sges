package ru.ssk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by user on 17.05.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataSynchronizationException extends RuntimeException {
    private DataSynchronizationException() {
    }

    public DataSynchronizationException(String message) {
        super(message);
    }
}
