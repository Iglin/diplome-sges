package ru.ssk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by user on 15.05.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateDataException extends RuntimeException {
    private DuplicateDataException() {
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
